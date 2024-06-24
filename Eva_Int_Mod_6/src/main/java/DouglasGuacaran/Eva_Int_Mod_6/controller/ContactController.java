package DouglasGuacaran.Eva_Int_Mod_6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

@GetMapping("/contact")
	public String contact() {
		return "contact";
	}
}