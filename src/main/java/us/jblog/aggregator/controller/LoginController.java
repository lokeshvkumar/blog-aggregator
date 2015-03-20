package us.jblog.aggregator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Step 29 to create custom login form along with spring security.
 * 
 * @author lokesh
 *
 */

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public String login(){
		
		return "login";
	} 
}
