package cn.com.goldwind.kis.service;

import org.springframework.ui.ModelMap;

import cn.com.goldwind.kis.entity.KeyInfoNonSearch;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;

public interface NonSearchService {

	public TableSplitResult<KeyInfoNonSearch> findPage(ModelMap map, Integer pageNumber, Integer pageSize);

	public KeyInfoNonSearch findBySearchContent(KeyInfoNonSearch keyInfoNonSearch);

	public int insert(KeyInfoNonSearch keyInfoNonSearch);

	public int update(KeyInfoNonSearch keyInfoNonSearch);

}
