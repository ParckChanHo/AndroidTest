<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.sql.*" %>    
<%@ page import="org.json.simple.*"%>
<%@ page import="board.board"%> 
<%@ page import="board.boardDAO"%>
<%@ page import="java.util.ArrayList" %>

<%
	request.setCharacterEncoding("utf-8");
	String str_boardId = request.getParameter("boardId");
	int boardId = Integer.parseInt(str_boardId);
	
	boardDAO instance = new boardDAO();
	board bbs = instance.getBoard(boardId);

	JSONObject jObject = new JSONObject();
	jObject.put("boardId",bbs.getBoardId());
	jObject.put("boardTitle",bbs.getBoardTitle());
	jObject.put("boardNickname",bbs.getBoardNickname());
	jObject.put("boardDate",bbs.getBoardDate().substring(0,11)); //===> 2021-07-25 이렇게 나옴!!!
	jObject.put("boardContent",bbs.getBoardContent());
	jObject.put("boardAvailable",bbs.getBoardAvailable());

	out.print(jObject.toJSONString());

%>