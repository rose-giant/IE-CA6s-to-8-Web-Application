package Mizdooni.Model.Table;

import Mizdooni.Model.Constants;
import Mizdooni.Model.DAO;
import Mizdooni.Model.HibernateUtils;
import Mizdooni.Model.User.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class TableDAO extends DAO {
    private static final String TABLE_NAME = "tables";
    public ArrayList<TableRest> getFromAPI() throws Exception{
        String TablesJsonString = getRequest(Constants.GET_TABLES_URL);
        ObjectMapper om = new ObjectMapper();
        return om.readValue(TablesJsonString, new TypeReference<ArrayList<TableRest>>(){});
    }

    protected void fillInsertValues(PreparedStatement st, TableRest table) throws SQLException {
        st.setString(1, table.managerUsername);
        st.setString(2, table.restaurantName);
        st.setInt(3, table.seatsNumber);
    }

    public void addToDatabase(TableRest table) throws SQLException {
        Connection con = HibernateUtils.getConnection();
        String insertQuery = getInsertRecordQuery();
        PreparedStatement st = con.prepareStatement(insertQuery);
        fillInsertValues(st, table);
        System.out.println(st);
        try {
            st.execute();
            st.close();
            con.close();
        } catch (Exception e) {
            st.close();
            con.close();
            System.out.println("error in Repository.insert query.");
            e.printStackTrace();
        }
    }

    private String getInsertRecordQuery() {
        return String.format("INSERT IGNORE INTO %s(table_restaurant, table_manager, seats) VALUES(?,?,?)", TABLE_NAME );
    }

    public void createTable() throws SQLException {
        Connection con = HibernateUtils.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format(
                        "CREATE TABLE %s (\n" +
                                "    id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                                "    table_number INT,\n" +
                                "    table_restaurant VARCHAR(255) NOT NULL,\n" +
                                "    table_manager VARCHAR(255) NOT NULL,\n" +
                                "    seats INT,\n" +
                                "    FOREIGN KEY (table_restaurant) REFERENCES restaurant (name),\n" +
                                "    FOREIGN KEY (table_manager) REFERENCES manager (username)\n" +
                                ");",
                        TABLE_NAME)
        );
        System.out.println(createTableStatement);
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }
}
