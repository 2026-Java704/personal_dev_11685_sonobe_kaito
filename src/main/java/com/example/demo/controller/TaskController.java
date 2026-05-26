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

		//合計時間の表示
		//		List<Task> tasks = taskRepository.findAll();

		int sum = 0;

		for (Task task : taskList) {
			sum += task.getTime();
		}

		model.addAttribute("sum", sum);

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
		if (date != null && closingDate != null) {
			if (date.isAfter(closingDate)) {
				errorList.add("開始日は期限より前に設定してください");
			}
		}
		if (progress == null) {
			errorList.add("進捗状況が設定されていません");
		}
		if (categoryId == null) {
			errorList.add("カテゴリが設定されていません");
		}
		if (time <= 0) {
			errorList.add("1分未満は設定できません");
		}

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("categoryId", categoryId);
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
			@RequestParam(required = false) Integer categoryId,
			@RequestParam(defaultValue = "") String title,
			@RequestParam(required = false) Integer progress,
			@RequestParam(required = false) LocalDate date,
			@RequestParam(required = false) LocalDate closingDate,
			@RequestParam(required = false) Integer time,
			@RequestParam(defaultValue = "") String memo,
			Model model) {

		if (account.getId() == null) {
			return "redirect:/login";
		}

		Task task = taskRepository.findById(id).get();

		if (!task.getUserId().equals(account.getId())) {
			return "redirect:/tasks";
		}

		List<String> errorList = new ArrayList<>();

		if (title == null || title.length() == 0) {
			errorList.add("タイトルは必須です");
		}

		if (date == null) {
			errorList.add("開始日付を選択してください");
		}

		if (closingDate == null) {
			errorList.add("期限を選択してください");
		}

		if (date != null && closingDate != null && date.isAfter(closingDate)) {
			errorList.add("開始日は期限より前に設定してください");
		}

		if (progress == null) {
			errorList.add("進捗状況が設定されていません");
		}

		if (categoryId == null) {
			errorList.add("カテゴリが設定されていません");
		}

		if (time == null || time <= 0) {
			errorList.add("1分未満は設定できません");
		}

		if (errorList.size() > 0) {
			task.setCategoryId(categoryId);
			task.setTitle(title);
			task.setProgress(progress);
			task.setDate(date);
			task.setClosingDate(closingDate);
			task.setTime(time);
			task.setMemo(memo);

			model.addAttribute("errorList", errorList);
			model.addAttribute("task", task);

			List<Category> categoryList = categoryRepository.findAll();
			model.addAttribute("categories", categoryList);

			return "editTask";
		}

		task.setCategoryId(categoryId);
		task.setTitle(title);
		task.setProgress(progress);
		task.setDate(date);
		task.setClosingDate(closingDate);
		task.setTime(time);
		task.setMemo(memo);

		taskRepository.save(task);

		return "redirect:/task";
	}

	//削除処理 他ユーザーとのID一致確認未実装
	@GetMapping("/tasks/{id}/delete")
	public String delete(@PathVariable("id") Integer id) {
		if (account.getId() == null) {
			return "redirect:/login";
		}

		Task task = taskRepository.findById(id).get();

		if (!task.getUserId().equals(account.getId())) {
			return "redirect:/task";
		}

		taskRepository.deleteById(id);

		return "redirect:/task";
	}

	//完了処理
	@GetMapping("/tasks/{id}/end")
	public String end(
			@PathVariable Integer id,
			@RequestParam(defaultValue = "") Integer progress,
			Model model) {

		Task task = taskRepository.findById(id).get();

		task.setProgress(1);
		taskRepository.save(task);
		return "redirect:/task";
	}

	//	@GetMapping("/logout")
	//	public String logout() {
	//		return "logout";
	//	}
	//合計時間
	//	@GetMapping("/task")
	//	public String index(Model model) {
	//
	//		List<Task> tasks = taskRepository.findAll();
	//
	//		int sum = 0;
	//
	//		for (Task task : tasks) {
	//			sum += task.getTime();
	//		}
	//
	//		model.addAttribute("tasks", tasks);
	//		model.addAttribute("sum", sum);
	//
	//		return "task";
	//	}
	//	@PostMapping("/tasks/{id}/sum")
	//	public String sum(
	//			@PathVariable Integer id,
	//			@RequestParam(defaultValue = "") Integer time,
	//			Model model) {
	//
	//		Task task = taskRepository.findById(id).get();
	//
	//		task.setProgress(1);
	//		taskRepository.save(task);
	//		return "redirect:/task";
	//	}

}
