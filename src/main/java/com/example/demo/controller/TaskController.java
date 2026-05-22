package com.example.demo.controller;

import java.sql.Date;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

		if (account.getId() == null) {
			return "redirect:/login";
		}
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		List<Task> taskList = taskRepository.findAll();
		model.addAttribute("tasks", taskList);

		//一覧情報の取得
		if (categoryId == null) {
			taskList = taskRepository.findByUserId(account.getId());
		} else {
			taskList = taskRepository.findByUserIdAndCategoryId(account.getId(), categoryId);
		}
		model.addAttribute("tasks", taskList);

		return "task";
	}

	@GetMapping("/tasks/create")
	public String create() {
		if (account.getId() == null) {
			return "redirect:/login";
		}
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
		if (account.getId() == null) {
			return "redirect:/login";
		}
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		Task task = new Task(categoryId, title, date, closingDate, time, memo);
		task.setProgress(0);
		task.setUserId(account.getId());
		taskRepository.save(task);

		return "redirect:/task";
	}

	@GetMapping("/tasks/{id}/edit")
	public String edit(
			@PathVariable Integer id,
			Model model) {
		if (account.getId() == null) {
			return "redirect:/login";
		}
		// 主キー検索
		Task task = taskRepository.findById(id).get();
		model.addAttribute("task", task);

		return "EditTask";
	}

	//更新登録
	@PostMapping("/tasks/{id}/edit")
	public String register(
			@PathVariable Integer id,
			@RequestParam(defaultValue = "") Integer categoryId,
			@RequestParam(defaultValue = "") String title,
			@RequestParam(defaultValue = "") Integer progress,
			@RequestParam(defaultValue = "") Date date,
			@RequestParam(defaultValue = "") Date closingDate,
			@RequestParam(defaultValue = "") Integer time,
			@RequestParam(defaultValue = "") String memo,
			Model model) {

		if (account.getId() == null) {
			return "redirect:/login";
		}
		Task task = taskRepository.findById(id).get();
		task.setCategoryId(categoryId);
		task.setTitle(title);
		task.setProgress(progress);
		task.setDate(date);
		task.setClosingDate(closingDate);
		task.setTime(time);
		task.setMemo(memo);
		task.setUserId(account.getId());
		taskRepository.save(task);

		return "redirect:/task";
	}

	//削除処理 他ユーザーとのID一致確認未実装
	@PostMapping("/tasks/{id}/delete")
	public String delete(@PathVariable("id") Integer id) {
		taskRepository.deleteById(id);
		return "redirect:/task";
	}

}
