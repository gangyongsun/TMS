package cn.com.uploadAndDownload.fileUploadDemo.shiro.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Table(name = "sys_role")
@Data
public class SysRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
	private Integer id;

	@Column(name = "role_desc")
	private String roleDesc;

	/**
	 * 一个role对多个resources，一对多处理
	 */
	@Transient
	private List<SysResources> resources = new LinkedList<SysResources>();

}