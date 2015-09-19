package org.gui.shadow.persistence;

import java.sql.ResultSet;

/**
 * @author David Soler <aensoler@gmail.com>
 */
public interface DatabaseHandler {

	public void open();

	public ResultSet retrieve(String sql);

	public int update(String sql);

	public void close();

}
