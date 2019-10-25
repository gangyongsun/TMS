package cn.com.goldwind.kis.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.goldwind.kis.entity.AccessSummary;
import cn.com.goldwind.kis.entity.KeyInfo;
import cn.com.goldwind.kis.entity.KeyInfoNonSearch;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.service.KeyInfoService;
import cn.com.goldwind.kis.service.NonSearchService;

@Controller
@RequestMapping("/kis")
public class KeyInfoController {
	@Autowired
	KeyInfoService keyInfoService;

	@Autowired
	NonSearchService nonSearchService;

	/**
	 * 初始页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "index")
	public ModelAndView index(ModelMap map) {
		// 查询所有术语类型
//		List<String> keyInfoTypeList = keyInfoService.findTermTypes();
//		if (null != keyInfoTypeList && keyInfoTypeList.size() > 0) {
//			map.put("keyInfoTypeList", keyInfoTypeList);
//		}

		// 热词展示
		List<String> hotKeyInfoList = keyInfoService.findHotTerms(9);
		if (null != hotKeyInfoList && hotKeyInfoList.size() > 0) {
			map.put("hotKeyInfoList", hotKeyInfoList);
		}
		return new ModelAndView("index");
	}

	/**
	 * bootstrap table分页查询
	 * 
	 * @param pageNumber 参数名必须为这个才能接收到bootstrap table传的参数
	 * @param pageSize   参数名必须为这个才能接收到bootstrap table传的参数
	 * @return
	 */
	@RequestMapping(value = "search")
	@ResponseBody
	public TableSplitResult<KeyInfo> search(ModelMap map, String termType, String findContent, Integer pageSize, Integer pageNumber) {
		map.put("findContent", findContent);
		map.put("termType", termType);
		TableSplitResult<KeyInfo> page = keyInfoService.findPagedTermByKeyInfo(map, pageNumber, pageSize);
		if (null == page || page.getTotal() == 0) {
			//根据内容查询
			KeyInfoNonSearch keyInfoNonSearch = new KeyInfoNonSearch();
			keyInfoNonSearch.setSearchContent(findContent);
			KeyInfoNonSearch result = nonSearchService.findBySearchContent(keyInfoNonSearch);
			if (null == result) {
				//查不到，则添加
				keyInfoNonSearch.setSearchNumber(1);
				nonSearchService.insert(keyInfoNonSearch);
			} else {
				//查得到，则查询次数加1
				result.setSearchNumber(result.getSearchNumber() + 1);
				nonSearchService.update(result);
			}
		}

		return page;
	}

	/**
	 * 查询术语详情
	 * 
	 * @param map
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "showDetail")
	public ModelAndView index(ModelMap map, Integer id) {
		KeyInfo keyInfo = keyInfoService.findTermById(id);
		if (null != keyInfo) {
			map.put("keyInfo", keyInfo);
			keyInfo.setTotalClick(keyInfo.getTotalClick() + 1);
			keyInfoService.updateTerm(keyInfo);
		}
		List<String> sentenceList = getSentenceArrayList(keyInfo);
		if (null != sentenceList && sentenceList.size() > 0) {
			map.put("sentenceList", sentenceList);
		}
		return new ModelAndView("detail");
	}

	/**
	 * 解析例句和例句来源
	 * 
	 * @param keyInfo
	 * @return
	 */
	private List<String> getSentenceArrayList(KeyInfo keyInfo) {
		List<String> sentenceList = new ArrayList<String>();

		String[] sentenceCNArray = keyInfo.getSentenceCN().split("\\$\\$");
		String[] sentenceENArray = keyInfo.getSentenceEN().split("\\$\\$");
		String[] sentenceCNResourceArray = keyInfo.getSentenceCNResource().split("\\$\\$");
		String[] sentenceENResourceArray = keyInfo.getSentenceENResource().split("\\$\\$");

		int size = sentenceCNArray.length > sentenceENArray.length ? sentenceCNArray.length : sentenceENArray.length;

		String sentenceCN = "";
		String sentenceCNResource = "";
		String sentenceEN = "";
		String sentenceENResource = "";
		for (int i = 0; i < size; i++) {
			sentenceCN = sentenceCNArray[i];
			sentenceCNResource = sentenceCNResourceArray[i];
			sentenceEN = sentenceENArray[i];
			sentenceENResource = sentenceENResourceArray[i];

			if (null != sentenceCNResource && !"".equals(sentenceCNResource)) {
				sentenceCN += "【来源: " + sentenceCNResource + "】";
			}
			if (null != sentenceENResource && !"".equals(sentenceENResource)) {
				sentenceEN += "【SOURCE: " + sentenceENResource + "】";
			}

			if (null != sentenceCN && !"".equals(sentenceCN)) {
				sentenceList.add(sentenceCN);
			}
			if (null != sentenceEN && !"".equals(sentenceEN)) {
				sentenceList.add(sentenceEN);
			}
		}
		return sentenceList;
	}

	/**
	 * 查询术语类型对应的数量
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "summary")
	@ResponseBody
	public Map<String, Integer> summary() {
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

	/**
	 * 查询各术语类型对应的点击量
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "accessSummary")
	@ResponseBody
	public Map<String, Integer> accessSummary() {
		List<AccessSummary> accessSummaryList = keyInfoService.findAccessSummary();
		Map<String, Integer> accessSummaryMap = new HashMap<String, Integer>();

		for (AccessSummary accessSummary : accessSummaryList) {
			accessSummaryMap.put(accessSummary.getClassification(), accessSummary.getTotalAccess());
		}
		return accessSummaryMap;
	}

	/**
	 * 查询更多热词
	 * 
	 * @param number
	 * @return
	 */
	@RequestMapping(value = "showMoreHotTerms")
	public ModelAndView showMoreHotTerms(ModelMap map, Integer number) {
		List<String> hotKeyInfoList = keyInfoService.findHotTerms(number);
		if (null != hotKeyInfoList && hotKeyInfoList.size() > 0) {
			map.put("hotKeyInfoList", hotKeyInfoList);
		}
		return new ModelAndView("hotTerms");
	}

}
