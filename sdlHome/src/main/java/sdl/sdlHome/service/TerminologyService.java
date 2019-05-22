
package sdl.sdlHome.service;

import com.alibaba.fastjson.JSONArray;

import sdl.sdlHome.domain.Hit;

/**
 * 术语查询相关服务
 * 
 * @author alvin
 *
 */
public interface TerminologyService {
	/**
	 * 查询术语
	 * 
	 * @param token
	 * @param terminologyWord
	 * @param languageId
	 * @return
	 */
	public JSONArray search(String token, String terminologyWord, int languageId);

	/**
	 * 获取token
	 * 
	 * @param username
	 * @param password
	 * @return token
	 */
	public String getToken(String username, String password);

	/**
	 * 假数据获取方法
	 * 
	 * @return
	 */
	public Hit getExample();
}
