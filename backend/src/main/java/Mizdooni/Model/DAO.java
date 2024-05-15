package Mizdooni.Model;

import Mizdooni.Model.User.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
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

    public void fetchFromAPI(String GET_URL) throws Exception{
        String UsersJsonString = getRequest(GET_URL);
        ObjectMapper om = new ObjectMapper();
        ArrayList<TYPE> users = om.readValue(UsersJsonString, new TypeReference<ArrayList<TYPE>>(){});

        for (TYPE user: users) {
//            Connection con =

        }
    }


}




