package sdl.sdlHome.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import sdl.sdlHome.domain.EntryId;
import sdl.sdlHome.domain.Hit;
import sdl.sdlHome.service.TerminologyService;

/**
 * 
 * @author alvin
 *
 */
@Controller
@RequestMapping("/terminologyController")
public class TerminologyController {

	@Autowired
	@Qualifier("terminologyService")
	public TerminologyService termService;

	/**
	 * 跳转到术语门户首页,包含search
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model,@SessionAttribute(WebSecurityConfig.SESSION_KEY)  String username) {
		model.addAttribute("username", "Welcome "+ username);
		// 从service层获取一个测试term
		Hit hit = termService.getExample();
		model.addAttribute("hit", hit);

		List<Hit> hitlist = new ArrayList<Hit>();
		hitlist.add(hit);

		model.addAttribute("termlist", hitlist);
		 // 注意，不要在最前面加上/，Linux下面会出错
		return "web/terminologySearch";
	}

	/**
	 * 跳转到术语详情页
	 * 
	 * @return
	 */
	@RequestMapping("/terminologyDetail")
	public String terminologyDetail(Model model,@SessionAttribute(WebSecurityConfig.SESSION_KEY)  String username) {
		model.addAttribute("username", "Welcome "+ username);
		return "web/terminologyDetail";
	}

	/**
	 * 搜索术语
	 * 
	 * @param model
	 * @param terminologyWord
	 *            关键词
	 * @param languageId
	 *            语言
	 * @return
	 */
	@RequestMapping("/search")
	public String search(Model model,@SessionAttribute(WebSecurityConfig.SESSION_KEY)  String username, @RequestParam("terminologyWord") String terminologyWord, @RequestParam("languageId") String languageId) {
		String token = termService.getToken("Kevin", "123456");
		JSONArray hitsArray = termService.search(token, terminologyWord, Integer.parseInt(languageId));

		List<Hit> hitlist = new ArrayList<Hit>();
		for (Iterator<Object> iterator = hitsArray.iterator(); iterator.hasNext();) {
			JSONObject jsonObj = (JSONObject) iterator.next();

			JSONObject entryIdObject = (JSONObject) jsonObj.get("entryId");
			EntryId entryId = new EntryId();
			entryId.setId(entryIdObject.getIntValue("id"));
			entryId.setUuid(entryIdObject.getString("uuid"));

			Hit hit = new Hit();
			hit.setLanguageId(jsonObj.getIntValue("languageId"));
			hit.setScore(jsonObj.getIntValue("score"));
			hit.setSimilarity(jsonObj.getIntValue("similarity"));
			hit.setTerm(jsonObj.getString("term"));
			hit.setTermbaseId(jsonObj.getIntValue("termbaseId"));
			hit.setEntryId(entryId);
			hitlist.add(hit);
		}
		// 设置术语列表
		model.addAttribute("termlist", hitlist);
		model.addAttribute("username","Welcome "+ username);
		return "web/terminologySearch";
	}
}
