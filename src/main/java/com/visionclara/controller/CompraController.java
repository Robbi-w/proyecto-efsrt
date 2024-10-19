package com.visionclara.controller;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.visionclara.model.Administrador;
import com.visionclara.model.Compra;
import com.visionclara.model.CompraDetalle;
import com.visionclara.model.Detalle;
import com.visionclara.model.Producto;
import com.visionclara.model.Usuario;
import com.visionclara.service.AdministradorService;
import com.visionclara.service.CompraService;
import com.visionclara.service.ProductoService;
import com.visionclara.service.UsuarioService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping("/compra")
public class CompraController {
	

	@Autowired
	private ProductoService serviProducto;
	@Autowired
	private UsuarioService serviUsuario;
	@Autowired
	private AdministradorService serviAdministrador;
	@Autowired
	private CompraService serviCompra;
	
	//CRUD de compra
	@RequestMapping("/lista")
	public String listado(Model model) {
		List<Compra> dataCompra = serviCompra.listarCompras();
		model.addAttribute("dataCompra", dataCompra);
		return "mantCompra";
	}
	
	@RequestMapping("/search")
	@ResponseBody
	public Compra buscar(@RequestParam("buscarCodigo") int cod) {
		Compra objCompra = serviCompra.buscar(cod);
		return objCompra;
	}

	
	@RequestMapping("/delete")
	public String delete(@RequestParam("buscarCodigo") int cod, RedirectAttributes redirect) {
		Compra objCompra = new Compra();
		objCompra.setCodCom(cod);
		try {
			serviCompra.eliminar(cod);
			redirect.addFlashAttribute("mensaje", "Compra " + "ID: " + objCompra.getCodCom() + " eliminado.");
		} catch (Exception e) {
			redirect.addFlashAttribute("mensaje", "Se produjo un error al eliminar este registro. Es posible que tenga una relación con otra tabla en la base de datos, lo que impide su eliminación!");
			e.printStackTrace();
		}
		return "redirect:/compra/lista";
	}
	
	
	
	
	
    //Creacion de transaccion
	@RequestMapping("/boleta")
	public String compra(Model model) {
		// Listado de Productos
		model.addAttribute("listProducto", serviProducto.listarProductos()); 	
		return "compra";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/adicionar")
	@ResponseBody
	public List<Detalle> adicionar(@RequestParam("codigo") int cod, 
			@RequestParam("nombre") String nom, @RequestParam("cantidad") int can, 
			@RequestParam("precio") BigDecimal precio, HttpSession session) {	
		// Clase Detalle
		List<Detalle> data = null;
		
		if(session.getAttribute("carrito") == null) {
			data = new ArrayList<Detalle>();
		} else { 
			data = (List<Detalle>) session.getAttribute("carrito");
		}
		
		// Verificar si el codigo ya existe en la seleccion
		for (Detalle d : data) {
			if (d.getCodigo() == cod) {
				// Si ya existe, se actualiza la cantidad, para no agregar 2 lineas del mismo producto
				d.setCantidad(d.getCantidad() + can);
				return data;
			}
		}
		
		Detalle objDetalle = new Detalle();
		objDetalle.setCodigo(cod);
		objDetalle.setNombre(nom);
		objDetalle.setCantidad(can);
		objDetalle.setPrecio(precio);
		
		// adicionar objDetalle dentro del arreglo
		data.add(objDetalle);
		
		// create atributo "carrito"
		session.setAttribute("carrito", data);
		
		int cantidadRegistros = data.size();
		session.setAttribute("cantidadRegistros", cantidadRegistros);
		
		return data;
	}
	
	@RequestMapping("/eliminar")
	@ResponseBody
	public List<Detalle> eliminar(@RequestParam("codigo") int cod, HttpSession session){
		// recuperar y guardarlo
		@SuppressWarnings("unchecked")
		List<Detalle> data = (List<Detalle>) session.getAttribute("carrito");
		
		// Iterar
		for(Detalle d : data) {
			if(d.getCodigo() == cod) {
				data.remove(d);
				break;
			}
		}
		
		session.setAttribute("carrito", data);	
		return data;
	}
	
	@RequestMapping("/listarUsuarios")
	@ResponseBody
	public List<Usuario> listarUsuarios(@RequestParam("apellido") String ape){
		List<Usuario> data = serviUsuario.listarUsuarioXApellido(ape+"%");
		return data;
	}
	
	@RequestMapping("/listarAdministradores")
	@ResponseBody
	public List<Administrador> listarAdministradores(@RequestParam("apellido") String ape){
		List<Administrador> dataA = serviAdministrador.listarAdministradorXApellido(ape+"%");
		return dataA;
	}
	
	@RequestMapping("/grabar")
	public String grabar(@RequestParam("fechaCompra") String fecCom,@RequestParam("codigoCliente") int codCli, 
			   @RequestParam("codigoAdmin") int codAdmin, @RequestParam("monto") BigDecimal monto,
				@RequestParam("mpago") String mpago, 
			   HttpSession session, RedirectAttributes redirect) {
		try {
			Compra objCompra = new Compra();
			objCompra.setFecCom(new SimpleDateFormat("yyyy-MM-dd").parse(fecCom));	
			objCompra.setMontoCom(monto);
			objCompra.setMpago(mpago);

			
			Usuario usu = new Usuario();
			usu.setCodUsu(codCli);	
			
			Administrador admi = new Administrador();
			admi.setCodAdm(codAdmin);
			
			objCompra.setCompraUsuario(usu);
			objCompra.setCompraAdministrador(admi);
			
			@SuppressWarnings("unchecked")
			List<Detalle> data=(List<Detalle>) session.getAttribute("carrito");		
			List<CompraDetalle> lista = new ArrayList<CompraDetalle>();
			
			for(Detalle detail:data) {
				CompraDetalle cdetalle = new CompraDetalle();
				Producto prod = new Producto();
				prod.setCodProd(detail.getCodigo());			
		
				cdetalle.setCDetalleProducto(prod);
				cdetalle.setCDetalleCompra(objCompra);	
				cdetalle.setPrecio(detail.getPrecio());
				cdetalle.setCantidad(detail.getCantidad());	
				lista.add(cdetalle);
			}
			objCompra.setListaCDetalle(lista);
			
			serviCompra.registrarCompra(objCompra);
			data.clear();
			session.setAttribute("carrito", data);
			redirect.addFlashAttribute("mensaje","La venta se registro correctamente");
		} catch (Exception e) {
			e.printStackTrace();
			redirect.addFlashAttribute("mensaje","Ha habido un problema al intentar registrar la venta. F");
		}
		return "redirect:/compra/boleta";
	}
	
	@Autowired
	private DataSource dataSource;
	@Autowired
	private ResourceLoader resourceLoader;
	
	@GetMapping("/comprobante")
	public void reporteConFiltros(HttpServletResponse response,
			@RequestParam("txtCodigo") int cod) {
		//Opcion para visualizar el PDF
		response.setHeader("Content-Disposition", "inline;");
		response.setContentType("application/pdf");
		try {
			//crear parametro
			HashMap<String, Object> parametros = new HashMap<>();
			parametros.put("codigo", cod);
			//Carga del Jasper
			String ru = resourceLoader.getResource("classpath:reportes/Boleta.jasper").getURI().getPath();
			//combinar el .jasper con la conexion
			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
			//genera el PDF
			OutputStream outStream = response.getOutputStream();
			//Visualiza
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
