package cn.com.goldwind.kis.service;

import java.util.List;

import cn.com.goldwind.kis.entity.KeyInfo;

public interface KeyInfoService {

	/**
	 * 根据给出的中文或英文模糊查询相关的关键词信息
	 * 
	 * @param keyInfo
	 * @return
	 */
	public List<KeyInfo> findByKeyInfo(String keyInfo);

}
