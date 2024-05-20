package Mizdooni.Model;




import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class HibernateUtils {
//    private static SessionFactory sessionFactory;


//    static {
//        try {
//            Configuration configuration = new Configuration();
//            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
//            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/mizdooni");
//            configuration.setProperty("hibernate.connection.username", "root");
//            configuration.setProperty("hibernate.connection.password", "rootpassword");
//            sessionFactory = configuration.buildSessionFactory();
//        } catch (Throwable ex) {
//            throw new ExceptionInInitializerError(ex);
//        }
//    }

//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }


    private static final BasicDataSource ds = new BasicDataSource();
    private final static String dbURL = "jdbc:mysql://localhost:3306/mizdooni";
    private final static String dbUserName = "root";
    private final static String dbPassword = "rootpassword";

    static {
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl(dbURL + "?useUnicode=true&characterEncoding=UTF-8");
        ds.setUsername("root");
        ds.setPassword("rootpassword");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(4000);
        System.out.println("max active::::" +ds.getMaxActive()+"----"+ ds.toString());
        setEncoding();
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void setEncoding(){
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute("ALTER DATABASE mizdooni CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");
            connection.close();
            statement.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

//    public static EntityManagerFactory getEmf() {
//        EntityManagerFactory emf;
//        Map<String, String> persistenceProperties = new HashMap<>();
//        persistenceProperties.put("jakarta.persistence.jdbc.url", "jdbc:mysql://localhost:3306/mizdooni");
//        persistenceProperties.put("jakarta.persistence.jdbc.user", "root");
//        persistenceProperties.put("jakarta.persistence.jdbc.password", "rootpassword");
//        persistenceProperties.put("jakarta.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
//        persistenceProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//        persistenceProperties.put("hibernate.show_sql", "true");
//
//        // Add entity classes
//        persistenceProperties.put("jakarta.persistence.mapping.entities", "Mizdooni.Model.User, Mizdooni.Model.Restaurant");
//        // Create EntityManagerFactory programmatically
//        emf = Persistence.createEntityManagerFactory("mizdooni-persistence-unit", persistenceProperties);
//
//        return emf;
//    }
}


