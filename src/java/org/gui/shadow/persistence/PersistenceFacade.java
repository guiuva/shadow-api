package org.gui.shadow.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.gui.shadow.dto.FieldDTO;
import org.gui.shadow.dto.StudyDTO;
import org.gui.shadow.dto.TypeDTO;
import org.gui.shadow.dto.UserDTO;

/**
 * @author David Soler <aensoler@gmail.com>
 */
public class PersistenceFacade implements IPersistenceFacade {

	private static DatabaseHandler databaseHandler;

	public PersistenceFacade(String databaseName) {
		if (databaseName == null || databaseName.isEmpty()) {
			databaseName = Config.DATABASE_NAME;
		}

		databaseHandler = new SQLiteHandler(databaseName);
	}

	@Override
	public void initDB() {
		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(Config.DATABASE_SCHEMA);

		Scanner scanner = new Scanner(inputStream);
		String sql = scanner.useDelimiter("\\A").next();
		scanner.close();

		databaseHandler.update(sql);
	}

	@Override
	public void seedDB(String filename) {
		File file = new File(filename);

		Scanner scanner;
		try {
			scanner = new Scanner(file);
			String sql = scanner.useDelimiter("\\A").next();
			scanner.close();

			databaseHandler.update(sql);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean createUser(UserDTO userDTO) {
		String sql = "INSERT INTO users VALUES(" + "'" + userDTO.getLogin()
				+ "'," + "'" + userDTO.getPassword() + "'," + "'"
				+ userDTO.getDni() + "'," + "'" + userDTO.getName() + "',"
				+ "'" + userDTO.getSurname() + "'," + "'" + userDTO.getEmail()
				+ "'," + "'" + userDTO.getPhone() + "'," + "'"
				+ userDTO.getMembership() + "'," + "'" + userDTO.getStudy()
				+ "')";

		int result = databaseHandler.update(sql);

		return (result > 0 ? true : false);
	}

	@Override
	public UserDTO retrieveUser(String userId) {
		UserDTO userDTO = new UserDTO(userId);

		String sql = "SELECT * FROM users WHERE login='" + userId + "'";

		ResultSet resultSet = databaseHandler.retrieve(sql);

		try {
			if (resultSet.next()) {
				userDTO.map(resultSet, getUserFieldsIDs(userId));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userDTO;
	}

	@Override
	public ArrayList<TypeDTO> getTypes() {
		ArrayList<TypeDTO> typesDTO = new ArrayList<TypeDTO>();

		ResultSet resultSet = databaseHandler.retrieve("SELECT id FROM types");

		try {
			while (resultSet.next()) {
				typesDTO.add(new TypeDTO(resultSet.getString("id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return typesDTO;
	}

	@Override
	public void addUserField(String userId, FieldDTO field) {
		int fieldId;
		String insertFieldSQL = "INSERT INTO fields VALUES(NULL,'"
				+ field.getType() + "','" + field.getValue() + "')";

		System.out.println(insertFieldSQL);

		fieldId = databaseHandler.update(insertFieldSQL);

		String insertUserFieldSQL = "INSERT INTO fields_users VALUES("
				+ fieldId + ",'" + userId + "')";

		System.out.println(insertUserFieldSQL);

		databaseHandler.update(insertUserFieldSQL);
	}

	/**
	 * Retrieves all fields associated with the user.
	 *
	 * @param userId Uniq identifier of the user
	 * @return Collection of all fields associated with the user.
	 */
	@Override
	public ArrayList<FieldDTO> getUserFields(String userId) {
		ArrayList<FieldDTO> fieldsDTO = new ArrayList<FieldDTO>();
		FieldDTO fieldDTO = null;

		String sql = "SELECT * FROM fields_users WHERE user_id='" + userId
				+ "'";

		ResultSet resultSet = databaseHandler.retrieve(sql);
		try {
			while (resultSet.next()) {
				fieldDTO = new FieldDTO(resultSet.getInt("id"));
				fieldDTO.setType(resultSet.getString("type_id"));
				fieldDTO.setValue(resultSet.getString("value"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return fieldsDTO;
	}

	@Override
	public ArrayList<StudyDTO> getStudies() {
		ArrayList<StudyDTO> studyDTOs = new ArrayList<StudyDTO>();
		StudyDTO studyDTO = null;

		ResultSet resultSet = databaseHandler
				.retrieve("SELECT id, name FROM studies");

		try {
			while (resultSet.next()) {
				studyDTO = new StudyDTO(resultSet.getString("id"));
				studyDTO.setName(resultSet.getString("name"));
				studyDTOs.add(studyDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return studyDTOs;
	}

	private int[] getUserFieldsIDs(String userId) {
		int[] userFieldsIDs = null;
		int i = 0;

		String sql = "SELECT field_id FROM fields_users WHERE user_id='"
				+ userId + "'";

		ResultSet resultSet = databaseHandler.retrieve(sql);

		try {
			userFieldsIDs = new int[resultSet.getFetchSize()];
			while (resultSet.next()) {
				userFieldsIDs[i] = resultSet.getInt("field_id");
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userFieldsIDs;
	}

}
