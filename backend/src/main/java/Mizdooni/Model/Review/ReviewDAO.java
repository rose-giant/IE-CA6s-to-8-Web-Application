package Mizdooni.Model.Review;

import Mizdooni.Model.Constants;
import Mizdooni.Model.DAO;
import Mizdooni.Model.User.User;
import Mizdooni.Model.HibernateUtils;
import Mizdooni.Model.User.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewDAO extends DAO {

    private static final String TABLE_NAME = "reviews";

    protected void fillInsertValues(PreparedStatement st, Review review) throws SQLException {
        st.setDouble(1, review.ambianceRate);
        st.setString(2, review.comment);
        st.setDouble(3, review.foodRate);
        st.setDouble(4, review.overallRate);
        st.setDouble(5, review.serviceRate);
        st.setString(6, review.restaurantName);
        st.setString(7, review.username);
    }

    public void addToDatabase(Review review) throws SQLException {
        Connection con = HibernateUtils.getConnection();
        String insertQuery = getInsertRecordQuery();
        PreparedStatement st = con.prepareStatement(insertQuery);
        fillInsertValues(st, review);
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
        return String.format("INSERT INTO %s(ambiance_rate, comment, food_rate, overall_rate, service_rate, review_restaurant, review_username) VALUES(?, ?, ?, ?, ?, ?, ?)", Constants.REVIEWS_TABLE_NAME);
    }

    private String getReviewsByRestaurantQuery(String restaurantName) {
        return String.format("SELECT * FROM %s WHERE review_restaurant = '%s'", TABLE_NAME, restaurantName);
    }

    public String getCreateTableQuery(String tableName) {
        return String.format(
                        "CREATE TABLE %s (\n" +
                                "    id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                                "    ambiance_rate DOUBLE,\n" +
                                "    comment VARCHAR(255),\n" +
                                "    food_rate DOUBLE,\n" +
                                "    overall_rate DOUBLE,\n" +
                                "    service_rate DOUBLE,\n" +
                                "    review_restaurant VARCHAR(255),\n" +
                                "    review_username VARCHAR(255),\n" +
                                "    FOREIGN KEY (review_restaurant) REFERENCES restaurant (name),\n" +
                                "    FOREIGN KEY (review_username) REFERENCES client (username)\n" +
                                ");",
                        tableName);
    }


    protected User convertToDomainModel(ResultSet res, String tableName) {
        return null;
    }

}
