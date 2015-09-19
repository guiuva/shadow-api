package org.gui.shadow.persistence;

import java.util.ArrayList;

import org.gui.shadow.dto.FieldDTO;
import org.gui.shadow.dto.StudyDTO;
import org.gui.shadow.dto.TypeDTO;
import org.gui.shadow.dto.UserDTO;

/**
 * @author David Soler <aensoler@gmail.com>
 */
public interface IPersistenceFacade {

	public void initDB();

	public void seedDB(String filename);

	public boolean createUser(UserDTO userDTO);

	public UserDTO retrieveUser(String userId);

	public ArrayList<TypeDTO> getTypes();

	public void addUserField(String userId, FieldDTO field);

	public ArrayList<FieldDTO> getUserFields(String userId);

	public ArrayList<StudyDTO> getStudies();

}
