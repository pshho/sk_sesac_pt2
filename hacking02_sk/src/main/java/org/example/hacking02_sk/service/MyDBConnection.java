package org.example.hacking02_sk.service;

import java.sql.Connection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class MyDBConnection {
    private static Connection conn;


    public static void setConnection(Connection connection){
        conn = connection;
    }

    public static Connection getConnection(){
        return conn;
    }

    @Autowired
    public MyDBConnection(DataSource dataSource) {
        try {
            MyDBConnection.setConnection(dataSource.getConnection());

            System.out.println(conn.toString() + "커넥션");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
