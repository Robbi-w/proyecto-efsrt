package com.visionclara.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.visionclara.model.Marca;
import com.visionclara.model.Producto;
import com.visionclara.model.Proveedor;
import com.visionclara.service.MarcaService;
import com.visionclara.service.ProductoService;
import com.visionclara.service.ProveedorService;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping("/producto")
public class ProductoController {
	
		@Autowired
		private ProductoService serviProducto;
		@Autowired
		private MarcaService serviMarca;
		@Autowired
		private ProveedorService serviProveedor;
		
		//Listado de Productos
		@RequestMapping("/lista")
		public String inicio(Model model) {
			
			// Obtener listado
			List<Producto> data = serviProducto.listarProductos();
			
			// Recuperar listado para combos
			List<Marca> dataMarca = serviMarca.listarMarcas();
			List<Proveedor> dataProveedor = serviProveedor.listarProveedores();
					
			// Crear lista, para enviar datos a tabla
			model.addAttribute("listaProducto" ,data);
			// crear lista, para enviar datos al combo
			model.addAttribute("listaMarca" ,dataMarca);
			model.addAttribute("listaProveedor" ,dataProveedor);
			
			return "producto";
		}
		
		// Registrar y actualizar
		@RequestMapping("/grabar")
		public String grabar(@RequestParam("txtCodigo") int cod, @RequestParam("txtNombre") String nom, @RequestParam("txtDescripcion") String des , 
				 @RequestParam("txtCategoria") String cat, @RequestParam("txtStock") int stock, 
				  @RequestParam("txtPrecio") BigDecimal pre, @RequestParam("txtMarca") int marca, 
				  @RequestParam("txtProveedor") int proveedor, 	RedirectAttributes redirect) {

			try {
				Producto objProducto = new Producto();

				objProducto.setCodProd(cod);
				objProducto.setNomProd(nom);
				objProducto.setDesProd(des);
				objProducto.setCatProd(cat);
				objProducto.setStockProd(stock);
				objProducto.setPrecioCompra(pre);
				
				Marca objMarca = new Marca();
				objMarca.setCodMarca(marca);
				
				Proveedor objProveedor = new Proveedor();
				objProveedor.setCodProv(proveedor);
				
				objProducto.setProductoMarca(objMarca);
				objProducto.setProductoProveedor(objProveedor);
				
				serviProducto.grabar(objProducto);

				if (cod > 0) {
					// si encuentra un cod mayor a 0; actualiza
					redirect.addFlashAttribute("mensaje", "Producto " + objProducto.getNomProd().toUpperCase() + " se actualizó correctamente.");
				} else {
					// si cod null; lo registra
					redirect.addFlashAttribute("mensaje", "Producto "  + objProducto.getNomProd().toUpperCase() + " se registró correctamente.");
				}

			} catch (Exception e) {
				redirect.addAttribute("mensaje", "Ocurrió un error al intentar guardar el producto.");
				e.printStackTrace();
			}

			return "redirect:/producto/lista";
		}

		// Busqueda
		@RequestMapping("/buscar")
		@ResponseBody
		public Producto buscar(@RequestParam("buscarCodigo") int cod) {
			Producto p = serviProducto.buscar(cod);
			return p;
		}

		// Eliminar
		@RequestMapping("/eliminar")
		public String eliminar(@RequestParam("buscarCodigo") int cod, RedirectAttributes redirect) {
		
			Producto objProducto = new Producto();
			objProducto.setCodProd(cod);
			
			try {
				serviProducto.eliminar(cod);
				redirect.addFlashAttribute("mensaje", "Producto " + "Con código: " + objProducto.getCodProd() + " eliminado.");
			} catch (Exception e) {
				redirect.addFlashAttribute("mensaje", "Se produjo un error al eliminar este producto. Es posible que tenga una relación con otra tabla en la base de datos, lo que impide su eliminación. F");
				e.printStackTrace();
			}
			return "redirect:/producto/lista";
		}
		
		@RequestMapping("/detalle/{id}")
		public String goDetail(@PathVariable(value = "id") int id, Model model) {
			Producto producto = serviProducto.listById(id);
			model.addAttribute("productoDetalle", producto);
			return "detalle";
		}
		
		// Ruta de la img y archivo
		@RequestMapping("/imagen-producto")
		public String subirArchivo(@RequestParam("dataArchivo") MultipartFile archivo, @RequestParam("txtCodigo") Integer cod,
				RedirectAttributes redirect) throws IOException {
			
			// Almacena el archivo
			String nomArchivoString = archivo.getOriginalFilename();
			
			// Guardar en un arreglo byte
			byte[] bytes = archivo.getBytes();
						
			// Ruta de escritorio
			//String ruta="C:\\Users\\User\\Desktop\\ImagenesDAWI\\datosImg";
			
			// Ruta de USB
			String ruta="C:/ProyectoEFSRT/ImagenesDAWI/datosImg/";
			
			// Generar archivo
			Files.write(Paths.get(ruta + nomArchivoString), bytes);
			
			// Llenar parametros
			serviProducto.actualizarImg(bytes, nomArchivoString, cod);
			
			// Mensaje
			redirect.addFlashAttribute("mensaje", "Imagen " + nomArchivoString.toUpperCase() + " se actualizó correctamente.");
			
			return "redirect:/producto/lista";
		}
		
		@Autowired
		private DataSource dataSource;
		@Autowired
		private ResourceLoader resourceLoader;
		
		@GetMapping("/reportes")
		public void reportes(HttpServletResponse response) {
			//Opcion para visualizar el PDF
			response.setHeader("Content-Disposition", "inline;");
			response.setContentType("application/pdf");
			try {
				//Carga del Jasper
				String ru = resourceLoader.getResource("classpath:reportes/ReporteProducto.jasper").getURI().getPath();
				//combinar el .jasper con la conexion
				JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
				//genera el PDF
				OutputStream outStream = response.getOutputStream();
				//Visualiza
				JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
}
