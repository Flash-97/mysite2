package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		if ("joinForm".equals(action)) {
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinForm.jsp");
		} else if ("join".equals(action)) {
			System.out.println("join");

			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			UserVo vo = new UserVo(id, pw, name, gender);
			UserDao userDao = new UserDao();
			userDao.insert(vo);
			System.out.println(vo.toString());

			WebUtil.forword(request, response, "/WEB-INF/views/user/joinOk.jsp");
		} else if ("loginForm".equals(action)) {
			System.out.println("test");
			WebUtil.forword(request, response, "/WEB-INF/views/user/loginForm.jsp");
		} else if ("login".equals(action)) {
			System.out.println("login");
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");

			UserDao userDao = new UserDao();
			UserVo authvo = userDao.getUser(id, pw);

			if (authvo == null) {
				System.out.println("로그인 실패");
				WebUtil.redirect(request, response, "/mysite2/user?action=loginForm&result=fail");
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authvo);

				WebUtil.redirect(request, response, "/mysite2/main");
			}
		} else if ("logout".equals(action)) {
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			WebUtil.redirect(request, response, "/mysite2/main");
		} else if ("modifyForm".equals(action)) {
			HttpSession session = request.getSession();
			UserVo vo = (UserVo) session.getAttribute("authUser");
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(vo.getNo());

			request.setAttribute("userVo", userVo);
			WebUtil.forword(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		} else if ("modify".equals(action)) {
			HttpSession session = request.getSession();
			UserVo sessionVo = (UserVo) session.getAttribute("authUser");

			System.out.println("modify");
			int no = sessionVo.getNo();
			String id = sessionVo.getId();
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			UserVo vo = new UserVo(no, id, pw, name, gender);
			UserDao userDao = new UserDao();

			System.out.println(vo.toString());

			userDao.userUpdate(vo);

//			UserVo authvo = new UserVo();
//			authvo.setNo(no);
//			authvo.setName(name);

//			session.setAttribute("authUser", authvo);
			sessionVo.setName(name);
//			System.out.println("****************"+authvo.getName());
			WebUtil.redirect(request, response, "/mysite2/main");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}