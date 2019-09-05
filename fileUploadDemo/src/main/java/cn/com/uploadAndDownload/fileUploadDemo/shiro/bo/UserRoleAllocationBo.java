package cn.com.uploadAndDownload.fileUploadDemo.shiro.bo;

import java.io.Serializable;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysUser;
import lombok.Data;

/**
 * 用户角色分配 查询列表BO
 * 
 * @author alvin
 *
 */
@Data
public class UserRoleAllocationBo extends SysUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Role Name列转行，以,分割
	 */
	private String roleNames;

	/**
	 * Role Id列转行，以‘,’分割
	 */
	private String roleIds;

}
