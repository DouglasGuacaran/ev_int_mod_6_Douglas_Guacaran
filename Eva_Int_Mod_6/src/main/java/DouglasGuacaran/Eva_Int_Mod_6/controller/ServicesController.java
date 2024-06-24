package DouglasGuacaran.Eva_Int_Mod_6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ServicesController {
	@GetMapping("/services")
	public String services() {
		return "services";
	}
	
}
