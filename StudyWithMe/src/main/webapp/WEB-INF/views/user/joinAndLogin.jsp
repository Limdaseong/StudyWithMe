<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="container" id="container">
	<div class="form-container sign-up-container">
		<form action="/user/join" method="post">
			<h1>계정 만들기</h1>
			<div class="social-container">
				<a href="https://kauth.kakao.com/oauth/authorize?client_id=dbf538005142f2a1c3d0acabf734fa5f&redirect_uri=http://localhost:8080/user/oauth&response_type=code"></a>
				<a href="#" class="social2"><i class="fab fa-google-plus-g"></i></a>
				<a href="#" class="social3"><i class="fab fa-linkedin-in"></i></a>
			</div>
			<span>또는 등록을 위한 이메일을 사용하세요</span>
			<input type="text" name="nm" placeholder="별명" />
			<input type="email" name="user_email" onkeyup="chkEmail()" placeholder="이메일"/>
			<div id="EmailChkResult" class="msg">dd</div>
			<!-- 다쳤을 때 이메일 중복확인 되게하는 p태그 만들기 -->
			<input type="password" name="user_pw" placeholder="비밀번호"/>
			<input type="password" name="user_pwre" placeholder="비밀번호 확인">
			<!-- 8자 이상 15자 이하 특수문자 하나 들어갔는지 유효성 검사하는거 만들기 -->
			
			<button>회원가입</button>
		</form>
	</div>
	<div class="form-container sign-in-container">
		<form action="/user/login" method="post">
			<h1>로그인</h1>
			<div class="social-container">
				<a href="#" class="social"><i class="fab fa-facebook-f"></i></a>
				<a href="#" class="social"><i class="fab fa-google-plus-g"></i></a>
				<a href="#" class="social"><i class="fab fa-linkedin-in"></i></a>
			</div>
			<span>또는 계정을 사용하세요</span>
			<input type="email" name="user_email" placeholder="이메일" />
			<input type="password" name="user_pw" placeholder="비밀번호" />
			<a href="#">비밀번호를 잊어버리셨나요?</a>
			<button>로그인</button>
		</form>
	</div>
	<div class="overlay-container">
		<div class="overlay">
			<div class="overlay-panel overlay-left">
				<h1>돌아온 것을 환영해요!</h1>
				<p>회원님의 정보를 이용하여 로그인하세요</p>
				<button class="ghost" id="signIn">로그인</button>
			</div>
			<div class="overlay-panel overlay-right">
				<h1>안녕하세요!</h1>
				<p>세부사항을 입력하고 함께 공부해요</p>
				<button class="ghost" id="signUp">회원가입</button>
			</div>
		</div>
	</div>
</div>
<script src="http://developers.kakao.com/sdk/js/kakao.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

<script>
	const signUpButton = document.getElementById('signUp');
	const signInButton = document.getElementById('signIn');
	const container = document.getElementById('container');
	
	signUpButton.addEventListener('click', () => {
		container.classList.add("right-panel-active");
	});
	
	signInButton.addEventListener('click', () => {
		container.classList.remove("right-panel-active");
	});
	
	function chkEmail() {
		const user_id = frm.user_id.value
		axios.post('/user/ajaxEmailChk', {
			user_email : user_email // post 쓸거면 params:{}를 지워준다
		}).then(function(res) {
			if(res.data == '0') {
				EmailChkResult.innerText = '이메일 사용 가능!'
				//joinButton.type = "submit";
			} else if(res.data == '1') {
				EmailChkResult.innerText = '이메일 중복! 사용 불가!'
			}
		})
	}
</script>