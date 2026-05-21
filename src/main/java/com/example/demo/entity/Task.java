package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

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
	private Date closing_date;
	private Integer time;
	private String memo;
	@Transient
	private String contact;

	public Task() {
	}

	public Task(String contact, String title, Date date, Date closing_date,
			Integer time, String memo) {
		this.contact = contact;
		this.title = title;
		this.date = date;
		this.closing_date = closing_date;
		this.time = time;
		this.memo = memo;
	}

	public Task(Integer taskId, Integer categoryId, String title, Date date, Date closing_date,
			Integer time, String memo) {

	}

	public Integer getTask_id() {
		return taskId;
	}

	public void setTask_id(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getUser_id() {
		return userId;
	}

	public void setUser_id(Integer userId) {
		this.userId = userId;
	}

	public Integer getCategory_id() {
		return categoryId;
	}

	public void setCategory_id(Integer category_id) {
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

	public Date getClosing_date() {
		return closing_date;
	}

	public void setClosing_date(Date closing_date) {
		this.closing_date = closing_date;
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}