package cn.com.uploadAndDownload.fileUploadDemo.shiro.service;

import java.util.List;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.domain.SysResources;

public interface ResourcesService {
    List<SysResources> selectAll();
}
