<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.sql.*" %>    
<%@ page import="org.json.simple.*"%>
<%@ page import="board.board"%> 
<%@ page import="board.boardDAO"%>
<%@ page import="java.util.ArrayList" %>
 
<%
	boardDAO instance = new boardDAO();	
	ArrayList<board> list = instance.getAllList();
	
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	
	for(int i=0; i<list.size(); i++){ 
		JSONObject jObject = new JSONObject();
		
		jObject.put("boardId",list.get(i).getBoardId());
		jObject.put("boardTitle",list.get(i).getBoardTitle());
		jObject.put("boardNickname",list.get(i).getBoardNickname());
		jObject.put("boardDate",list.get(i).getBoardDate().substring(0,11)); //===> 2021-07-25 이렇게 나옴!!!
		jObject.put("boardContent",list.get(i).getBoardContent());
		jObject.put("boardAvailable",list.get(i).getBoardAvailable());
		jArray.add(i,jObject);
	}
	jsonMain.put("Allboard", jArray);
		
	out.print(jsonMain.toJSONString());

%>