<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<% 
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");

	if(id.equals("mjc") && pwd.equals("inbo")){
		out.println("ȸ���� �ݰ����ϴ�."); //Ŭ���̾�Ʈ�� ������
	}
	else {
		out.println("���̵� �Ǵ� ��й�ȣ�� ����ġ�մϴ�.");
	}

%>