package cn.com.goldwind.kis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.goldwind.kis.entity.KeyInfoNonSearch;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.service.NonSearchService;

@Controller
@RequestMapping("/console")
public class NonSearchController {
	
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
		return new ModelAndView("console");
	}

	/**
	 * bootstrap table分页查询
	 * 
	 * @param pageNumber 参数名必须为这个才能接收到bootstrap table传的参数
	 * @param pageSize   参数名必须为这个才能接收到bootstrap table传的参数
	 * @return
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public TableSplitResult<KeyInfoNonSearch> search(ModelMap map, String findContent, Integer pageSize, Integer pageNumber) {
		map.put("findContent", findContent);
		TableSplitResult<KeyInfoNonSearch> page = nonSearchService.findPage(map, pageNumber, pageSize);
		return page;
	}
	
}
