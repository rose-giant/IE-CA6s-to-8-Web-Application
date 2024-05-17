package Mizdooni.Model.User;

import Mizdooni.Model.Address;
import Mizdooni.Model.DAO;
import Mizdooni.Model.HibernateUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.sql.*;

import static Mizdooni.Model.Constants.CLIENTS_TABLE_NAME;
import static Mizdooni.Model.Constants.MANAGERS_TABLE_NAME;

public class UserDAO extends DAO<User> {


    protected void fillInsertValues(PreparedStatement st, User user) throws SQLException {
        st.setString(1, user.getUsername());
        st.setString(2, user.getPassword());
        st.setString(3, user.getEmail());
        st.setString(4, user.getAddress().toString());
    }
    @Override
    public void addToDatabase(User user) throws SQLException {
//        Connection con = HibernateUtils.getConnection();
//        String insertQuery = getInsertRecordQuery(user.getRole());
//        PreparedStatement st = con.prepareStatement(insertQuery);
//        fillInsertValues(st, user);
//        System.out.println(st);
//        st.execute();
//        SQLWarning warning = st.getWarnings();
//        if (warning != null) {
//            throw new SQLWarning(warning.getMessage());
//        }
//        st.close();
//        con.close();
        EntityManagerFactory emf = HibernateUtils.getEmf();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        System.out.println("heereeeskldjfskl\n");
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    private String getInsertRecordQuery(String role) {
        return String.format("INSERT IGNORE INTO %s(username, password, email, address) VALUES(?,?,?,?)", role );
    }

    protected String getCreateTableQuery(String tableName) {
       return   String.format(
                "CREATE TABLE IF NOT EXISTS %s " +
                        "(username CHAR(225),\npassword CHAR(225),\nemail CHAR(225),\naddress CHAR(225),\nPRIMARY KEY(username));",
                tableName);

    }

    @Override
    protected String getAllQuery() {
        return "SELECT * FROM " + MANAGERS_TABLE_NAME + " UNION SELECT * FROM " + CLIENTS_TABLE_NAME +";";
    }

    @Override
    protected User convertToDomainModel(ResultSet rs) {
        try{
            Address ad = new Address().toAddress(rs.getString(4));
            return new User(ad, rs.getString(3), rs.getString(2), "user", rs.getString(1));
        }
        catch (Exception e){
            System.out.println("convertToDomainModelError: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected String getInsertRecordQuery() {return null;}

    private String getFindByFieldQuery(String fieldName, String tableName) {
        return String.format("SELECT * FROM %s u WHERE u.%s = ?;", tableName, fieldName);
    }

    public User findByField(String value, String field_name, String tableName) throws SQLException {
        Connection con = HibernateUtils.getConnection();
        String query = getFindByFieldQuery(field_name, tableName);
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1,value);
        System.out.println(st);
        try {
            ResultSet resultSet = st.executeQuery();
            if (!resultSet.next()) {
                st.close();
                con.close();
                return null;
            }
            User result = convertToDomainModel(resultSet);
            System.out.println("result: "+result);
            st.close();
            con.close();
            return result;
        } catch (Exception e) {
            st.close();
            con.close();
            System.out.println("error in Repository.find query.");
            e.printStackTrace();
            throw e;
        }
    }


}
