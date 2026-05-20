package com.example.demo.controller;

import java.time.LocalDate;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Task;
import com.example.demo.model.Account;
//import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TaskRepository;

@Controller
public class TaskController {

	private final HttpSession session;
	private final Account account;
	private final TaskRepository taskRepository;
	//	private final CategoryRepository categoryRepository;

	public TaskController(
			HttpSession session,
			Account account,
			TaskRepository taskRepository) {
		this.session = session;
		this.account = account;
		this.taskRepository = taskRepository;
		//		this.categoryRepository = categoryRepository;
	}

	//	@GetMapping("/task")
	//	public String index(
	//			@RequestParam(defaultValue = "") Integer categoryId,
	//			Model model) {
	//
	//		List<Category> categoryList = categoryRepository.findAll();
	//		model.addAttribute("categories", categoryList);
	//
	//		List<Task> taskList = null;
	//		if (categoryId == null) {
	//			taskList = taskRepository.findAll();
	//		} else {
	//			taskList = taskRepository.findByCategory(categoryId);
	//		}
	//		model.addAttribute("tasks", taskList);
	//
	//		return "task";
	//	}

	@GetMapping("/tasks/create")
	public String create() {
		return "NewTask";
	}

	@PostMapping("/tasks/create")
	public String register(
			@RequestParam(defaultValue = "") String contact,
			@RequestParam(defaultValue = "") String title,
			@RequestParam(defaultValue = "") LocalDate date,
			@RequestParam(defaultValue = "") LocalDate closing_date,
			@RequestParam(defaultValue = "") Integer time,
			@RequestParam(defaultValue = "") String memo) {

		Task task = new Task(contact, title, date, closing_date, time, memo);
		taskRepository.save(task);

		return "task";
	}
}
