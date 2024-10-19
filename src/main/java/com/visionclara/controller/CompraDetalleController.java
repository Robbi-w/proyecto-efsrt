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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.visionclara.model.CompraDetalle;
import com.visionclara.service.CompraDetalleService;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;


@Controller
@RequestMapping("/detalles")
public class CompraDetalleController {
	
	@Autowired
	private CompraDetalleService serviCompraDetalle;

	@RequestMapping("/lista")
	public String detalles(Model model) {
		List<CompraDetalle> detalles = serviCompraDetalle.listaDetalles();
		model.addAttribute("dataDetalles", detalles);
		return "mantDetalle";
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(@RequestParam("buscarCodigo") int cod, RedirectAttributes redirect) {
		CompraDetalle objCompraDetalle = new CompraDetalle();
		objCompraDetalle.setCodDetCom(cod);
		try {
			serviCompraDetalle.eliminar(cod);
			redirect.addFlashAttribute("mensaje", "Compra Detalle " + "ID: " + objCompraDetalle.getCodDetCom() + " eliminado.");
		} catch (Exception e) {
			redirect.addFlashAttribute("mensaje", "Se produjo un error al eliminar este registro!");
			e.printStackTrace();
		}
		return "redirect:/detalles/lista";
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
					String ru = resourceLoader.getResource("classpath:reportes/ReporteVentas.jasper").getURI().getPath();
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
