
package com.javaex.dao;

import com.javaex.vo.UserVo;

public class DaoTest {

	public static void main(String[] args) {

		UserDao userDao = new UserDao();

		UserVo vo = new UserVo("hi", "1234", "이정재", "male");
		userDao.insert(vo);

	}

}