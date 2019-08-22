package cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.domain;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;

@Table(name = "sys_user")
@Data
public class SysUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
	private Integer id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "pass_word")
	private String passWord;

	/**
	 * 是否启用
	 */
	@Column(name = "user_enable")
	private Integer userEnable;

}