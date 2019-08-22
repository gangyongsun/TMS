package cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.domain;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;

@Table(name = "sys_role")
@Data
public class SysRole implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
	private Integer id;

	@Column(name = "role_desc")
	private String roleDesc;

}