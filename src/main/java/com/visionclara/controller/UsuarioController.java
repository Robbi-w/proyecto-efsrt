package com.visionclara.controller;

import java.io.OutputStream;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.visionclara.model.Rol;
import com.visionclara.model.Usuario;
import com.visionclara.service.RolService;
import com.visionclara.service.UsuarioService;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService serviUsuario;
	@Autowired
	private RolService serviRol;
	
	
	// Login
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }
	
	// Listado de Usuarios
		@RequestMapping("/usuario/lista")
		public String inicio(Model model) {
			
			List<Usuario> data = serviUsuario.listarUsuarios();
			List<Rol> dataRol = serviRol.listarRoles();
			
			model.addAttribute("listaUsuario", data);
			model.addAttribute("listaRol", dataRol);
			
			return "usuario";
		}
		//Guardar o Actualizar
		@RequestMapping("/usuario/grabar")
		public String grabar(@RequestParam("txtCodigo") int cod, @RequestParam("txtNombre") String nom, @RequestParam("txtApellido") String ape, 
				@RequestParam("txtDni") int dni, @RequestParam("txtEmail") String ema, @RequestParam("txtTelefono") int tel, 
				@RequestParam("txtDireccion") String dir,@RequestParam("txtRol") int rol,  RedirectAttributes redirect) {
			try {
				Usuario u = new Usuario();
				
				u.setCodUsu(cod);
				u.setNomUsu(nom);
				u.setApeUsu(ape);
				u.setDniUsu(dni);
				u.setEmaUsu(ema);
				u.setTelUsu(tel);
				u.setDirUsu(dir);
				
				Rol objRol = new Rol();
				objRol.setCodRol(rol);	
				
				u.setUsuarioRol(objRol);
				// Guardado
				serviUsuario.grabar(u);
				// Mensaje
				if (cod > 0) {
					// si encuentra un cod mayor a 0; actualiza
					redirect.addFlashAttribute("mensaje", "Usuario " + u.getNomUsu().toUpperCase() + " se actualizó correctamente.");
				} else {
					// si cod null; lo registra
					redirect.addFlashAttribute("mensaje", "Usuario: " + u.getNomUsu().toUpperCase() + " se registró corectamente.");
				}
			} catch (Exception e) {
				redirect.addAttribute("mensaje", "Ocurrió un error al intentar grabar!");
				e.printStackTrace();
			}
			return "redirect:/usuario/lista";
		}
		
		// Busqueda
		@RequestMapping("/usuario/buscar")
		@ResponseBody
		public Usuario buscar(@RequestParam("buscarCodigo") int cod) {
			// LLAMAR AL METODO BUSCAR
			Usuario u = serviUsuario.buscar(cod);
			return u;
		}
		
		// Eliminar
		@RequestMapping("/usuario/eliminar")
		public String eliminar(@RequestParam("buscarCodigo") int cod, RedirectAttributes redirect) {
			// OBJECT
			Usuario objUser = new Usuario();
			objUser.setCodUsu(cod);
			try {
				serviUsuario.eliminar(cod);
				redirect.addFlashAttribute("mensaje", "Usuario " + "CODIGO: " + objUser.getCodUsu() + " eliminado.");
			} catch (Exception e) {
				redirect.addFlashAttribute("mensaje", "Se produjo un error al eliminar este registro. Es posible que tenga una relación con otra tabla en la base de datos, lo que impide su eliminación!");
				e.printStackTrace();
			}
			return "redirect:/usuario/lista";
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
				String ru = resourceLoader.getResource("classpath:reportes/ReporteUsuario.jasper").getURI().getPath();
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
