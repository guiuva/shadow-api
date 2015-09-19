package org.gui.shadow.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

/**
 * @author David Soler <aensoler@gmail.com>
 */
public class UserDTO {

	private String login;
	private String password;
	private String dni;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private Date membership;

	private String study;
	private int[] fields;

	public UserDTO(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getMembership() {
		return membership;
	}

	public void setMembership(Date membership) {
		this.membership = membership;
	}

	public String getStudy() {
		return study;
	}

	public void setStudy(String study) {
		this.study = study;
	}

	public int[] getFields() {
		return fields;
	}

	public void setFields(int[] fields) {
		this.fields = fields;
	}

	public void map(ResultSet userResultSet, int[] userFieldsIds) {
		try {
			setPassword(userResultSet.getString("password"));
			setDni(userResultSet.getString("dni"));
			setName(userResultSet.getString("name"));
			setSurname(userResultSet.getString("surname"));
			setPhone(userResultSet.getString("phone"));
			setEmail(userResultSet.getString("email"));
			setStudy(userResultSet.getString("study"));
			setFields(userFieldsIds);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
