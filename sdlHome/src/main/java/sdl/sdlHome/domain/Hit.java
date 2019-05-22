package sdl.sdlHome.domain;

import lombok.Data;

/**
 * 
 * @author alvin
 *
 */
@Data
public class Hit {
	public int score;

	public int termbaseId;

	public int similarity;

	public int languageId;

	public String term;
	
	public EntryId entryId;

}
