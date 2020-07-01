package com.javaex.vo;

public class BoardVo {
	private int no;
	private String title;
	private String content;
	private int hit;
	private String date;
	private int user_no;
	private String name;

	public BoardVo(String title, String content, int no) {
		this.no = no;
		this.title = title;
		this.content = content;
	}

	public BoardVo(int user_no, String title, String content) {
		this.user_no = user_no;
		this.title = title;
		this.content = content;
	}

	public BoardVo(int no, String name, int hit, String date, String title, String content, int user_no) {
		this.no = no;
		this.name = name;
		this.hit = hit;
		this.date = date;
		this.title = title;
		this.content = content;
		this.user_no = user_no;
	}

	public BoardVo(int no, String title, String name, int hit, String date, int user_no) {
		this.no = no;
		this.title = title;
		this.name = name;
		this.hit = hit;
		this.date = date;
		this.user_no = user_no;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getUser_no() {
		return user_no;
	}

	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", date=" + date
				+ ", user_no=" + user_no + "]";
	}

}