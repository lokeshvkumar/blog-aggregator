package us.jblog.aggregator.controller;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import us.jblog.aggregator.entity.User;
import us.jblog.aggregator.service.UserService;

/**
 * This class added in order to seperate operations for user and registration.
 * 
 * @author lokesh
 *
 */
@Controller
public class RegisterController {
	
	@Autowired
	private UserService userService;
	

	@ModelAttribute("user")
	public User constructUser(){
		return new User();
	}
	
	/**
	 * this is step 26 to create register form. Create endpoint method along with 
	 * user-register form mapping in general.xml and required jsp.
	 * Spring form or html form can be used. Spring form has advantage, as it 
	 * can be bound to an object. Add jsp form taglib to taglib.jsp.
	 * Create horizontal form using spring and bootstrap.
	 * @return
	 */
	@RequestMapping(value="/register")
	public String showRegister(){
		
		return "user-register";
	}
	
	/**
	 * Step 27
	 * Method above popped up a registration form. We need a method to handle post request
	 * coming in from same url.
	 * Grab the User object using ModelAttribute constructed above. 
	 * Spring will create the User object using the form submitted and apply that 
	 * as arguement to this post request handler.
	 */
	/**
	 * step 42 - server side validation added to this method. @ModelAttribute annocated further with @Valid
	 * and validation result captured in arguement BindingResult. Also check if result has errors.
	 * step 48 - better way to perform redirection is to use redirect attributes. Add flash attributes to
	 * the redirect attributes.
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult bindigResult, 
			RedirectAttributes attributes ){
		//step 42, additonal checks, same checks added for the blog
		if(bindigResult.hasErrors()){
			return "user-register";
		}
		
		userService.save(user);
		//step 36 is redirect. registration completion status, and refreshing the same page results in re-insert of form submitted.
		//solution? redirect!!
		//we return back to the registration form.
		
		//return "user-register";//only line was commented to avoid refreshing same page.
		
		//step 48, added redirect attributes
		attributes.addFlashAttribute("success", true);
		/*return "redirect:/register.html?success=true";//use this parameter on user-registration page to alert
*/		
		return "redirect:/register.html";//step 48, removed parameters from the return statement
		}
	
	/*
	 * This method added as part of setp 44, ir oder to implement
	 * client side ajax call to validate user exists in database.
	 * Test this by invoking it from URL. http://localhost:8080/available.html?userName=admin, and it will return false.
	 * Integrate it with the user-register.jsp.
	 */
	@RequestMapping("/available")
	@ResponseBody//This means that this method will return response some response object
	public String available(@RequestParam String userName){
		
		Boolean available = userService.findOne(userName) == null;
		return available.toString();
		
	}
}
