package cn.com.goldwind.kis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import cn.com.goldwind.kis.entity.AccessSummary;
import cn.com.goldwind.kis.entity.KeyInfo;
import cn.com.goldwind.kis.mybatis.BaseMybatisDao;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.repository.KeyInfoRepository;
import cn.com.goldwind.kis.service.KeyInfoService;

@Service
public class KeyInfoServiceImpl extends BaseMybatisDao<KeyInfoRepository> implements KeyInfoService {

	@Autowired
	private KeyInfoRepository keyInforRepository;

	@Override
	public List<KeyInfo> findByKeyInfo(String findContent) {
		return keyInforRepository.findByTermName(findContent);
	}

	@Override
	public List<String> findTermTypes() {
		return keyInforRepository.findTermTypes();
	}

	@Override
	public List<KeyInfo> findByTermType(String termType) {
		return keyInforRepository.findByTermType(termType);
	}

	@Override
	public int getNumByType(String termType) {
		return keyInforRepository.getNumByType(termType);
	}

	@Override
	public KeyInfo findTermById(Integer id) {
		return keyInforRepository.selectByPrimaryKey(id);
	}

	@Override
	public void updateTerm(KeyInfo keyInfo) {
		keyInforRepository.updateByPrimaryKeySelective(keyInfo);
	}

	@Override
	public List<String> findHotTerms(Integer num) {
		return keyInforRepository.findHotTerms(num);
	}

	@Override
	public List<AccessSummary> findAccessSummary() {
		return keyInforRepository.findAccessSummary();
	}

	@Override
	public TableSplitResult<KeyInfo> findPagedTermByKeyInfo(ModelMap map, Integer pageNumber, Integer pageSize) {
		return super.findPage(map, pageNumber, pageSize);
	}

}
