package cn.com.uploadAndDownload.fileUploadDemo.shiro.service;

import java.util.List;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysResources;

public interface ResourcesService {
	/**
	 * 查询所有资源
	 * 
	 * @return
	 */
	List<SysResources> selectAll();
}
