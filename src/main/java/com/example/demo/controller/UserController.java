package com.example.demo.controller;

import java.util.ArrayList;
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

	//ログイン画面表示
	@GetMapping({ "/", "login", "logout" })
	public String index() {
		session.invalidate();
		return "login";
	}

	//ログイン処理
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

	//新規登録画面
	@GetMapping("/users/new")
	public String create() {
		return "NewUser";
	}

	//新規登録処理
	@PostMapping("/users/add")
	public String create(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String password,
			@RequestParam(defaultValue = "") String passwordConfirm,
			Model model) {

		List<String> errorList = new ArrayList<>();
		if (name.length() == 0) {
			errorList.add("名前は必須です");
		}
		if (password.length() == 0) {
			errorList.add("パスワードを入力してください");
		}
		if (passwordConfirm.length() == 0) {
			errorList.add("パスワード確認を入力してください");
		}
		if (!password.equals(passwordConfirm)) {
			errorList.add("パスワードが一致していません");
		}
		List<User> userList = userRepository.findByName(name);
		if (userList != null && userList.size() > 0) {
			// 登録済みのメールアドレスが存在した場合
			errorList.add("登録済みの名前です");
		}

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("name", name);
			model.addAttribute("password", password);
			model.addAttribute("passwordConfirm", passwordConfirm);
			return "NewUser";
		}

		User user = new User(name, password);
		userRepository.save(user);

		return "redirect:/login";
	}

}
