package com.javaex.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtil {
	
	//포워드
	public static void forword(HttpServletRequest req, HttpServletResponse res, String path) throws ServletException, IOException {
		
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, res);
	}
	//리다이렉트
	
	public static void redirect(HttpServletRequest req, HttpServletResponse res, String url) throws IOException {
		res.sendRedirect(url);
	}
}