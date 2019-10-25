package cn.com.goldwind.kis.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@Table(name = "t_key_info_nonesearch")
public class KeyInfoNonSearch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
	private Integer id;

	@Column(name = "searchContent")
	private String searchContent;

	@Column(name = "searchNumber")
	private Integer searchNumber;
}
