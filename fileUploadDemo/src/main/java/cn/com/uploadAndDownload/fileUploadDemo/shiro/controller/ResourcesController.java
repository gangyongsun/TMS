package cn.com.uploadAndDownload.fileUploadDemo.shiro.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.uploadAndDownload.fileUploadDemo.controller.BaseController;
import cn.com.uploadAndDownload.fileUploadDemo.mybatis.page.Pagination;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysResources;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.service.ResourcesService;
import cn.com.uploadAndDownload.fileUploadDemo.utils.LoggerUtils;

/**
 * 资源管理
 * 
 * @author alvin
 *
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("resource")
public class ResourcesController extends BaseController {

	@Autowired
	ResourcesService resourcesService;

	/**
	 * 资源列表
	 * 
	 * @param findContent 查询内容
	 * @param pageNo      页码
	 * @param modelMap    参数回显
	 * @return
	 */
	@RequestMapping(value = "index")
	public ModelAndView index(String findContent, ModelMap modelMap, Integer pageNo) {
		modelMap.put("findContent", findContent);
		Pagination<SysResources> resources = resourcesService.findPage(modelMap, pageNo, pageSize);
		modelMap.put("page", resources);
		return new ModelAndView("system/resource/index");
	}

	/**
	 * 资源添加
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "addResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addResource(SysResources resource) {
		try {
			SysResources entity = resourcesService.insertSelective(resource);
			resultMap.put("status", 200);
			resultMap.put("entity", entity);
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "添加失败，请刷新后再试！");
			LoggerUtils.fmtError(getClass(), e, "添加资源报错！source[%s]", resource.toString());
		}
		return resultMap;
	}

	/**
	 * 根据ID删除资源，但是删除资源的时候，需要查询是否有赋予给角色，如果有角色在使用，那么就不能删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteResourceById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRoleById(String ids) {
		return resourcesService.deleteResourceByIds(ids);
	}
}
