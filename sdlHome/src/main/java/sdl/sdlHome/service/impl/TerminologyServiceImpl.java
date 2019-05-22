package sdl.sdlHome.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import sdl.sdlHome.domain.EntryId;
import sdl.sdlHome.domain.Hit;
import sdl.sdlHome.service.TerminologyService;
import sdl.sdlHome.util.JerseyClient;
import sdl.sdlHome.util.PropertiesUtil;

/**
 * 
 * @author alvin
 *
 */
@Service("terminologyService")
public class TerminologyServiceImpl implements TerminologyService {
	@Override
	public String getToken(String username, String password) {
		String apiURL = PropertiesUtil.getValueByKey("API_TOKEN", "config.properties");
		return JerseyClient.getToken(apiURL, username, password);
	}

	@Override
	public JSONArray search(String token, String terminologyWord, int languageId) {
		String apiURL = PropertiesUtil.getValueByKey("API_SEARCH", "config.properties");
		String params = "{'term':'" + terminologyWord + "','sourceLanguageIds':" + languageId + "}";
		JSONObject jsonObject = JerseyClient.getMethod(apiURL, token, params);
		return jsonObject.getJSONArray("hits");
	}

	@Override
	public Hit getExample() {
		EntryId entryId = new EntryId();
		entryId.setId(1);
		entryId.setUuid("1001");

		Hit hit = new Hit();
		hit.setEntryId(entryId);
		hit.setLanguageId(2);
		hit.setScore(4);
		hit.setTermbaseId(1);
		hit.setSimilarity(80);
		hit.setTerm("齿轮");

		return hit;
	}
}
