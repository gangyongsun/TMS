package cn.com.uploadAndDownload.fileUploadDemo.shiro.bo;

import java.io.Serializable;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysResources;
import cn.com.uploadAndDownload.fileUploadDemo.utils.StringUtils;
import lombok.Data;

/**
 * 权限选择
 * 
 * @author alvin
 *
 */
@Data
public class SysResourcesBo extends SysResources implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 是否勾选
	 */
	private String marker;

	/**
	 * role Id
	 */
	private String roleId;

	public boolean isCheck() {
		return StringUtils.equals(roleId, marker);
	}

}
