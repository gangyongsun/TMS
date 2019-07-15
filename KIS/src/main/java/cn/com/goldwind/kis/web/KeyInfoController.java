package cn.com.goldwind.kis.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.goldwind.kis.entity.KeyInfo;
import cn.com.goldwind.kis.service.KeyInfoService;

@Controller
@RequestMapping("/kis")
public class KeyInfoController {
	@Autowired
	KeyInfoService keyInfoService;

	/**
	 * 初始页面进行重定向到list查询
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String index() {
//		return "redirect:/kis/list";
		return "index";
	}

	/**
	 * 查询所有数据
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String hello(Model model) {
		List<KeyInfo> keyInfoList = keyInfoService.findAll();
		model.addAttribute("keyInfoList", keyInfoList);
		return "index";
	}

	/**
	 * 根据给出的中英文进行模糊查询
	 * 
	 * @param keyInfoDto
	 * @param model
	 * @return
	 */
	@RequestMapping("/searchAll")
	public String searchAll(String findContent, Model model) {
		System.out.println(findContent);
		List<KeyInfo> keyInfoList = keyInfoService.search(findContent);
		model.addAttribute("keyInfoList", keyInfoList);
		model.addAttribute("findContent", findContent);
		return "index";
	}
}
