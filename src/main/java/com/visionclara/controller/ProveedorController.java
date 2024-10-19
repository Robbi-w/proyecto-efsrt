package com.visionclara.controller;

import java.io.OutputStream;
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

import com.visionclara.model.Proveedor;
import com.visionclara.service.ProveedorService;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping("/proveedor")
public class ProveedorController {
	
		@Autowired
		private ProveedorService serviProveedor;
		
		//Listado de Proveedores
		@RequestMapping("/lista")
		public String inicio(Model model) {
			
			// Obtener listado...
			List<Proveedor> data = serviProveedor.listarProveedores();
					
			// crear lista, para enviar datos a tabla
			model.addAttribute("listaProveedor" ,data);
			return "proveedor";
		}
		
		// Registrar y Actualizar
		@RequestMapping("/grabar")
		public String grabar(@RequestParam("txtCodigo") int cod, @RequestParam("txtNombre") String nom,
				RedirectAttributes redirect) {

			try {
				Proveedor p = new Proveedor();

				p.setCodProv(cod);
				p.setNomProv(nom);

				serviProveedor.grabar(p);

				// Mensaje
				if (cod > 0) {
					// si encuentra un cod mayor a 0; actualiza
					redirect.addFlashAttribute("mensaje", "Proveedor "  + p.getNomProv().toUpperCase() + " se actualizó correctamente.");
				} else {
					// si cod null; lo registra
					redirect.addFlashAttribute("mensaje", "Proveedor "  + p.getNomProv().toUpperCase() + " se registró correctamente.");
				}

			} catch (Exception e) {
				redirect.addAttribute("mensaje", "Ocurrió un error al intentar registrar. F");
				e.printStackTrace();
			}
			return "redirect:/proveedor/lista";
		}

		// Busqueda
		@RequestMapping("/buscar")
		@ResponseBody
		public Proveedor buscar(@RequestParam("buscarCodigo") int cod) {
			Proveedor p = serviProveedor.buscar(cod);
			return p;
		}

		// Eliminar
		@RequestMapping("/eliminar")
		public String eliminar(@RequestParam("buscarCodigo") int cod, RedirectAttributes redirect) {
			
			Proveedor objPro = new Proveedor();
			objPro.setCodProv(cod);
			
			try {
				serviProveedor.eliminar(cod);
				redirect.addFlashAttribute("mensaje", "Proveedor " + "con código: " + objPro.getCodProv() + " eliminado.");
			} catch (Exception e) {
				redirect.addFlashAttribute("mensaje", "Se produjo un error al eliminar este Proveedor. Es posible que tenga una relación con otra tabla en la base de datos, lo que impide su eliminación. F");
				e.printStackTrace();
			}
			
			return "redirect:/proveedor/lista";
		}
		
		// Reporte de Proveedores
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
				String ru = resourceLoader.getResource("classpath:reportes/ReporteProveedor.jasper").getURI().getPath();
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
