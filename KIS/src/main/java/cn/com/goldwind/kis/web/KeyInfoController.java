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
	@RequestMapping(value = "index")
	public ModelAndView index(ModelMap map, String termType) {
		List<String> keyInfoTypeList = keyInfoService.findTermTypes();
		
		List<KeyInfo> keyInfoList = null;
		if (null == termType || "".equalsIgnoreCase(termType)) {
			keyInfoList = keyInfoService.findByTermType(keyInfoTypeList.get(0));
		} else {
			keyInfoList = keyInfoService.findByTermType(termType);
		}

		map.put("keyInfoTypeList", keyInfoTypeList);
		map.put("keyInfoList", keyInfoList);
		return new ModelAndView("index");
	}

//	/**
//	 * 按分类搜索关键词
//	 * 
//	 * @param map
//	 * @param findContent
//	 * @return
//	 */
//	@RequestMapping(value = "searchByTermType", method = RequestMethod.POST)
//	public ModelAndView searchByTermType(ModelMap map, String termType) {
//		List<String> keyInfoTypeList = keyInfoService.findTermTypes();
//		List<KeyInfo> keyInfoList = keyInfoService.findByTermType(termType);
//
//		map.put("keyInfoTypeList", keyInfoTypeList);
//		map.put("keyInfoList", keyInfoList);
//		return new ModelAndView("index");
//	}

	/**
	 * 搜索页面跳转
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
	public ModelAndView searchAll(ModelMap map, String findContent) {
		List<KeyInfo> keyInfoList = keyInfoService.findByKeyInfo(findContent);
		map.put("keyInfoList", keyInfoList);
		return new ModelAndView("keyinfo/list");
	}

}
