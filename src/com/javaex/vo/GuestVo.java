package com.javaex.vo;

public class GuestVo {
	private int no;
	private String name;
	private String password;
	private String content;
	private String date;

	public GuestVo(int no, String name, String content, String date) {
		this.no = no;
		this.name = name;
		this.content = content;
		this.date = date;
	}

	public GuestVo(String name, String content, String password) {
		this.name = name;
		this.content = content;
		this.password = password;
	}

	public GuestVo(int no, String name, String password, String content, String date) {
		this.no = no;
		this.name = name;
		this.password = password;
		this.content = content;
		this.date = date;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "GuestVo [no=" + no + ", name=" + name + ", password=" + password + ", content=" + content + ", date="
				+ date + "]";
	}

}