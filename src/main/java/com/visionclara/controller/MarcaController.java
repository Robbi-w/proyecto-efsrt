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

import com.visionclara.model.Marca;
import com.visionclara.service.MarcaService;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping("/marca")
public class MarcaController {
	
		@Autowired
		private MarcaService serviMarca;
		
		// listado de marcas
		@RequestMapping("/lista")
		public String inicio(Model model) {
			
			// recupera listado de las marcas
			List<Marca> dataMarca = serviMarca.listarMarcas();
					
			// crear el atributo listaMarca para enviar datos a la tabla
			model.addAttribute("listaMarca" ,dataMarca);
			
			// retorna marca.html
			return "marca";
		}
		
		// guardado y actualizado de marcas
		@RequestMapping("/grabar")
		public String grabar(@RequestParam("txtCodigo") int cod, @RequestParam("txtNombre") String nom, @RequestParam("txtPais") String pais, RedirectAttributes redirect) {

			try {
				// object
				Marca m = new Marca();

				// set
				m.setCodMarca(cod);
				m.setNomMarca(nom);
				m.setPaiMarca(pais);

				// llamar metodo
				serviMarca.grabar(m);

				// message
				if (cod > 0) {
					// si encuentra un cod mayor a 0; actualiza
					redirect.addFlashAttribute("mensaje", "Marca " + m.getNomMarca().toUpperCase() + " se actualizó correctamente.");
				} else {
					// si devuelve codigo null, lo registra
					redirect.addFlashAttribute("mensaje", "Marca: "  + m.getNomMarca().toUpperCase() + " se registró corectamente.");
				}

			} catch (Exception e) {
				redirect.addAttribute("mensaje", "Ocurrió un error al intentar grabar!");
				e.printStackTrace();
			}

			return "redirect:/marca/lista";
		}

		// ruta busqueda
		@RequestMapping("/buscar")
		@ResponseBody
		public Marca buscar(@RequestParam("buscarCodigo") int cod) {
			Marca m = serviMarca.buscar(cod);

			return m;
		}

		// ruta eliminar
		@RequestMapping("/eliminar")
		public String eliminar(@RequestParam("buscarCodigo") int cod, RedirectAttributes redirect) {
			
			// object
			Marca objMarca = new Marca();
			objMarca.setCodMarca(cod);
			
			try {
				serviMarca.eliminar(cod);
				redirect.addFlashAttribute("mensaje", "Marca " + "CODIGO: " + objMarca.getCodMarca() + " eliminado.");
			} catch (Exception e) {
				redirect.addFlashAttribute("mensaje", "Se produjo un error al eliminar este registro. Es posible que tenga una relación con otra tabla en la base de datos, lo que impide su eliminación!");
				e.printStackTrace();
			}
			
			return "redirect:/marca/lista";
		}
		
		//Reporte de marcas
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
				String ru = resourceLoader.getResource("classpath:reportes/ReporteMarca.jasper").getURI().getPath();
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
