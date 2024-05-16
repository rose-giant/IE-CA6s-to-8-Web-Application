package Mizdooni.Model;

import Mizdooni.Model.User.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static Mizdooni.Model.Constants.*;

public abstract class DAO<TYPE> {

    protected abstract String getCreateTableQuery(String tableName);

    public static String getRequest(String Url) throws Exception {
        URL url = new URL(Url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        //Check if connect is made
        int responseCode = conn.getResponseCode();

        // 200 OK
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            StringBuilder informationString = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                informationString.append(scanner.nextLine());
            }
            scanner.close();
            return informationString.toString();
        }
    }


    public <TYPE> ArrayList<TYPE> fetchFromAPI(String GET_URL, Class<TYPE> elementClass) throws Exception{
        String UsersJsonString = getRequest(GET_URL);
        ObjectMapper om = new ObjectMapper();
        CollectionType listType = om.getTypeFactory().constructCollectionType(ArrayList.class,elementClass);
        return om.readValue(UsersJsonString,listType);
    }

    public boolean checkTableExistence(String tableName) throws SQLException {
        Connection conn = HibernateUtils.getConnection();
        String sql = "SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, tableName);
        ResultSet rs = stmt.executeQuery();
        boolean tableExists = rs.next();
        System.out.println(tableName + ": " +tableExists);
        if (!tableExists) {
            stmt = conn.prepareStatement(getCreateTableQuery(tableName));
            System.out.println(stmt);
            stmt.executeUpdate();
        }
        rs.close();
        stmt.close();
        conn.close();
        return tableExists;

    }

    protected abstract TYPE convertToDomainModel(Object...res);


    public <TYPE> ArrayList<TYPE> getAll() throws SQLException {
        Connection conn = HibernateUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(getAllQuery());
        ResultSet rs = stmt.executeQuery();
        System.out.println(stmt);
        ArrayList<TYPE> objects = new ArrayList<>();
        while (rs.next()) {
            objects.add((TYPE) convertToDomainModel(rs));
        }
        return objects;
    }

    protected abstract String getAllQuery();
}




