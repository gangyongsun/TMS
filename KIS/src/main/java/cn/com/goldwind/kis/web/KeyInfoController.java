package cn.com.goldwind.kis.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.goldwind.kis.entity.KeyInfo;
import cn.com.goldwind.kis.service.KeyInfoService;

@Controller
@RequestMapping("/kis")
public class KeyInfoController {
	@Autowired
	KeyInfoService keyInfoService;

	/**
	 * 初始页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "index")
	public ModelAndView index(ModelMap map, String termType) {
		List<String> keyInfoTypeList = keyInfoService.findTermTypes();

		List<KeyInfo> keyInfoList = null;
		if (null == termType || "".equalsIgnoreCase(termType)) {
			keyInfoList = keyInfoService.findByTermType(keyInfoTypeList.get(0));
		} else {
			keyInfoList = keyInfoService.findByTermType(termType);
		}

		if (null != keyInfoTypeList && keyInfoTypeList.size() > 0) {
			map.put("keyInfoTypeList", keyInfoTypeList);
		}
		if (null != keyInfoList && keyInfoList.size() > 0) {
			map.put("keyInfoList", keyInfoList);
		}
		return new ModelAndView("index");
	}

	/**
	 * 搜索关键词
	 * 
	 * @param map
	 * @param findContent
	 * @return
	 */
	@RequestMapping(value = "searchAll")
	public ModelAndView searchAll(ModelMap map, String findContent) {
		List<String> keyInfoTypeList = keyInfoService.findTermTypes();

		List<KeyInfo> keyInfoList = keyInfoService.findByKeyInfo(findContent);
		if (null != keyInfoList && keyInfoList.size() > 0) {
			map.put("keyInfoList", keyInfoList);
		}
		if (null != keyInfoTypeList && keyInfoTypeList.size() > 0) {
			map.put("keyInfoTypeList", keyInfoTypeList);
		}
		return new ModelAndView("index");
	}

}
