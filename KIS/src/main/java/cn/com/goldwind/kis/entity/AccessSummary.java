package cn.com.goldwind.kis.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class AccessSummary implements Serializable {

	private static final long serialVersionUID = -4521107699246458586L;

	private String classification;

	private Integer totalAccess;
}
