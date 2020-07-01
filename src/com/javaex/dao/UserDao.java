package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
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

	public int insert(UserVo vo) {
		int count = 0;
		getConnection();

		try {
			String query = "insert INTO users VALUES(seq_users_no.nextval, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getGender());

			count = pstmt.executeUpdate(); // 쿼리문 실행
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}

	public UserVo getUser(String id, String pw) {
		UserVo userVo = null;
		getConnection();

		try {
			String query = "SELECT no, ";
				   query+= "       name ";
				   query+= "FROM users where id = ? ";
				   query+= "and password = ?";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			pstmt.setString(1, id);
			pstmt.setString(2, pw);

			ResultSet rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");

				userVo = new UserVo();

				userVo.setNo(no);
				userVo.setName(name);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return userVo;
	}

	public UserVo getUser(int no) {
		UserVo vo = null;
		getConnection();

		try {
			String query = "select id,";
				   query+= "       password,";
				   query+= "       name,";
				   query+= "       gender";
				   query+= " from users where no = ?";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			pstmt.setInt(1, no);

			ResultSet rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");

				vo = new UserVo(no, id, password, name, gender);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();

		return vo;
	}

	public int userUpdate(UserVo vo) {
		int count = 0;
		getConnection();

		try {
			String query = " update users set name = ?,";
				   query+= " 		          password = ?,";
				   query+= "                  gender = ?";
				   query+= " where            no = ?";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getGender());
			pstmt.setInt(4, vo.getNo());

			count = pstmt.executeUpdate(); // 쿼리문 실행
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}
}
