package Mizdooni.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class DAO<TYPE> {

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


//    public void addToDatabase(TYPE obj) throws SQLException {
//        Connection con = HibernateUtils.getConnection();
//        PreparedStatement st = con.prepareStatement(getInsertStatement());
//        fillInsertValues(st, obj);
//        System.out.println(st);
//        try {
//            st.execute();
//            st.close();
//            con.close();
//        } catch (Exception e) {
//            st.close();
//            con.close();
//            System.out.println("error in Repository.insert query.");
//            e.printStackTrace();
//        }
//    }
}




