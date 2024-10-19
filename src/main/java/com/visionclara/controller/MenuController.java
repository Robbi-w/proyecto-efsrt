package com.visionclara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.visionclara.repository.AdministradorRepository;
import com.visionclara.repository.CompraDetalleRepository;
import com.visionclara.repository.CompraRepository;
import com.visionclara.repository.MarcaRepository;
import com.visionclara.repository.ProductoRepository;
import com.visionclara.repository.ProveedorRepository;
import com.visionclara.repository.UsuarioRepository;




@Controller
public class MenuController {
	
	@Autowired
	private UsuarioRepository repoUsuario;
	@Autowired
	private AdministradorRepository repoAdmin;
	@Autowired
	private ProductoRepository repoProdcuto;
	@Autowired
	private ProveedorRepository repoProveedor;
	@Autowired
	private MarcaRepository repoMarca;
	@Autowired
	private CompraRepository repoCompra;
	@Autowired
	private CompraDetalleRepository repoCDetalle;
	
	
	@GetMapping("/menu")
	public String abrirMenu(Model model) {
		//Contar la cantidad de registros dentro del repository
		Long administradores = repoAdmin.count();
		Long usuarios = repoUsuario.count();
		Long productos = repoProdcuto.count();
		Long proveedores = repoProveedor.count();
		Long marcas = repoMarca.count();
		Long compras = repoCompra.count();
		Long cDetalles = repoCDetalle.count();
		
		model.addAttribute("cantUsuario", usuarios);
		model.addAttribute("cantAdministrador", administradores);
		model.addAttribute("cantProducto", productos);
		model.addAttribute("cantProveedor", proveedores);
		model.addAttribute("cantMarca", marcas);
		model.addAttribute("cantCompra", compras);
		model.addAttribute("cantCDetalle", cDetalles);
		
		return "menu";
	}
}
