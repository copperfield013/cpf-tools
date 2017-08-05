<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath }" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
	</head>
	<body>
		结果
		<ul style="color: red">
			<c:forEach items="${table.errors }" var="error">
				<li>${error }</li>
			</c:forEach>
		</ul>
		<div>
			<textarea rows="30" cols="40">${table.text }</textarea>
			<c:if test="${fileLink != null }">
				<div>
					<a href="${fileLink }">${fileName }</a>
				</div>
			</c:if>
		</div>
		<table border="1" style="border-collapse: collapse;">
			<c:if test="${table.ths != null }">
				<tr>
					<c:forEach items="${table.ths }" var="th">
						<th>${th }</th>
					</c:forEach>
				</tr>
			</c:if>
			<c:if test="${table.rows != null }">
				<c:forEach items="${table.rows }" var="row">
					<tr>
						<c:forEach items="${row.tds }" var="td">
							<td>${td }</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</body>
</html>