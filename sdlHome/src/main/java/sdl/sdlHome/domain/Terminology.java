package sdl.sdlHome.domain;

import lombok.Data;

/**
 * 术语实体类
 * 
 * @author alvin
 *
 */
@Data
public class Terminology {
	/**
	 * EntryID
	 */
	public String entryId;

	/**
	 * Entry类别
	 */
	public String entryClass;

	/**
	 * 术语名
	 */
	public String termName;

	/**
	 * 术语定义
	 */
	public String termDefination;
	/**
	 * 术语定义来源
	 */
	public String termDefinationSource;

	/**
	 * 术语例句
	 */
	public String termExampleSentence;

	/**
	 * 术语例句来源
	 */
	public String termExampleSentenceSource;

	/**
	 * 所在的术语库
	 */
	public String termbaseId;

	/**
	 * 术语语言
	 */
	public String languageId;

}
