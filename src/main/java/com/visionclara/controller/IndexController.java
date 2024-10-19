package com.visionclara.controller;


import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.visionclara.model.Producto;
import com.visionclara.service.ProductoService;



@Controller
public class IndexController {
	
	@Autowired
	private ProductoService serviProd;
	
	@RequestMapping("/index")
	public String inicio(Model model) {
		List<Producto> data = serviProd.listarProductos();
		Collections.reverse(data);
		model.addAttribute("dataProducto", data);	
		return "index";
	}

}
