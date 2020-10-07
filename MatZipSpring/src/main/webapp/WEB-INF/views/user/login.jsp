<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="sectionContainerCenter">
	<div>
		<div class="msg">${data.msg}</div>
		<form class="frm" action="/user/login" method="post">
			<div><input type="text" name="user_id" placeholder="아이디" value="ddddd"></div>
			<div><input type="password" name="user_pw" placeholder="비밀번호" value="ddddd"></div>
			<div><input type="submit" value="로그인"></div>
		</form>
		<a href="join">회원가입</a>
	</div>	
</div>


