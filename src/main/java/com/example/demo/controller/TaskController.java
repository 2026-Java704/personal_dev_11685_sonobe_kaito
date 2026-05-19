package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class TaskController {

	@GetMapping("/login")
	public String create() {
		return "task";
	}
}
