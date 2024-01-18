package app.lifeone.lgchem;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException
	 */
	@RequestMapping(value = "/.well-known/apple-app-site-association", produces = "application/json; charset=utf8")
	@ResponseBody
	public String getAppleAppSiteAssociation(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String fileName = "static/apple-app-site-association";
		ClassPathResource resource = new ClassPathResource(fileName);
		String body = new String(Files.readAllBytes(resource.getFile().toPath()));

		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + URLEncoder.encode(resource.getFilename(), "UTF-8"));
		response.setHeader("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8");

		return body;
	}

}
