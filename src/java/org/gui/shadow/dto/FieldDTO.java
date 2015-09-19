package org.gui.shadow.dto;

/**
 * @author David Soler <aensoler@gmail.com>
 */
public class FieldDTO {

	private int id;
	private String type;
	private String value;

	public FieldDTO(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
