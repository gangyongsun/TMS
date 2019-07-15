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
	public List<KeyInfo> findAll() {
		return keyInforRepository.findAll();
	}

	@Override
	public List<KeyInfo> search(String findContent) {
		return keyInforRepository.search(findContent);
	}

}
