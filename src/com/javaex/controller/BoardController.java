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
		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");

		if ("read".equals(action)) {
			System.out.println("BoardController > read");

			// 파라미터 값 추출
			int no = Integer.parseInt(request.getParameter("no"));

			// Dao 사용
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getBoard(no);

			// 포워드 --> 데이터전달(요청문서의 바디(attributte))
			request.setAttribute("boardVo", boardVo);
			WebUtil.forword(request, response, "/WEB-INF/views/board/read.jsp");

		} else if ("writeForm".equals(action)) {
			System.out.println("BoardController > writeForm");

			// 포워드
			WebUtil.forword(request, response, "/WEB-INF/views/board/writeForm.jsp");

		} else if ("write".equals(action)) {
			System.out.println("BoardController > write");

			// 파라미터 값 추출 --> vo만들기
			// 글작성자 == 로그인한 사용자 따라서 userNo는 세션에서 가져온다.
			HttpSession session = request.getSession();
			int userNo = ((UserVo) session.getAttribute("authUser")).getNo();

			String title = request.getParameter("title");
			String content = request.getParameter("content");

			BoardVo boardVo = new BoardVo();
			boardVo.setTitle(title);
			boardVo.setContent(content);
			boardVo.setUserNo(userNo);

			// Dao 사용
			BoardDao boardDao = new BoardDao();
			boardDao.write(boardVo);

			// 리다이렉트
			WebUtil.redirect(request, response, "/mysite2/board");

		} else if ("modifyForm".equals(action)) {
			System.out.println("BoardController > modifyForm");

			// 파라미터 값 추출
			int no = Integer.parseInt(request.getParameter("no"));

			// Dao 사용
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getBoard(no);

			// 포워드 --> 데이터전달(요청문서의 바디(attributte))
			request.setAttribute("boardVo", boardVo);
			WebUtil.forword(request, response, "/WEB-INF/views/board/modifyForm.jsp");

		} else if ("modify".equals(action)) {
			System.out.println("BoardController > modify");

			// 파라미터 값 추출 --> vo만들기
			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			BoardVo boardVo = new BoardVo(no, title, content);

			// Dao 사용
			BoardDao boardDao = new BoardDao();
			boardDao.modify(boardVo);

			// 리다이렉트
			WebUtil.redirect(request, response, "/mysite2/board");

		} else if ("delete".equals(action)) {
			System.out.println("BoardController > delete");

			// 파라미터 값 추출
			int no = Integer.parseInt(request.getParameter("no"));

			// Dao 사용
			BoardDao boardDao = new BoardDao();
			boardDao.delete(no);

			// 리다이렉트
			WebUtil.redirect(request, response, "/mysite2/board");

		} else {
			System.out.println("BoardController > default:list");

			List<BoardVo> boardList;
			// 파라미터 값 추출
			String keyword = request.getParameter("keyword");

			// Dao 사용
			BoardDao boardDao = new BoardDao();
			boardList = boardDao.getList(keyword);

			// 포워드 --> 데이터전달(요청문서의 바디(attributte))
			request.setAttribute("boardList", boardList);
			WebUtil.forword(request, response, "/WEB-INF/views/board/list.jsp");

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}