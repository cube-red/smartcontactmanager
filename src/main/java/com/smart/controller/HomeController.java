package com.smart.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title","Home - Smart Contact Manager");
		return "home";
	}
	
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title","About - Smart Contact Manager");
		return "about";
	}
	
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title","Register - Smart Contact Manager");
		model.addAttribute("user",new User());
		return "signup";
	}
	
	//handler for registring user
	@RequestMapping(value="/do_register", method=RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") User user, 
				@RequestParam( value="agreement",defaultValue="false") boolean agreement,
				Model model, HttpSession httpSession ) {
		try {
			if(!agreement) {
				 System.out.println("You have not agreed to terms and conditions");
				 throw new Exception("You have not agreed to terms and conditions");
			}
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			System.out.println(agreement);
			System.out.println(user);
			
			User result = this.userRepository.save(user);
			model.addAttribute("user",result);
			httpSession.setAttribute("message", 	new Message("Success","alert-success"));
		}
		catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user);
			httpSession.setAttribute("message", 	new Message("Something Went Wrong","alert-danger"));
		}
		
		return "signup";
	}
}
