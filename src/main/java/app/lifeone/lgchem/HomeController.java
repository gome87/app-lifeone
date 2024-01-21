package app.lifeone.lgchem;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		String rootPath1 = System.getProperty("user.dir");
		logger.info("##### ROOT 경로1 : {} #####", rootPath1);

		String rootPath2 = request.getRealPath("/");
		logger.info("##### ROOT 경로2 : {} #####", rootPath2);

		logger.info("##### LifeOne File Demo Test #####");

		// 이동화면 설정
		ModelAndView mv = new ModelAndView();
		mv.setViewName("fileDemo1");

		return mv;
	}

}
