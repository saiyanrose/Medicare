package com.medicare.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medicare.entity.User;
import com.medicare.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/login")
	public String login() {		
		return "login";
	}
	@GetMapping(value="/register")
	public String registerForm(Model model) {
		User user=new User();
		model.addAttribute("user",user);
		return "registerUser";
	}
	@PostMapping("/register/register_success")
	public String registerHandle(@ModelAttribute("user") @Valid User user,BindingResult bindingResult,Model model,RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("message","All fields are required*");
			return "registerUser";
		}
		User saveUser=userService.saveNewUser(user);
		redirectAttributes.addFlashAttribute("message","Register Successfully!");
		return "redirect:/login";
	}
	
}
