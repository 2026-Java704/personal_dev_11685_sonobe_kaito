package com.example.demo.controller;

import java.sql.Date;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Task;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TaskRepository;

@Controller
public class TaskController {

	private final HttpSession session;
	private final Account account;
	private final TaskRepository taskRepository;
	private final CategoryRepository categoryRepository;

	public TaskController(
			HttpSession session,
			Account account,
			TaskRepository taskRepository,
			CategoryRepository categoryRepository) {
		this.session = session;
		this.account = account;
		this.taskRepository = taskRepository;
		this.categoryRepository = categoryRepository;
	}

	//一覧画面
	@GetMapping("/task")
	public String index(@RequestParam(defaultValue = "") Integer categoryId,
			Model model) {
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		//一覧情報の取得
		List<Task> taskList = null;
		if (categoryId == null) {
			taskList = taskRepository.findAll();
		} else {
			taskList = taskRepository.findByCategoryId(categoryId);
		}
		model.addAttribute("tasks", taskList);

		return "task";
	}

	@GetMapping("/tasks/create")
	public String create() {
		return "NewTask";
	}

	//新規タスク処理
	@PostMapping("/tasks/create")
	public String register(
			@RequestParam(defaultValue = "1") Integer categoryId,
			@RequestParam(defaultValue = "") String title,
			@RequestParam(defaultValue = "") Date date,
			@RequestParam(defaultValue = "") Date closingDate,
			@RequestParam(defaultValue = "1") Integer time,
			@RequestParam(defaultValue = "") String memo,
			Model model) {

		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		//		Category category = categoryRepository.findById(categoryId).get();

		Task task = new Task(categoryId, title, date, closingDate, time, memo);
		task.setProgress(0);
		taskRepository.save(task);

		return "redirect:/task";
	}

	//	@PostMapping("/tasks/{id}/edit")
	//	public String register(
	//			@RequestParam(defaultValue = "") Integer taskId,
	//			@RequestParam(defaultValue = "") Integer categoryId,
	//			@RequestParam(defaultValue = "") String title,
	//			@RequestParam(defaultValue = "") Date date,
	//			@RequestParam(defaultValue = "") Date closingDate,
	//			@RequestParam(defaultValue = "") Integer time,
	//			@RequestParam(defaultValue = "") String memo) {
	//
	//		Task task = new Task(taskId, categoryId, title, date, closingDate, time, memo);
	//		taskRepository.save(task);
	//
	//		return "redirect:/task";
	//	}

}
