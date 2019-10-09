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
	public ModelAndView index(ModelMap map, String termType, String findContent) {
		// 查询所有术语类型
		List<String> keyInfoTypeList = keyInfoService.findTermTypes();

		// 如果搜索内容不空，按照关键词查询术语
		List<KeyInfo> keyInfoList = null;
		if (null != findContent && !"".equalsIgnoreCase(findContent)) {
			keyInfoList = keyInfoService.findByKeyInfo(findContent);
		} else {
			// 如果没有关键词，并且没有电机类型索引，默认搜索第一个索引类型的术语
			if (null == termType || "".equalsIgnoreCase(termType)) {
				keyInfoList = keyInfoService.findByTermType(keyInfoTypeList.get(0));
			} else {
				// 按术语类型搜索关键词
				keyInfoList = keyInfoService.findByTermType(termType);
			}
		}

		if (null != keyInfoTypeList && keyInfoTypeList.size() > 0) {
			map.put("keyInfoTypeList", keyInfoTypeList);
		}
		if (null != keyInfoList && keyInfoList.size() > 0) {
			map.put("keyInfoList", keyInfoList);
		}
		return new ModelAndView("index");
	}

}
