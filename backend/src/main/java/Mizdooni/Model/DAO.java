package Mizdooni.Model;

import Mizdooni.Model.User.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class DAO<TYPE> {

    protected abstract String getCreateTableQuery(String tableName);
    protected abstract String getAllQuery();
    protected abstract TYPE convertToDomainModel(ResultSet res);
    protected abstract String getInsertRecordQuery();
    abstract protected void fillInsertValues(PreparedStatement st, TYPE obj) throws SQLException;
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
    public <TYPE> ArrayList<TYPE> getAll() throws SQLException {
        Connection conn = HibernateUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(getAllQuery());
        System.out.println(stmt);
        ResultSet rs = stmt.executeQuery();
        ArrayList<TYPE> objects = convertToDomainModelList(rs);
        return objects;
    }

    public void addToDatabase(TYPE obj) throws SQLException {
        Connection con = HibernateUtils.getConnection();
        String insertQuery = getInsertRecordQuery();
        System.out.println(insertQuery);
        PreparedStatement st = con.prepareStatement(insertQuery);
        fillInsertValues(st, obj);
        System.out.println(st);
        st.execute();
        SQLWarning warning = st.getWarnings();
        if (warning != null) {
            throw new SQLWarning(warning.getMessage());
        }
        st.close();
        con.close();
    }

    public <TYPE> ArrayList<TYPE> findByFields(Connection con, List<String> values, List<String> field_names, String tableName) throws SQLException {
        String query = getFindByFieldQuery(values, field_names, tableName);
        PreparedStatement st = con.prepareStatement(query);
        System.out.println(st);
        try {
            ResultSet resultSet = st.executeQuery();
            if (resultSet == null) {
                st.close();
                con.close();
                return new ArrayList<>();
            }
            ArrayList<TYPE> result = convertToDomainModelList(resultSet);
            st.close();
            con.close();
            return result;
        } catch (Exception e) {
            st.close();
            con.close();
            System.out.println("error in Repository.findAll query.");
            e.printStackTrace();
            throw e;
        }
    }

    private String getFindByFieldQuery(List<String> values, List<String> fieldNames, String tableName) {
        String query = "SELECT * FROM " + tableName + " t WHERE ";
        for(int i = 0; i < fieldNames.size(); i++){
            query += ("t." + fieldNames.get(i) + " = " + "\"" + values.get(i) +"\"");
            if(i != fieldNames.size()-1) query += " AND ";
        }
        query += ";";
        return query;
    }

    protected <TYPE> ArrayList<TYPE> convertToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<TYPE> suppliers = new ArrayList<>();
        while (rs.next()) {
            suppliers.add((TYPE) convertToDomainModel(rs));
        }
        return suppliers;
    }




}




