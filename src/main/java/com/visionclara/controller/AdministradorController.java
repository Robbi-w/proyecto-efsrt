package com.visionclara.controller;

import java.io.OutputStream;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.visionclara.model.Administrador;
import com.visionclara.model.Rol;
import com.visionclara.service.AdministradorService;
import com.visionclara.service.RolService;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;


@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	@Autowired
	private AdministradorService serviAdmin;
	@Autowired
	private RolService serviRol;
	
	@GetMapping("lista")
	public String listado(Model model) {	
		try { model.addAttribute("listAdmin", serviAdmin.listarAdministradores());
		} catch (Exception e) { e.printStackTrace(); }
		return "administrador";
	}
	
	@GetMapping("/nuevo")
	public String newAdmin(Model model) {
		Administrador admin = new Administrador();
		admin.setCodAdm(0);
		model.addAttribute("admin", admin);
		
		// Llenar el combo con los roles de administrador disponibles
		List<Rol> dataRol = serviRol.listarRoles();
		model.addAttribute("listaRol", dataRol);
		
		return "mantAdministrador";
	}
	
	@PostMapping("/save")
	public String saveAdmin(@Validated @ModelAttribute("admin") Administrador admin, BindingResult result, 
			Model model, RedirectAttributes flash, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			System.out.println(result.getFieldError());
			return "mantAdministrador";
		} else {
			try { 
				serviAdmin.save(admin);
				if (admin.getCodAdm() > 0) {
					flash.addFlashAttribute("mensaje", "Vendedor " + admin.getNomAdm().toUpperCase() + " se actualizó correctamente.");					
				} else {
					flash.addFlashAttribute("mensaje", "Vendedor " + admin.getNomAdm().toUpperCase() + " se registró correctamente.");										
				}
			} catch (Exception e) { 
				flash.addFlashAttribute("mensaje", "Se produjo un error al grabar este registro!");
				e.printStackTrace(); 
			}
			status.setComplete();
		}
		return "redirect:/administrador/lista";
	}
	
	@RequestMapping("/actualizar/{id}")
	public String goUpdate(@PathVariable(value = "id") int id, Model model) {
		Administrador admin = serviAdmin.listById(id);
		model.addAttribute("admin", admin);
		
		// Llenar el combo con los roles disponibles
		List<Rol> dataRol = serviRol.listarRoles();
		model.addAttribute("listaRol", dataRol);
		
		return "mantAdministrador";
	}
	
	@RequestMapping("/delete/{id}")
	public String eliminar(@PathVariable(value = "id") int id, Model model, RedirectAttributes attributes) {
		Administrador objAdmin = new Administrador();
		objAdmin.setCodAdm(id);
		try { 
			serviAdmin.eliminarByID(id); 
			attributes.addFlashAttribute("mensaje", "El administrador con ID " + objAdmin.getCodAdm()  + " ha sido eliminado satisfactoriamente.");
		} catch (Exception e) { 
			attributes.addFlashAttribute("mensaje", "Se produjo un error al eliminar. Es posible que tenga una relación con otra tabla en la base de datos, lo que impide su eliminación :(");
			e.printStackTrace(); 
		}
		return "redirect:/administrador/lista";
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
			String ru = resourceLoader.getResource("classpath:reportes/ReporteAdministrador.jasper").getURI().getPath();
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
