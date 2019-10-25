package cn.com.goldwind.kis.service;

import java.util.List;

import org.springframework.ui.ModelMap;

import cn.com.goldwind.kis.entity.AccessSummary;
import cn.com.goldwind.kis.entity.KeyInfo;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;

public interface KeyInfoService {

	/**
	 * 根据给出的中文或英文模糊查询相关的关键词信息
	 * 
	 * @param findContent
	 * @return
	 */
	public List<KeyInfo> findByKeyInfo(String findContent);

	public List<String> findTermTypes();

	public List<KeyInfo> findByTermType(String termType);

	public int getNumByType(String termType);

	public KeyInfo findTermById(Integer id);

	public void updateTerm(KeyInfo keyInfo);

	public List<String> findHotTerms(Integer num);

	public List<AccessSummary> findAccessSummary();

	public TableSplitResult<KeyInfo> findPagedTermByKeyInfo(ModelMap map, Integer pageNumber, Integer pageSize);
}
