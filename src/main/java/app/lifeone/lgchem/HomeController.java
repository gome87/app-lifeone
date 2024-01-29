package app.lifeone.lgchem;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);


	/**
	 * 기본페이지
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("##### LifeOne File Demo #####");

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate );

		return "home";
	}

	/**
	 * apple-app-site-association 설정 파일
	 *
	 * @throws IOException
	 */
	@RequestMapping(value = "/apple-app-site-association", produces = "application/json; charset=utf8")
	@ResponseBody
	public String getAppleAppSiteAssociation(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String fileName = "static/apple-app-site-association";
		ClassPathResource resource = new ClassPathResource(fileName);
		String body = new String(Files.readAllBytes(resource.getFile().toPath()));

		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + URLEncoder.encode(resource.getFilename(), "UTF-8"));
		response.setHeader("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8");

		return body;
	}

	/**
	 * File Demo 사이트
	 *
	 * @param HttpServletRequest request
	 * @param ModelAndView model
	 * @return ModelAndView
	 * @throws Exception
	 * @since 2024. 01. 21
	 * @author 김영우
	 */
	@RequestMapping(value = "/fileDemo1")
	public ModelAndView retrieveFileDemo1(HttpServletRequest request, ModelAndView model) throws Exception {

		logger.info("##### LifeOne File Demo Test #####");

		// 이동화면 설정
		ModelAndView mv = new ModelAndView();
		mv.setViewName("fileDemo1");

		return mv;
	}

	/**
	 * PolalisViewer 연결 부분
	 *
	 * @param HttpServletRequest req
	 * @param HttpServletResponse rep
	 * @return ModelAndView
	 * @throws Exception
	 * @since 2024. 01. 30
	 * @author 김영우
	 */
	@RequestMapping(value = "/polalisViewer", method = { RequestMethod.GET })
	public ModelAndView retrievePolalisViewer(HttpServletRequest req, HttpServletResponse res) throws Exception {

		logger.info("##### 값 테스트 1 : {} #####", req.getQueryString());

		// 쿼리 스트링값 가져오기
		String sQueryString = req.getQueryString();

		// 값이 없는 경우 에러 페이지로 이동
		if(StringUtils.isBlank(sQueryString)) {
			logger.info("##### 수신되어진 값 없음 #####");
			return null;
		}

		// 상세 파라미터 검증
		String[] arrQueryString = StringUtils.split(sQueryString, "&");
		for(int nIdx=0; nIdx < arrQueryString.length; nIdx++) {
			String[] arrValues = StringUtils.split(arrQueryString[nIdx], "=");
			if(StringUtils.isBlank(arrValues[0]) || StringUtils.isBlank(arrValues[1])) {
				logger.info("##### 파라미터 확인 필요 #####");
			}
		}

		// 이동화면 설정
		ModelAndView mv = new ModelAndView();
		mv.setViewName("polalis");
		mv.addObject("RTN_VAL", sQueryString);
		return mv;
	}

}
