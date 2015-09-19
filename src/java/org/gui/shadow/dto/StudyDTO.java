package org.gui.shadow.dto;

/**
 * @author David Soler <aensoler@gmail.com>
 */
public class StudyDTO {

	private String id;
	private String name;

	public StudyDTO(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
