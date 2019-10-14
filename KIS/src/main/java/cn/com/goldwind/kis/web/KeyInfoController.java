package cn.com.goldwind.kis.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	 * @param map
	 * @param termType
	 * @param findContent
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
		}else {
			if (null != termType || !"".equalsIgnoreCase(termType)) {
				// 按术语类型搜索关键词
				keyInfoList = keyInfoService.findByTermType(termType);
			} 
			// 如果没有关键词，并且没有电机类型索引，默认搜索第一个索引类型的术语
//			else {
//				keyInfoList = keyInfoService.findByTermType(keyInfoTypeList.get(0));
//			}
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
	 * 查询术语详情
	 * 
	 * @param map
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "showDetail")
	public ModelAndView index(ModelMap map, String id) {
		KeyInfo keyInfo = keyInfoService.findTermById(id);
		if (null != keyInfo) {
			map.put("keyInfo", keyInfo);
		}
		return new ModelAndView("detail");
	}

	/**
	 * 转到图表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "echart")
	public String gotoSummary() {
		return "echarts";
	}

	/**
	 * 查询术语类型对应的数量
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "summary")
	@ResponseBody
	public Map<String, Integer> summary(ModelMap map) {
		// 查询所有术语类型
		List<String> keyInfoTypeList = keyInfoService.findTermTypes();

		Map<String, Integer> termTypeNumMap = new HashMap<String, Integer>();

		if (null != keyInfoTypeList && keyInfoTypeList.size() > 0) {
			for (String keyInfoType : keyInfoTypeList) {
				int num = keyInfoService.getNumByType(keyInfoType);
				termTypeNumMap.put(keyInfoType, num);
			}
		}
		return termTypeNumMap;
	}

}
