package Mizdooni.Model.Table;


import Mizdooni.Model.Address;
import Mizdooni.Model.Constants;
import Mizdooni.Model.DAO;
import Mizdooni.Model.HibernateUtils;
import Mizdooni.Model.User.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Mizdooni.Model.Constants.TABLES_TABLE_NAME;

public class TableDAO extends DAO {



    protected User convertToDomainModel(ResultSet res, String tableName) {
        return null;
    }

    @Override
    protected String getCreateTableQuery(String tableName) {
        return  String.format(
               "CREATE TABLE IF NOT EXISTS %s (\n" +
                       "id MEDIUMINT NOT NULL AUTO_INCREMENT,\n" +
                       "tableRestaurant CHAR(225),\n" +
                       "tableManager CHAR(225),\n" +
                       "seats INT,\n" +
                       "tableNumber INT UNIQUE,\n" +
                       "PRIMARY KEY(id), \n" +
                       "FOREIGN KEY(tableManager)REFERENCES manager(username),\n" +
                       "FOREIGN KEY(tableRestaurant)REFERENCES restaurant(name)\n" +
                       ");"
                ,tableName);
    }

    @Override
    protected Object convertToDomainModel(ResultSet rs) {
        try{
            return new TableRest(rs.getString(3), rs.getInt(5), rs.getString(2), rs.getInt(4));
        }
        catch (Exception e){
            System.out.println("convertToDomainModelError: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected String getAllQuery() {
        return "SELECT * FROM " + TABLES_TABLE_NAME;
    }


    protected void fillInsertValues(PreparedStatement st, TableRest table) throws SQLException {
        st.setString(1, table.restaurantName);
        st.setString(2, table.managerUsername);
        st.setInt(3, table.seatsNumber);
        st.setInt(4, table.tableNumber);
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
        return String.format("INSERT IGNORE INTO %s(tableRestaurant, tableManager, seats, tableNumber) VALUES(?,?,?,?)", Constants.TABLES_TABLE_NAME );
    }

}
