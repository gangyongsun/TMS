package cn.com.goldwind.kis.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "t_key_info")
public class KeyInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private String chinese;
	private String english;

	private String classification1;
	private String classification2;
	private String classification3;

	private String sentenceCN;
	private String sentenceEN;
	private String sentenceCNResource;
	private String sentenceENResource;
	
	private String abbreviationCN;
	private String abbreviationEN;
	
	private String synonymCN;
	private String synonymEN;
	
	private String definationCN;
	private String definationEN;
	private String definationCNResource;
	private String definationENResource;
	
	private String image;
	private String remark;
	private String status;
	private String source;
	private String contributor;
	
	private int hitCount;
}
