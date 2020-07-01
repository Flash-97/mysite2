package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;

@WebServlet("/guest")
public class GuestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if ("list".equals(action)) {
			GuestDao guestDao = new GuestDao();
			List<GuestVo> list = guestDao.getList();

			request.setAttribute("list", list);

			WebUtil.forword(request, response, "/WEB-INF/views/guestbook/addList.jsp");
		} else if ("deleteForm".equals(action)) {
			WebUtil.forword(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
		} else if ("delete".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			String pw = request.getParameter("pw");

			GuestDao guestDao = new GuestDao();
			guestDao.deleteList(no, pw);

			WebUtil.redirect(request, response, "/mysite/guest?action=list");
		} else if ("insert".equals(action)) {
			String name = request.getParameter("name");
			String pw = request.getParameter("pw");
			String content = request.getParameter("content");

			GuestVo vo = new GuestVo(name, pw, content);
			GuestDao guestDao = new GuestDao();

			guestDao.insertLinst(vo);

			WebUtil.redirect(request, response, "/mysite/guest?action=list");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}