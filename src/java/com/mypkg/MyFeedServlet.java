package com.mypkg;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Prava
 */
public class MyFeedServlet extends HttpServlet {

    private static String dbURL = "jdbc:derby://localhost:1527/newsfeed;user=user123;password=password";
    private static String tableName = "tabs";
    // jdbc Connection
    private Connection conn = null;
    private static Statement stmt = null;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html; charset=UTF-8");
        // Allocate a output writer to write the response message into the network socket
        PrintWriter out = response.getWriter();

        try {
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
            out.println("<title>Echo Servlet</title></head>");
            out.println("<body><h3>NewsFeed</h3>");
            String str = createSection();
            out.println(str);
            //out.println("</body></html>");
            out.println("</body></html>");
        } catch (Exception ex) {
            Logger.getLogger(MyFeedServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();

        }

    }

    public String createSection() throws Exception {
        StringBuilder str = new StringBuilder("");
        String name = "";
        String title = "";
        Integer likes = 0;
        String body = "";
        try {
            createConnection();
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM USER123.TABS");
            while (rs.next()) {
                str.append("<fieldset>");
                title = rs.getString("title");
                likes = rs.getInt("likes");
                body = rs.getString("body");
                str.append("<h4>").append(title).append("</h4>");
                str.append("<p>").append(body).append("</p>");
                str.append("<button type=\"button\">").append(likes).append("</button>").append(" Likes");
                str.append("</fieldset>");
            }
            conn.close();
        } catch (Exception except) {
            except.printStackTrace();
        }
        return str.toString();
    }

    private void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            conn = DriverManager.getConnection(dbURL);
        } catch (Exception except) {
            except.printStackTrace();
        }
    }
}
