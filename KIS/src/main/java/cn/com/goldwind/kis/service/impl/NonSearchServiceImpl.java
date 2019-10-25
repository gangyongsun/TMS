package cn.com.goldwind.kis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import cn.com.goldwind.kis.entity.KeyInfoNonSearch;
import cn.com.goldwind.kis.mybatis.BaseMybatisDao;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.repository.NonSearchRepository;
import cn.com.goldwind.kis.service.NonSearchService;

@Service
public class NonSearchServiceImpl extends BaseMybatisDao<NonSearchRepository> implements NonSearchService {

	@Autowired
	private NonSearchRepository nonSearchRepository;

	@Override
	public TableSplitResult<KeyInfoNonSearch> findPage(ModelMap map, Integer pageNumber, Integer pageSize) {
		return super.findPage(map, pageNumber, pageSize);
	}

	@Override
	public KeyInfoNonSearch findBySearchContent(KeyInfoNonSearch keyInfoNonSearch) {
		return nonSearchRepository.selectOne(keyInfoNonSearch);
	}

	@Override
	public int insert(KeyInfoNonSearch keyInfoNonSearch) {
		return nonSearchRepository.insertSelective(keyInfoNonSearch);
	}

	@Override
	public int update(KeyInfoNonSearch keyInfoNonSearch) {
		return nonSearchRepository.updateByPrimaryKeySelective(keyInfoNonSearch);
	}

}
