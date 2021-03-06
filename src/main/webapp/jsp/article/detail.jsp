<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
Map<String, Object> articleRow = (Map<String, Object>) request.getAttribute("articleRow");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 상세 페이지</title>
</head>
<body>

	<h1>게시물 상세 페이지</h1>

	<%@ include file="../part/topBar.jspf" %>

	<div>
		번호 :
		<%=(int) articleRow.get("id")%></div>
	<div>
		날짜 :
		<%=(LocalDateTime) articleRow.get("regDate")%></div>
	<div>
		제목 :
		<%=(String) articleRow.get("title")%></div>
	<div>
		내용 :
		<%=(String) articleRow.get("body")%></div>
	<div>
		<a href="modify?id=${param.id }">수정</a> 
		<a href="doDelete?id=${param.id }">삭제</a>
		<a href="list">리스트</a>
	</div>
</body>
</html>