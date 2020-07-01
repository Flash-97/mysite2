package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;
import com.javaex.vo.UserVo;

public class GuestDao {
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

	public List<GuestVo> getList() {
		List<GuestVo> list = new ArrayList();
		getConnection();

		try {
			String query = "SELECT no, name, content, re_date FROM guestbook order by no desc";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			ResultSet rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String content = rs.getString("content");
				String date = rs.getString("re_date");

				GuestVo vo = new GuestVo(no, name, content, date);

				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return list;
	}

	public int deleteList(int no, String pw) {
		int count = 0;
		getConnection();

		try {
			String query = "DELETE from guestbook where no = ? and password = ?";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setInt(1, no);
			pstmt.setString(2, pw);

			count = pstmt.executeUpdate();
			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}

	public int insertLinst(GuestVo vo) {
		int count = 0;
		getConnection();

		try {
			String query = "insert into guestbook values(seq_person_no.nextval, ?, ?, ?, to_char(SYSTIMESTAMP, 'yyyy-mm-dd hh24:mi:ss'))";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContent());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}
}