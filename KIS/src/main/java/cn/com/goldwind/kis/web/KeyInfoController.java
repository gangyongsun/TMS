package cn.com.goldwind.kis.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(value = "list")
	public ModelAndView index() {
		return new ModelAndView("keyinfo/list");
	}

	/**
	 * 搜索关键词
	 * 
	 * @param map
	 * @param findContent
	 * @return
	 */
	@RequestMapping(value = "searchAll", method = RequestMethod.POST)
	public ModelAndView list(ModelMap map, String findContent) {
		List<KeyInfo> keyInfoList = keyInfoService.findByKeyInfo(findContent);
		map.put("keyInfoList", keyInfoList);
		return new ModelAndView("keyinfo/list");
	}

}
