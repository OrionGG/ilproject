package org.musicbrainz.model;


/**
 * <p>Represents an artist alias.</p>
 * 
 * <p>An alias (the alias value) is a different representation of an artist's name.
 * This may be a common misspelling or a transliteration (the alias type).
 * The alias script is interesting mostly for transliterations and indicates
 * which script is used for the alias value. To represent the script,
 * ISO-15924 script codes like 'Latn', 'Cyrl', or 'Hebr' are used.</p>
 *
 * @author Patrick Ruhkopf
 */
public class ArtistAlias 
{
	/**
	 * The alias
	 */
	private String value;

	/**
	 * The alias type (an absolute URI)
	 */
	private String type;
	
	/**
	 * ISO-15924 script code
	 */
	private String script;

	/**
	 * @return the getScript
	 */
	public String getScript() {
		return script;
	}

	/**
	 * @param getScript the getScript to set
	 */
	public void setScript(String getScript) {
		this.script = getScript;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
}
