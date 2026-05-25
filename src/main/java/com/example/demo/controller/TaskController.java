package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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
			@RequestParam(defaultValue = "") Integer progress,
			@RequestParam(required = false) LocalDate date,
			@RequestParam(required = false) LocalDate closingDate,
			@RequestParam(defaultValue = "1") Integer time,
			@RequestParam(defaultValue = "") String memo,
			Model model) {
		if (account.getId() == null) {
			return "redirect:/login";
		}

		List<String> errorList = new ArrayList<>();
		if (title.length() == 0) {
			errorList.add("タイトルは必須です");
		}
		if (date == null) {
			errorList.add("開始日付を選択してください");
		}
		if (closingDate == null) {
			errorList.add("期限を選択してください");
		}

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("title", title);
			model.addAttribute("progress", progress);
			model.addAttribute("date", date);
			model.addAttribute("closingDate", closingDate);
			model.addAttribute("time", time);
			model.addAttribute("memo", memo);

			return "NewTask";
		}
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		Task task = new Task(categoryId, title, progress, date, closingDate, time, memo);
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
			@RequestParam(required = false) LocalDate date,
			@RequestParam(required = false) LocalDate closingDate,
			@RequestParam(defaultValue = "") Integer time,
			@RequestParam(defaultValue = "") String memo,
			Model model) {

		if (account.getId() == null) {
			return "redirect:/login";
		}
		List<String> errorList = new ArrayList<>();
		if (title.length() == 0) {
			errorList.add("タイトルは必須です");
		}
		if (progress == null) {
			errorList.add("進行度を選択してください");
		}
		if (date == null) {
			errorList.add("開始日付を選択してください");
		}
		if (closingDate == null) {
			errorList.add("を選択してください");
		}

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("title", title);
			model.addAttribute("progress", progress);
			model.addAttribute("date", date);
			model.addAttribute("closingDate", closingDate);
			model.addAttribute("time", time);

			return "NewTask";
		}

		Task task = taskRepository.findById(id).get();

		if (!task.getUserId().equals(account.getId())) {
			return "redirect:/tasks";
		}

		if (progress != null && progress == 2) {
			taskRepository.deleteById(id);
			return "redirect:/tasks";
		}

		task.setCategoryId(categoryId);
		task.setTitle(title);
		task.setProgress(progress);
		task.setDate(date);
		task.setClosingDate(closingDate);
		task.setTime(time);
		task.setMemo(memo);
		//		task.setUserId(account.getId());
		taskRepository.save(task);

		return "redirect:/task";
	}

	//削除処理 他ユーザーとのID一致確認未実装
	@PostMapping("/tasks/{id}/delete")
	public String delete(@PathVariable("id") Integer id) {
		if (account.getId() == null) {
			return "redirect:/login";
		}

		Task task = taskRepository.findById(id).get();

		if (!task.getUserId().equals(account.getId())) {
			return "redirect:/tasks";
		}

		taskRepository.deleteById(id);

		return "redirect:/tasks";
	}

	//	@GetMapping("/tasks/{id}/sum")
	//	public String sum(
	//			@PathVariable Integer id,
	//			@RequestParam(defaultValue = "") Integer progress,
	//			Model model) {
	//		Task task = taskRepository.findById(id).get();
	//		if (progress != 1) {
	//			task.setProgress(1);
	//			task.setUserId(account.getId());
	//			taskRepository.save(task);
	//			return "redirect:/task";
	//		}
	//		return "redirect:/task";
	//	}
}
