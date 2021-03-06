import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.Constant;
import Util.Helper;

import java.sql.*;
import java.io.*;
/**
 * Servlet implementation class RegisterDispatcher
 */
@WebServlet("/register")
public class RegisterDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String url = "jdbc:mysql://localhost:3306/PA4Users";

    /**
     * Default constructor.
     */
    public RegisterDispatcher() {
    }


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //TODO
    	boolean missingData = false;

    	String name = request.getParameter("registerName");
    	String email = request.getParameter("registerEmail");
    	String password = request.getParameter("registerPassword");
    	String confirmPassword = request.getParameter("confirmPassword");
    	if (!password.contentEquals(confirmPassword))
    	{
    		missingData = true;
    	}
    	if (name == null || name.contentEquals(""))
    	{
    		missingData = true;
    	}
    	if (!Helper.validName(name))
    	{
    		missingData = true;
    		
    	}
    	if (email == null || email.contentEquals(""))
    	{
    		missingData = true;
    	}
    	if (password == null || password.contentEquals(""))
    	{
    		missingData = true;
    		//emailError += "<p> email is missing </p>";
    	}
    	if (Helper.emailAlreadyRegistered(email, request, response))
    	{
    		missingData = true;
    	}
    	if (!Helper.isValidEmail(email))
    	{
    		missingData = true;
    		
    	}


    	



    	

    	if (!missingData)
    	{
    		String db = "jdbc:mysql://localhost:3306/SalEats";
    		String user = Constant.DBUserName;
    		String pwd = Constant.DBPassword;
    		String sql = "INSERT INTO Users (email, username, password) VALUES (?, ?, ?)";
    		// add the jar to tomcat lib if it is not working!

            try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        	try (Connection conn = DriverManager.getConnection(db, user, pwd);
        			PreparedStatement ps = conn.prepareStatement(sql);) {
        			ps.setString(1, email);
        			ps.setString(2, name);
        			ps.setString(3, password);
        			int row = ps.executeUpdate(); //the number of rows affected
        			String realName = "";
        			for (int i = 0; i < name.length(); i++)
        			{
        				if (name.charAt(i) == ' ')
        				{
        					realName += '=';
        				}
        				else
        				{
        					realName += name.charAt(i);
        				}
        			}
                	Cookie cookie = new Cookie("name", realName);
                	cookie.setMaxAge(60 * 60);
                	response.addCookie(cookie);
        			System.out.println(String.format("Row affected %d", row));

        		} catch (SQLException sqle) {
        			System.out.println ("SQLException: " + sqle.getMessage());
        		}

    		//request.getRequestDispatcher("index.jsp").forward(request, response);
        	response.sendRedirect("index.jsp");
    		
    		
    	}
    	else
    	{
    		request.setAttribute("regUsername", name);
    		request.setAttribute("regEmail", email);
    		request.setAttribute("regPassword", password);
    		request.setAttribute("error", "yes");
    		request.getRequestDispatcher("auth.jsp").forward(request, response);

    	}
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
