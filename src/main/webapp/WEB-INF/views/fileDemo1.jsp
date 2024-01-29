<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no, user-scalable=no, minimal-ui">
		<title>File Demo</title>
		<script type="text/javascript" charset='UTF-8'>
			/**
			 * 폴라리스 뷰어 호출
			 */
			function fnPolarisViewerCall(sTpNm, sUrl, sFileName){
				console.log("##### 폴라리스 뷰어 호출 시작 #####");

				// Url 변수 암호화
				var sUrl2       = fnEncode(sUrl);
				var sFileName   = fnEncode(sFileName);
				var sSessionKey = fnEncode("test");

				// IOS앱 호출 URL
				var sFullPath = "";
				if(fnLenCheck(sTpNm)){
					sFullPath = sTpNm;
				}
				if(fnLenCheck(sUrl2)){
					sFullPath = sFullPath + "?url=" + sUrl2;
				}
				if(fnLenCheck(sFileName)){
					sFullPath = sFullPath + "&filename=" + sFileName;
				}
				if(fnLenCheck(sFileName)){
					sFullPath = sFullPath + "&sk=" + sSessionKey;
				}

				console.log("##### 전송 URL : " + sFullPath);

				//window.location.href = sFullPath;

				console.log("##### 폴라리스 뷰어 호출 종료 #####");

				return sFullPath;
			}

			/**
			  * Base64/encodeURI 암호화
			  */
			function fnEncode(sEncodeText){
				var sEncodeVl = encodeURIComponent(btoa(unescape(encodeURIComponent(sEncodeText))));

				console.log("##### 변경완료 값 : " + sEncodeVl);

				return sEncodeVl;
			}

			/**
			  * Base64/encodeURI 길이체크
			  */
			function fnLenCheck(sValue){
				if(sValue.trim().length < 1) return false;
				return true;
			}

			/**
			  * 외부 App 호출
			  */
			function inAppBrowserOut(sVal){
				//location.href=sVal;

				//var link = document.createElement("a");
				//link.setAttribute("href"  , sVal);
				//link.setAttribute("target", "_blank");
				//link.setAttribute("rel"   , "noopener noreferrer");
				//document.body.appendChild(link);
				//link.click();
				//document.body.removeChild(link);
			};
		</script>
	</head>
	<body>
		<h2>Universal Link Test</h2>
		<button onclick="inAppBrowserOut(fnPolarisViewerCall('polaris://open', 'https://blog.kakaocdn.net/dn/Ugubl/btrHTWWqcC1/hd6fIBJaEkwmogkpbqS3kk/1%EB%B6%84%ED%8C%8C%EC%9D%B4%EC%8D%AC_%EA%B0%95%EC%9D%98%EC%9E%90%EB%A3%8C_%EC%A0%84%EC%B2%B4.pdf?attach=1&knm=tfile.pdf', 'TEST1'))">링크1(PDF)</button>
		<button onclick="inAppBrowserOut(fnPolarisViewerCall('polaris://open', 'https://www.kf.or.kr/common/nttFileDownload.do?fileKey=63f76aa67d642f9c01b2c95203eced54', 'TEST2'))">링크2(HWP)</button>
		<button onclick="inAppBrowserOut(fnPolarisViewerCall('polaris://open', 'https://download.blog.naver.com/open/6cf970c0d0818854799efac6f0136714b3e013febb/BiHMWqWgRKiPnw6NqsgNrJpFcWrKoS0_Y7SBJj8nK7xtH1_rOkE5leREKwiu42V1W_UyBsDy6YXhnkrF2yJ3BQ/%28%EC%A3%BC%29%EC%97%91%EC%85%80%ED%85%8C%ED%81%AC-%EC%8B%A4%EC%8A%B5%EC%98%88%EC%A0%9C%281%29.xlsx', 'TEST3'))">링크3(XLSX)</button>
		<button onclick="inAppBrowserOut(fnPolarisViewerCall('polaris;//open', 'https://scc.sogang.ac.kr/Download?pathStr=NTMjIzUyIyM1MCMjMTI0IyMxMDQjIzExNiMjOTcjIzgwIyMxMDEjIzEwOCMjMTA1IyMxMDIjIzM1IyMzMyMjMzUjIzUwIyMxMjQjIzEyMCMjMTAxIyMxMDAjIzExMCMjMTA1IyMzNSMjMzMjIzM1IyM0OCMjNTEjIzU1IyM1MCMjNTMjIzU1IyMxMjQjIzEwMCMjMTA1IyMxMDcjIzExMg==&fileName=%EC%99%B8%EA%B5%AD%EC%9D%B8%EA%B0%95%EC%82%AC+%EB%AA%A8%EC%A7%91+%EC%A7%80%EC%9B%90%EC%84%9C+%EC%96%91%EC%8B%9D%5B%EC%9B%8C%EB%93%9C%5D.docx&gubun=board', 'TEST4'))">링크4(DOCX)</button>
		<button onclick="inAppBrowserOut(fnPolarisViewerCall('polaris://open', 'https://www.lettercontest.kr/2023%ED%8E%B8%EC%A7%80%EC%93%B0%EA%B8%B0%EA%B3%B5%EB%AA%A8%EC%A0%84%20%EA%B3%B5%EB%AA%A8%EC%A0%84%20%EA%B0%95%EC%9D%98.pptx', 'TEST5'))">링크5(PPTX)</button>

		<h2>외부 App 호출</h2>
		<button onclick="inAppBrowserOut('kakaotalk://web/openExternal?')">링크1(카카오톡)</button>
		<button onclick="inAppBrowserOut('x-web-search://?')">링크2(사파리)</button>
		<button onclick="inAppBrowserOut('https://naver.me/IDbRUn5o')">링크3</button>
		<button onclick="inAppBrowserOut('https://naver.worksmobile.com/mail/write?version=8&to=user1@worksmobile.com;user2@worksmobile.com&cc=user3@worksmobile.com&subject=testsubject&body=testbody')">링크4</button>
		<button onclick="inAppBrowserOut('https://www.heydealer.com/chat')">링크5</button>
		<button onclick="inAppBrowserOut('http://naverapp.naver.com/default/?version=1')">링크6</button>

		<h2>프로그램 설치</h2>
		<button onclick="inAppBrowserOut('itms-services://?action=download-manifest&url=https://dl.dropboxusercontent.com/s/sls1j1vzyrmpbs1/manifest.plist')">폴라리스 뷰어 설치</button>
	</body>

</html>
