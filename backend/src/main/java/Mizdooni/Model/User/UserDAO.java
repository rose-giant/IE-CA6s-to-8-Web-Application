package Mizdooni.Model.User;

import Mizdooni.Model.Constants;

import Mizdooni.Model.DAO;
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

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class UserDAO extends DAO {
    public void fetchFromAPI() throws Exception{
        String UsersJsonString = getRequest(Constants.GET_USERS_URL);
        ObjectMapper om = new ObjectMapper();
        ArrayList<User> users = om.readValue(UsersJsonString, new TypeReference<ArrayList<User>>(){});

        for (User user:
             users) {


        }
    }


}
