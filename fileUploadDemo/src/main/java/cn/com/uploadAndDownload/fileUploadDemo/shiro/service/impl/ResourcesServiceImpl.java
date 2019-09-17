package cn.com.uploadAndDownload.fileUploadDemo.shiro.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.uploadAndDownload.fileUploadDemo.mybatis.BaseMybatisDao;
import cn.com.uploadAndDownload.fileUploadDemo.mybatis.page.Pagination;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.SysResourcesBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.SysResourcesMapper;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysResources;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.service.ResourcesService;

@Service
public class ResourcesServiceImpl extends BaseMybatisDao<SysResourcesMapper>  implements ResourcesService {
    @Autowired
    private SysResourcesMapper sysResourcesMapper;

    @Override
    public List<SysResources> selectAll() {
        return sysResourcesMapper.selectAll();
    }

	@Override
	public Pagination<SysResources> findPage(Map<String, Object> modelMap, Integer pageNo, int pageSize) {
		return super.findPage(modelMap, pageNo, pageSize);
	}

	@Override
	public SysResources insertSelective(SysResources psermission) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> deleteResourceById(String ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> addResource2Role(Long roleId, String ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysResourcesBo> selectResourceById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> findResourceByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
