package us.jblog.aggregator.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import us.jblog.aggregator.entity.Blog;
import us.jblog.aggregator.entity.User;
import us.jblog.aggregator.service.BlogSerive;
import us.jblog.aggregator.service.UserService;

/**
 * 
 * @author lokesh
 *
 */
@Controller
public class UserController {
	
	/**
	 * At the time of start up, spring will create this object.
	 * It will be referenced in the jsp, for example user-register.jsp.
	 * Each time form is submitted, spring will create user object for each submission.
	 * User 'user' attribute to refer in jsp. as  -- <form:form commandName="user" cssClass="form-horizontal">
	 * @return
	 */
	@ModelAttribute("user")
	public User constructUser(){
		return new User();
	}
	
	/**
	 * STEP 38 - At the time of start up, spring will create this object.
	 * It will be referenced in the jsp, for example user-register.jsp. whn user clicks on add
	 * blog, the form oibject will be a blog object.
	 * Each time form is submitted, spring will create Blog object for each submission.
	 * Blog 'blog' attribute to refer in jsp. as  -- <form:form commandName="blog" cssClass="form-horizontal">
	 * When user goes to his account and does a post, method mapped to /account as post request
	 * will be invoked
	 * @return
	 */
	@ModelAttribute("blog")
	public Blog constructBlog(){
		return new Blog();
	}
	/**
	 * step 20
	 * You can autowire repository directoly to Controller. Best practice is to create a corresponding 
	 * service and have resposiroty autowired to that service, in turn have service autowired to the controller.
	 * 
	 */
	
	//step 21 involves creating user jsp to pick these values returned by this controller.
	//step22 create a definition of the jsp page in general.xml tiles template definitions.
	//Also add JSTL dependencies to the pom.xml
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogSerive blogService;
	
	@RequestMapping(value="/users")
	public String users(Model model){
		 /*
		  * In here, we refer the service, pull the users list information.
		  * Load into an object and forward the request with model object to view.
		  * Front controller picks the model passed by this controller and send the model object
		  * to respective view template.
		  */
		model.addAttribute("users", userService.findAll());
		return "users";//returning a view name
	}
	
	/**
	 * modification done to this method to bring back blogs and its associated items.
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/users/{id}")
	public String detail(Model model, @PathVariable int id){
		model.addAttribute("user", userService.findOneWithBlogs(id));
		System.out.println("showing user-detail!!!!!!!");
		return "user-detail";
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
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult bindigResult ){
		//step 42, additonal checks, same checks added for the blog
		if(bindigResult.hasErrors()){
			return "user-register";
		}
		
		userService.save(user);
		//step 36 is redirect. registration completion status, and refreshing the same page results in re-insert of form submitted.
		//solution? redirect!!
		//we return back to the registration form.
		
		//return "user-register";//only line was commented to avoid refreshing same page.
		
		return "redirect:/register.html?success=true";//use this parameter on user-registration page to alert
	}
	
	
	/*
	 * step 37, added to allow users to see their own details.
	 */
	@RequestMapping(value="/account")
	public String account(Model model, Principal principal){
		//Principal will be contained in the session and will have the name of the user
		//if the user is not logged in, principal.getName() will return null.
		String name = principal.getName();
		//add an attribute to the model.
		model.addAttribute("user", userService.findOneWithBlogs(name));
		//as part of step 43, the return page was changed to account instead of user-detail
		//next remove the option to create new blog for administrator on user-details page.user
		return "account";
	}
	/**
	 * This method is involved when user clicks on add blog. Its a post submission on account page.
	 * With the form submission, principal object will have the user name. This can be invoked to obtain the users
	 * detail. This will invoke blogservice and save blog to the user.
	 * Once submitted, the user is redirected back to the same account page. 
	 * step 39 is to have the blogs as tabs. Using bootstrap.
	 */
	@RequestMapping(value="/account", method=RequestMethod.POST)
	public String doAddBlog(Model model, @Valid @ModelAttribute("blog") Blog blog,BindingResult bindigResult,  Principal principal){
		//step 42, server side validation/
		//this method needs to call account() method since, it adds user details to the model.
		//hence the Model added to the arguement list as well.
		//finally we need to print validation result in jsp file using <form:errors> tag
		//always make sure BindingResult bindigResult follows @Valid and @ModelAttribute arguements
		//nest step is client side validation
		System.out.println("invoked from blog submission page!!");
		if(bindigResult.hasErrors()){
			System.out.println("invoked within blog submission has errors!!");
			return account(model, principal);
		}
		String name = principal.getName();
		blogService.save(blog, name);
		return "redirect:/account.html";
	}
	
	/*@RequestMapping(value="/account", method=RequestMethod.POST)
	public String doAddBlog(@ModelAttribute("blog") Blog blog, Principal principal){
		//step 42, server side validation/
		//this method needs to call account() method since, it adds user details to the model.
		//hence the Model added to the arguement list as well.
		//finally we need to print validation result in jsp file using <form:errors> tag
		String name = principal.getName();
		blogService.save(blog, name);
		return "redirect:/account.html";
	}*/
	/**
	 * step 41 is to avoid one user from removing other users
	 * blog. 
	 * 1. One way to do is pass an arguement of Principal along with the blog id,
	 * then in the service layer check the user id against the user id on the blog and
	 * then perform delete if they match.
	 * 2. 2nd approach is to use spring security annotations. and then modofy the following method.
	 */
	/**
	 * Step 40 is to add functionality to remove blog from user-detail.
	 * Also to handle unique constraint exception when you delete the last blog
	 * available for the user. With this, one user cannot remove blog of other user.
	 */
	@RequestMapping(value="/blog/remove/{id}")
	public String removeBlog(@PathVariable int id){
		
		Blog blog = blogService.findOne(id);
		blogService.delete(blog);
		//blogService.remove(id);
		
		return "redirect:/account.html";
	}
	
	/**
	 * as part of step 40, another method added to aloow for deleting users.
	 * 
	 */
	@RequestMapping(value="/users/remove/{id}")
	public String removeUser(@PathVariable int id){
		
		userService.remove(id);
		return "redirect:/users";
	}
}
