package Mizdooni.Model.User;

import Mizdooni.Model.Constants;

import Mizdooni.Model.DAO;
import Mizdooni.Model.HibernateUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class UserDAO extends DAO {

    private static final String TABLE_NAME = "user";



    protected void fillInsertValues(PreparedStatement st, User user) throws SQLException {
        st.setString(1, user.getUsername());
        st.setString(2, user.getPassword());
        st.setString(3, user.getEmail());
        st.setString(4, user.getAddress().toString());
    }

    public void addToDatabase(User user) throws SQLException {
        Connection con = HibernateUtils.getConnection();
        String insertQuery = getInsertRecordQuery(user.getRole());
        PreparedStatement st = con.prepareStatement(insertQuery);
        fillInsertValues(st, user);
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

    private String getInsertRecordQuery(String role) {
        return String.format("INSERT IGNORE INTO %s(username, password, email, address) VALUES(?,?,?,?)", role );
    }


    public void createTable(String tableName) throws SQLException {
        Connection con = HibernateUtils.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(username CHAR(225),\npassword CHAR(225),\nemail CHAR(225),\naddress CHAR(225),\nPRIMARY KEY(username));",
                        tableName)
        );
        System.out.println(createTableStatement);
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

}
