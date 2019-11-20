package com.mvn.test.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

@WebServlet("/file")
public class FileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int memSize = 1024 * 1024 * 5; // 5 MB (기본 단위  bit ==> byte ==> KB) // Server, 들어오는 접속자 수, 파일 업로드를 다 고려해서 결정해야 함
		int totalSize = 1024 * 1024 * 50; // 50MB
 		int fileSize = 1024 * 1024 * 10; // 10MB
 		
 		DiskFileItemFactory dfif = new DiskFileItemFactory();
 		dfif.setSizeThreshold(memSize); // 메모리에 기억해 놓는다 
 		
 		// 자아분열 ㅋㅋ 여러 군데서 입력을 받는 것 ==> thread
 		dfif.setRepository(new File(System.getProperty("java.io.tmpdir"))); // 5MB를 일단 저장하고 머리를 비우기 (총 용량이 5MB이라는 게 아니라) // 잠깐 기억하려고
 		
 		ServletFileUpload sfu = new ServletFileUpload(dfif); // 비운 곳에 다시 저장
 		sfu.setFileSizeMax(fileSize); // 각각의 file max
 		sfu.setSizeMax(totalSize); // 총 size max
 		
 		boolean isForm = ServletFileUpload.isMultipartContent(request);
 		System.out.println(isForm);
	}
	
	public static void main(String[] args) {
		String tmp = System.getProperty("java.io.tmpdir");
		System.out.println(tmp); // C:\Users\ADMINI~1\AppData\Local\Temp\
	}
}
