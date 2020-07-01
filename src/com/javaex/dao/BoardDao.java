package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.GuestVo;
import com.javaex.vo.UserVo;

public class BoardDao {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			// System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public List<BoardVo> getList() {
		List<BoardVo> list = new ArrayList();
		getConnection();

		try {
			String query = "SELECT b.no no, b.title title, u.name name, b.hit hit, to_char(b.reg_date,'yyyy-mm-dd hh24:mi') reg_date, b.user_no user_no FROM board b, users u where b.user_no = u.no order by no desc";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			ResultSet rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String date = rs.getString("reg_date");
				int user_no = rs.getInt("user_no");

				BoardVo vo = new BoardVo(no, title, name, hit, date, user_no);
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return list;
	}

	public int insert(BoardVo vo) {
		int count = 0;
		getConnection();

		try {
			String query = "insert into board VALUES(seq_board_no.nextval, ?, ?, 0, sysdate, ?)";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getUser_no());

			count = pstmt.executeUpdate(); // 쿼리문 실행
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}

	public int updateHit(int boardNo) {
		int count = 0;
		getConnection();

		try {
			String query = "UPDATE board set hit = hit + 1 where no = ?";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setInt(1, boardNo);

			count = pstmt.executeUpdate(); // 쿼리문 실행
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}

	public BoardVo read(int boardNo) {
		BoardVo vo = null;
		getConnection();

		try {
			String query = "SELECT b.no no, u.name name, b.hit hit, b.reg_date reg_date, b.title title, b.content content, b.user_no user_no FROM board b, users u where b.user_no = u.no and b.no = ?";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setInt(1, boardNo);

			ResultSet rs = pstmt.executeQuery();

			rs.next();

			int no = rs.getInt("no");
			String name = rs.getString("name");
			int hit = rs.getInt("hit");
			String date = rs.getString("reg_date");
			String title = rs.getString("title");
			String content = rs.getString("content");
			int user_no = rs.getInt("user_no");

			vo = new BoardVo(no, name, hit, date, title, content, user_no);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return vo;
	}

	public int delete(int boardNo) {
		int count = 0;
		getConnection();

		try {
			String query = "delete from board where no=?";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setInt(1, boardNo);

			count = pstmt.executeUpdate(); // 쿼리문 실행
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}

	public int modify(BoardVo vo) {
		int count = 0;
		getConnection();

		try {
			String query = "update board set title = ?, content = ? where no = ?";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNo());
			System.out.println(vo.toString());
			count = pstmt.executeUpdate(); // 쿼리문 실행
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}
}