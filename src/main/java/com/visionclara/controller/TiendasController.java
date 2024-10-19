package com.visionclara.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TiendasController {
	//redireccionar a contactanos
	@RequestMapping("/tiendas")
	public String contacto(Model model) {
		return "tiendas";
	}

}
