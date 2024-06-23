package DouglasGuacaran.Eva_Int_Mod_6.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class TestAuthController {
	
	@GetMapping("/test")
	public String testAuth(){
		return "Autenticado";
    }
	
	@GetMapping("/Hello-secured")
	public String helloSecured() {
		return "Hello from secured";
	}
		
}
