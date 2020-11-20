<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<c:choose>
		<c:when test="${userId eq null}">
			<a href="/user/joinAndLogin">회원가입 또는 로그인</a>
		</c:when>
		<c:when test="${loginUser != null}">
			<a href="/user/joinAndLogin">회원가입 또는 로그인</a>
		</c:when>
		<c:when test="${userId ne null}">
			<div>${nickname}님 오늘도 열공하세요.</div>
			<div><a href="/user/logout">로그아웃</a></div>
		</c:when>
		<c:otherwise>
			<div>${nickname}님 오늘도 열공하세요.</div>
			<div><a href="/user/logout">로그아웃</a></div>
		</c:otherwise>
	</c:choose>
	
	<form action="/swm/start" method="post">
		<div class="button-container">
			<div id="start" class="button">
				<button class="button-text" onclick="startWatch()">START</button>
			</div>
			<div class="button-outline"></div>
		</div>
	</form>
	<!-- 
	<div class="button-container">
	<div class="button">
		<div class="button-text">START</div>
	</div>
	<div class="button-outline"></div>
	</div>
	 -->
</div>

<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script>
	function startWatch() {
		start.innerHTML = ''
		let endBtn = document.createElement('button')
		endBtn.setAttribute('class','button-text')
		endBtn.innerHTML = '1111'
		start.appendChild(endBtn)
	}
	
	var min = newTime.getMinutes() //분
	var sec = newTime.getSeconds() //초

	
</script>