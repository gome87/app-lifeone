<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta charset="UTF-8">
<title>Polalis Viewer Connector</title>
<style type="text/css">
* {
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}

body {
	margin: 0;
	padding: 0;
	font-weight: 400;
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
	font-size: 1rem;
	line-height: 1.58;
	color: #333;
	background-color: #f4f4f4;
}
</style>
<script type="text/javascript">
	/**
	 * 화면호출시 폴라리스 뷰어 호출
	 */
	function fn_init(){

		var visitTime = (new Date()).getTime();

		setTimeout(function() {
			if ((new Date()).getTime() - visitTime < 3000) {
				fn_urlCall();
				console.log("##### 호출1 #####");
			}
		}, 2500);

		setTimeout(function() {
			location.href = fn_PolarisViewerUrl("polaris://open?");
			console.log("##### 호출2 #####");
		}, 0);

		//var iframe = document.createElement('iframe');
		//iframe.style.visibility = 'hidden'; // 보이지 않는 iframe으로 스킴을 호출한다.
		//iframe.src = fn_PolarisViewerUrl("polaris://open?");
		//document.body.appendChild(iframe);
		//document.body.removeChild(iframe); // back 호출시 캐싱될 수 있으므로 제거
	}

	/**
	 * 폴라리스 뷰어 호출
	 */
	function fn_PolarisViewerUrl(sTpNm) {
		console.log("##### 폴라리스 뷰어 호출 시작 #####");

		// 수신값
		var sUrl = "${RTN_VAL}"

		// Url 변수 암호화
		var sSessionKey = fn_encode("test");

		// IOS앱 호출 URL
		var sFullPath = "";
		if (fn_lenCheck(sUrl)) {
			sFullPath = sTpNm + sUrl + "&sk=" + sSessionKey;
		}
		console.log("##### 전송 URL : " + sFullPath);
		return sFullPath;
	}

	/**
	 * 외부 App 호출
	 */
	function fn_urlCall(sVal) {
		location.href = "itms-services://?action=download-manifest&url=https://dl.dropboxusercontent.com/s/sls1j1vzyrmpbs1/manifest.plist";
	};

	/**
	 * Base64/encodeURI 암호화
	 */
	function fn_encode(sEncodeText) {
		var sEncodeVl = encodeURIComponent(btoa(unescape(encodeURIComponent(sEncodeText))));
		console.log("##### 변경완료 값 : " + sEncodeVl);
		return sEncodeVl;
	}

	/**
	 * Base64/encodeURI 길이체크
	 */
	function fn_lenCheck(sValue) {
		if (sValue.trim().length < 1) {
			return false;
		}
		return true;
	}
</script>
</head>
<body onload="fn_init();">
	<div>
		<button onclick="fn_urlCall()">Polaris Viewer App 다운로드</button>
	</div>
</body>
</html>
