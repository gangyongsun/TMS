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
@Table(name = "t_key_info")
public class KeyInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
	private Integer id;
	private String chinese;
	private String english;

	private String classification1;
	private String classification2;
	private String classification3;

	@Column(name = "sentenceCN")
	private String sentenceCN;
	@Column(name = "sentenceEN")
	private String sentenceEN;
	@Column(name = "sentenceCNResource")
	private String sentenceCNResource;
	@Column(name = "sentenceENResource")
	private String sentenceENResource;

	@Column(name = "abbreviationCN")
	private String abbreviationCN;
	@Column(name = "abbreviationEN")
	private String abbreviationEN;

	@Column(name = "synonymCN")
	private String synonymCN;
	@Column(name = "synonymEN")
	private String synonymEN;

	@Column(name = "definationCN")
	private String definationCN;
	@Column(name = "definationEN")
	private String definationEN;
	@Column(name = "definationCNResource")
	private String definationCNResource;
	@Column(name = "definationENResource")
	private String definationENResource;

	private String image;
	private String remark;
	private String status;
	private String source;
	private String contributor;

	@Column(name = "totalClick")
	private int totalClick;
}
