package cn.com.uploadAndDownload.fileUploadDemo.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.SysResourcesMapper;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysResources;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.service.ResourcesService;

@Service
public class ResourcesServiceImpl implements ResourcesService {
    @Autowired
    private SysResourcesMapper sysResourcesMapper;

    @Override
    public List<SysResources> selectAll() {
        return sysResourcesMapper.selectAll();
    }
}
