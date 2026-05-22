package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {
	private final HttpSession session;
	private final Account account;
	private final UserRepository userRepository;

	public UserController(
			HttpSession session,
			Account account,
			UserRepository userRepository) {
		this.session = session;
		this.account = account;
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
		List<User> userList = userRepository.findByNameAndPassword(name, password);
		if (userList == null || userList.size() == 0) {
			// 存在しなかった場合
			model.addAttribute("message", "名前かパスワードが一致しませんでした");
			return "login";
		}
		User user = userList.get(0);

		account.setName(user.getName());
		account.setId(user.getId());

		return "redirect:/task";
	}

	@GetMapping("/users/new")
	public String create() {
		return "NewUser";
	}

	@PostMapping("/users/add")
	public String create(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String password,
			@RequestParam(defaultValue = "") String passwordConfirm) {

		if (password.equals(passwordConfirm)) {
			User user = new User(name, password);
			userRepository.save(user);
		}
		return "redirect:/login";
	}

}
