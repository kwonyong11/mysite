<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-3.5.1.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
let startNo = 0;
let isEnd = false;
/* guestbook spa application */
const render = function(vo){
	let html =
		"<li data-no='" + vo.no + "'>" +
		"<strong>" + vo.name + "</strong>" +
		"<p>" + vo.message.replace(/\n/gi, "<br>") + "</p>" +
		"<strong></strong>" + 
		"<a href='' data-no='" + vo.no + "'>삭제</a>" + 
	    "</li>";
	
	$('#list-guestbook').append(html);
}
const fetchList = function(){
	if(isEnd) {
		return;
	}
	$.ajax({
		url: '${pageContext.request.contextPath }/api/guestbook/list/' + startNo,
		async: true,
		type: 'get',
		dataType: 'json',
		data: '',
		success: function(response){
			if(response.result != 'success'){
				console.error(response.message);
				return;
			}
			
			// detect end
			if(response.data.length < 3){
				isEnd = true;
				$('#btn-fetch').prop('disabled', true);
				return;
			}
			
			// rendreing
			response.data.forEach(render);
			
			// startNo = response.data[response.data.length-1]["no"];
			startNo = $('#list-guestbook li').last().data('no') || 0;
		},
		error: function(xhr, status, e){
			console.log(status + ':' + e);
		}
	});	
}
$(function(){
	// 버튼 이벤트(test)
	$('#btn-fetch').click(fetchList);
	
	// 창 스크롤 이벤트
	$(window).scroll(function(){
		const $window = $(this);
		const $document = $(document);
		
		const scrollTop = $window.scrollTop(); 
		const windowHeight = $window.height();
		const documentHeight = $document.height();
		
		if(windowHeight + scrollTop + 10 > documentHeight){
			fetchList();
		}
	});
	
	// 첫번쨰 리스트 가져오기
	fetchList();
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook"></ul>
				<div style='margin:20px 0 0 0'>
					<button id='btn-fetch'>다음가져오기</button>
				</div>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>