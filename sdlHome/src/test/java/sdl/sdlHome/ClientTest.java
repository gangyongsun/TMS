package sdl.sdlHome;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import sdl.sdlHome.domain.EntryId;
import sdl.sdlHome.domain.Hit;
import sdl.sdlHome.service.TerminologyService;
import sdl.sdlHome.service.impl.TerminologyServiceImpl;
import sdl.sdlHome.util.JerseyClient;

/**
 * 
 * @author alvin
 *
 */
public class ClientTest {
	// public static MultivaluedMap<String, String> parseJSON2Map(String jsonStr) {
	// MultivaluedMap<String, String> multivaluedMap = new MultivaluedMapImpl();
	//
	// System.out.println("jsonStr:" + jsonStr);
	// JSONObject json = JSON.parseObject(jsonStr);// 字符串转换为JSON对象
	//
	// for (Map.Entry<String, Object> entry : json.entrySet()) {
	// String jsonValue = entry.getValue().toString();// 获取JSON对象value并转换为String
	// if (jsonValue.contains(",")) {
	// String[] arr = jsonValue.split(","); // 用,分割成数组
	// for (String subValue : arr) {
	// multivaluedMap.add(entry.getKey(), subValue);
	// }
	// } else {
	// multivaluedMap.add(entry.getKey(), jsonValue);
	// }
	// }
	// return multivaluedMap;
	// }

	/**
	 * 打印字段
	 * 
	 * @param jsonObject
	 */
	private void printFields(JSONObject jsonObject) {
		JSONArray fields = jsonObject.getJSONArray("fields");
		if (null != fields) {
			for (Object field : fields) {
				JSONObject fieldJsonObject = (JSONObject) field;
				System.out.println("字段属性：\n"+fieldJsonObject.toJSONString());
			}
		}
	}

	/**
	 * 打印日期信息
	 * 
	 * @param jsonObject
	 */
	private void printStampInfo(JSONObject jsonObject) {
		JSONObject stamp = (JSONObject) jsonObject.get("stamp");
		System.out.println("创建信息：\n"+stamp.toJSONString());
	}

	@Test
	public void staticTest() {
		String token = JerseyClient.getToken("/sdl/v1.0/token", "Kevin", "123456");
		System.out.println("token=" + token);
		JSONObject jsonObject = JerseyClient.getMethod("/sdl/v1.0/search", token, "{'term':'bolt','sourceLanguageIds':2}");
		// entries level
		JSONArray entries = jsonObject.getJSONArray("entries");
		for (Object entry : entries) {
			JSONObject entry_jsonObject = (JSONObject) entry;
			printFields(entry_jsonObject);

			// languages level
			JSONArray languages = entry_jsonObject.getJSONArray("languages");
			for (Object languge : languages) {
				JSONObject language_JsonObjectbject = (JSONObject) languge;
				printFields(language_JsonObjectbject);

				// terms level
				JSONArray terms = language_JsonObjectbject.getJSONArray("terms");
				for (Object term : terms) {
					JSONObject term_jsonObject = (JSONObject) term;
					System.out.println("术语id：" + term_jsonObject.getString("id"));
					System.out.println("术语名：" + term_jsonObject.getString("term"));
					System.out.println("术语是否已删除：" + term_jsonObject.getString("isDeleted"));
					printStampInfo(term_jsonObject);
					printFields(term_jsonObject);
					System.out.println("========");
				}
			}
		}
	}

	@Test
	public void testService() {
		TerminologyService termService = new TerminologyServiceImpl();

		String token = termService.getToken("Kevin", "123456");
		JSONArray hitsArray = termService.search(token, "bolt", 2);

		System.out.println(hitsArray);

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
		for (Hit hit : hitlist) {
			System.out.println(hit.getTerm());
		}
	}
}
