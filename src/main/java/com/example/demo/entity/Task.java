package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id")
	private Integer taskId;
	@Column(name = "user_id")
	private Integer userId;
	@Column(name = "category_id")
	private Integer categoryId;
	private String title;
	private Date date;
	@Column(name = "closing_date")
	private Date closingDate;
	private Integer time;
	private String memo;
	private Integer progress;

	public Task() {
	}

	public Task(Integer categoryId, String title, Date date, Date closingDate,
			Integer time, String memo) {
		this.categoryId = categoryId;
		this.title = title;
		this.date = date;
		this.closingDate = closingDate;
		this.time = time;
		this.memo = memo;
	}

	public Task(Integer taskId, Integer categoryId, String title, Date date, Date closingDate,
			Integer time, String memo) {
		this.taskId = taskId;
		this.title = title;
		this.date = date;
		this.closingDate = closingDate;
		this.time = time;
		this.memo = memo;
		this.categoryId = categoryId;

	}

	public Task(Integer taskId, Integer userId, Integer categoryId, String title, Date date, Date closingDate,
			Integer time, String memo, Integer progress) {
		this.taskId = taskId;
		this.title = title;
		this.date = date;
		this.closingDate = closingDate;
		this.time = time;
		this.memo = memo;
		this.userId = userId;
		this.categoryId = categoryId;
		this.progress = progress;

	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

}