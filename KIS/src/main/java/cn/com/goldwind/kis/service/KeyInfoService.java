package cn.com.goldwind.kis.service;

import java.util.List;

import cn.com.goldwind.kis.entity.KeyInfo;

public interface KeyInfoService {

	/**
	 * 查询所有关键词信息
	 * 
	 * @return
	 */
	public List<KeyInfo> findAll();

	/**
	 * 根据给出的中文或英文模糊查询相关的关键词信息
	 * 
	 * @param findContent
	 * @return
	 */
	public List<KeyInfo> search(String findContent);

}
