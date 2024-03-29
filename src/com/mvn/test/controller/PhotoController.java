package com.mvn.test.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvn.test.common.ServletFileUtil;
import com.mvn.test.service.PhotoBoardService;
import com.mvn.test.service.impl.PhotoBoardServiceImpl;
import com.mvn.test.vo.PhotoBoardVO;

@WebServlet(name="/PhotoController", urlPatterns= {"/photo/*"})
public class PhotoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private PhotoBoardService pbs = new PhotoBoardServiceImpl();
    private Gson gson = new Gson();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		
		String cmd = request.getRequestURI().substring(7); // /photo/
		String json = "";
		
		if("list".contentEquals(cmd)) {
			json = gson.toJson(pbs.getPhotoList(null));
		} else if("view".contentEquals(cmd)) {
			System.out.println("Controller 성공");
			int pbNum = Integer.parseInt(request.getParameter("pbNum"));
			PhotoBoardVO photo = new PhotoBoardVO();
			
			photo.setPbNum(pbNum);
			System.out.println("photo set : " + photo); // pbNum 입력
			
			json = gson.toJson(pbs.getPhoto(photo)); // service
			System.out.println("pbs.getPhoto(photo) : " + pbs.getPhoto(photo));
			System.out.println("json : " + json); // 여기서 json 형태로 찍히게 됨
		}
		response.getWriter().print(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		String cmd = request.getRequestURI().substring(7); // /photo/
		String cmd = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
		String json = "";
		Map<String, Object> param = ServletFileUtil.parseRequest(request);
//		JsonElement je = gson.toJsonTree(photo);
//		PhotoBoardVO pb = gson.fromJson(je, PhotoBoardVO.class);
		PhotoBoardVO pb = new PhotoBoardVO();
		
		/*
		 * Reflection을 사용하면 공통 작업이 자동으로 처리됨
		 * 전제조건 : 화면에서 보내는 key-value가 VO에 정의된 것과 반드시 모두 일치해야 한다!
		 */
		// PhotoBoardVO pb;
		// try안에 (PhotoBoardVO)ServletFileUtil.parseRequest(request, PhotoBoardVO.class);
		
		if("insert".contentEquals(cmd)) {	
			json = gson.toJson(pbs.insertPhoto(param));
		} else if("update".contentEquals(cmd)) {
			json = gson.toJson(pbs.updatePhoto(pb));
		} else if("delete".contentEquals(cmd)) {
			json = gson.toJson(pbs.deletePhoto(pb));
		}
		response.getWriter().print(json);
	}
	// 넣어주지 않은 것은 null로 들어감
}













