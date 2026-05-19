package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {
	private final HttpSession session;
	private final UserRepository userRepository;

	public UserController(
			HttpSession session,
			UserRepository userRepository) {
		this.session = session;
		this.userRepository = userRepository;
	}

	@GetMapping({ "/", "login", "logout" })
	public String index() {
		session.invalidate();
		return "login";
	}

	@PostMapping("/login")
	public String login(
			@RequestParam String name,
			@RequestParam String password,
			Model model) {
		// 名前が空の場合にエラーとする
		if (name.length() == 0 || password.length() == 0) {
			model.addAttribute("message", "入力してください");
			return "login";
		}

		// 「/items」へのリダイレクト
		return "redirect:/task";
	}

	@GetMapping("/users/new")
	public String create() {
		return "NewUser";
	}

	@PostMapping("/users/add")
	public String create(
			@RequestParam String name,
			@RequestParam String password,
			Model model) {
		User user = new User(name, password);

		userRepository.save(user);

		return "redirect:/login";
	}
}