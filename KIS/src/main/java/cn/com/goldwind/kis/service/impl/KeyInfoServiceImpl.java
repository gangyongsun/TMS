package cn.com.goldwind.kis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.goldwind.kis.entity.KeyInfo;
import cn.com.goldwind.kis.repository.KeyInfoRepository;
import cn.com.goldwind.kis.service.KeyInfoService;

@Service
public class KeyInfoServiceImpl implements KeyInfoService {

	@Autowired
	private KeyInfoRepository keyInforRepository;

	@Override
	public List<KeyInfo> findByKeyInfo(String keyInfo) {
		return keyInforRepository.findByKeyInfo(keyInfo);
	}

	@Override
	public List<String> findTermTypes() {
		return keyInforRepository.findTermTypes();
	}

	@Override
	public List<KeyInfo> findByTermType(String termType) {
		return keyInforRepository.findByTermType(termType);
	}

}
