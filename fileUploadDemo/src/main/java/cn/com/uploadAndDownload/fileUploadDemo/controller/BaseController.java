package cn.com.uploadAndDownload.fileUploadDemo.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * super controller
 * 
 * @author alvin
 *
 */
public class BaseController {
	protected final static Logger logger = Logger.getLogger(BaseController.class);

	protected Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

	public static String URL404 = "/404.html";

	public ModelAndView redirect(String redirectUrl, Map<String, Object>... parament) {
		ModelAndView view = new ModelAndView(new RedirectView(redirectUrl));
		if (null != parament && parament.length > 0) {
			view.addAllObjects(parament[0]);
		}
		return view;
	}

	public ModelAndView redirect404() {
		return new ModelAndView(new RedirectView(URL404));
	}

}
