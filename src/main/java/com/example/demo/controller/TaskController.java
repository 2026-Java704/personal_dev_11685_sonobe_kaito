package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TaskController {

	@GetMapping("/task")
	public String index() {
		return "task";
	}

	@PostMapping("/tasks/create")
	public String create() {

		return "NewTask";
	}

}
