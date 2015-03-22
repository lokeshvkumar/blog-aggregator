package us.jblog.aggregator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import us.jblog.aggregator.service.ItemService;


@Controller
public class IndexController {
	
	/**
	 * This method was extended in order to pull all 
	 * items from database.
	 * @author lokesh
	 *
	 */
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value="/")
	public String index(Model model){
		model.addAttribute("items", itemService.getItems());
		return "index";
	}
	
	
}
