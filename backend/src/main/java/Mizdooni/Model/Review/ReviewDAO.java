package Mizdooni.Model.Review;

import Mizdooni.Model.Constants;
import Mizdooni.Model.DAO;
import Mizdooni.Model.Table.TableRest;
import Mizdooni.Model.User.User;
import Mizdooni.Model.HibernateUtils;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Mizdooni.Model.Constants.REVIEWS_TABLE_NAME;

public class ReviewDAO extends DAO<Review> {

    private static final String TABLE_NAME = "reviews";

    @Override
    protected void fillInsertValues(PreparedStatement st, Review review) throws SQLException {
        st.setDouble(1, review.ambianceRate);
        st.setString(2, review.comment);
        st.setDouble(3, review.foodRate);
        st.setDouble(4, review.overallRate);
        st.setDouble(5, review.serviceRate);
        st.setString(6, review.restaurantName);
        st.setString(7, review.username);
    }

    @Override
    protected String getInsertRecordQuery() {
        return String.format("INSERT INTO %s(ambiance_rate, comment, food_rate, overall_rate, service_rate, review_restaurant, review_username) VALUES(?, ?, ?, ?, ?, ?, ?)", Constants.REVIEWS_TABLE_NAME);
    }

    private String getReviewsByRestaurantQuery(String restaurantName) {
        return String.format("SELECT * FROM %s WHERE review_restaurant = '%s';", TABLE_NAME, restaurantName);
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
                                "    review_restaurant CHAR(225),\n" +
                                "    review_username CHAR(225),\n" +
                                "    FOREIGN KEY (review_restaurant) REFERENCES restaurant (name),\n" +
                                "    FOREIGN KEY (review_username) REFERENCES client (username)\n" +
                                ");",
                        tableName);
    }

    @Override
    protected String getAllQuery() {
        return "SELECT * FROM " + REVIEWS_TABLE_NAME + ";";
    }


    @Override
    protected Review convertToDomainModel(ResultSet rs) {
        try{
            return new Review(rs.getDouble(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getString(7), rs.getDouble(6), rs.getString(8));
        }
        catch (Exception e){
            System.out.println("convertToDomainModelError: " + e.getMessage());
            return null;
        }
    }

}
