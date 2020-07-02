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

		if ("joinForm".equals(action)) { // 회원가입 폼
			System.out.println("userController > joinForm");

			// 포워드
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinForm.jsp");

		} else if ("join".equals(action)) { // 회원가입
			System.out.println("userController > join");

			// 파라미터 값 추출 --> vo만들기
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			UserVo vo = new UserVo(id, password, name, gender);

			// Dao 사용
			UserDao userDao = new UserDao();
			userDao.insert(vo);

			// 포워드
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinOk.jsp");

		} else if ("loginForm".equals(action)) { // 로그인 폼
			System.out.println("userController > loginForm");

			// 포워드
			WebUtil.forword(request, response, "/WEB-INF/views/user/loginForm.jsp");

		} else if ("login".equals(action)) { // 로그인
			System.out.println("userController > login");

			// 파라미터 값 추출
			String id = request.getParameter("id");
			String password = request.getParameter("password");

			// Dao 사용
			UserDao userDao = new UserDao();
			UserVo authVo = userDao.getUser(id, password);

			if (authVo == null) { // 로그인 > 로그인실패
				System.out.println("로그인실패");

				// 리다이렉트
				WebUtil.redirect(request, response, "/mysite2/user?action=loginForm&result=fail");

			} else { // 로그인 > 로그인성공
				System.out.println("로그인성공");

				// 세션영역 로그인한 사용자 데이터 추가
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authVo);

				// 리다이렉트
				WebUtil.redirect(request, response, "/mysite2/main");
			}

		} else if ("logout".equals(action)) {// 로그아웃 일때
			System.out.println("userController > logout");

			// 세션영역 데이터 제거
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();

			// 리다이렉트
			WebUtil.redirect(request, response, "/mysite2/main");

		} else if ("modifyForm".equals(action)) {
			System.out.println("userController > modifyForm");

			// 사용자 정보를 가져오기 위한 no값은 세션에서 가져온다.
			HttpSession session = request.getSession();
			int no = ((UserVo) session.getAttribute("authUser")).getNo();
			/*
			 * 다른표현 UserVo vo = (UserVo)session.getAttribute("authUser"); int no =
			 * vo.getNo();
			 */

			// Dao 사용
			UserDao userDao = new UserDao();
			UserVo vo = userDao.getUser(no);

			// 포워드 --> 데이터전달(요청문서의 바디(attributte))
			request.setAttribute("userVo", vo);
			WebUtil.forword(request, response, "/WEB-INF/views/user/modifyForm.jsp");

		} else if ("modify".equals(action)) {
			System.out.println("userController > modify");

			// 파라미터 값 추출 --> vo만들기
			// 로그인한 사용자 정보는 세션에서 가져온다.
			HttpSession session = request.getSession();
			int no = ((UserVo) session.getAttribute("authUser")).getNo();
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");

			// 아래처럼 생성자 사용가능 --> 모양이 맞는 생성자 사용추천
			UserVo vo = new UserVo(no, "", password, name, gender);

			// Dao 사용
			UserDao userDao = new UserDao();
			userDao.update(vo);

			// 세션값 업데이트(이름만 수정)
			UserVo sVo = (UserVo) session.getAttribute("authUser");
			sVo.setName(name);

			/*
			 * //비교해볼것 --> no, name 만 세션에 관리하는 정책에 어긋남 //vo에 no,name,password,gender정보가 있음
			 * session.setAttribute("authUser", vo);
			 */

			// 포워드
			WebUtil.redirect(request, response, "/mysite2/main");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}