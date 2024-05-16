package Mizdooni.Model.Table;

import Mizdooni.Model.Constants;
import Mizdooni.Model.DAO;
import Mizdooni.Model.HibernateUtils;
import Mizdooni.Model.User.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TableDAO extends DAO {



    protected User convertToDomainModel(ResultSet res, String tableName) {
        return null;
    }

    @Override
    protected String getCreateTableQuery(String tableName) {
        return null;
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
        return String.format("INSERT IGNORE INTO %s(table_restaurant, table_manager, seats) VALUES(?,?,?)", Constants.TABLES_TABLE_NAME );
    }

}
