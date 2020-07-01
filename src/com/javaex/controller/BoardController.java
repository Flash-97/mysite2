package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if ("list".equals(action)) {
			BoardDao boardDao = new BoardDao();
			List<BoardVo> list = boardDao.getList();

			request.setAttribute("list", list);

			WebUtil.forword(request, response, "/WEB-INF/views/board/list.jsp");
		} else if ("writeForm".equals(action)) {
			WebUtil.forword(request, response, "WEB-INF/views/board/writeForm.jsp");
		} else if ("write".equals(action)) {
			HttpSession session = request.getSession();
			UserVo sessionVo = (UserVo) session.getAttribute("authUser");
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			BoardVo vo = new BoardVo(sessionVo.getNo(), title, content);

			BoardDao boardDao = new BoardDao();
			boardDao.insert(vo);

			WebUtil.redirect(request, response, "/mysite2/board?action=list");
		} else if ("read".equals(action)) {
			int boardNo = Integer.parseInt(request.getParameter("boardNo"));

			BoardDao boardDao = new BoardDao();
			boardDao.updateHit(boardNo);
			BoardVo vo = boardDao.read(boardNo);

			request.setAttribute("bookUser", vo);

			WebUtil.forword(request, response, "WEB-INF/views/board/read.jsp");
		} else if ("delete".equals(action)) {
			int boardNo = Integer.parseInt(request.getParameter("boardNo"));

			BoardDao boardDao = new BoardDao();

			boardDao.delete(boardNo);

			WebUtil.redirect(request, response, "/mysite2/board?action=list");
		} else if ("modifyForm".equals(action)) {
			int boardNo = Integer.parseInt(request.getParameter("boardNo"));

			BoardDao boardDao = new BoardDao();
			BoardVo vo = boardDao.read(boardNo);

			request.setAttribute("bookUser", vo);
			WebUtil.forword(request, response, "WEB-INF/views/board/modifyForm.jsp");
		} else if ("modify".equals(action)) {
			int boardNo = Integer.parseInt(request.getParameter("boardNo"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			BoardVo vo = new BoardVo(title, content, boardNo);
			BoardDao boardDao = new BoardDao();

			boardDao.modify(vo);

			WebUtil.redirect(request, response, "/mysite2/board?action=list");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}