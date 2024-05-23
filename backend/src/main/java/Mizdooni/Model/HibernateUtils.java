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
}


