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
	String content = request.getParameter("content");
	String title = request.getParameter("title");

	boardDAO instance = new boardDAO();
	int result = instance.update(boardId, title, content);

	if(result==-1)
		out.println("update에 실패했습니다.");
	else
		out.println("update에 성공했습니다.");

%>