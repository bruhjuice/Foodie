import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.Helper;

import java.io.IOException;
import java.io.Serial;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class LoginDispatcher
 */
@WebServlet("/login")
public class LoginDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	boolean missingData = false;

    	String email = request.getParameter("loginEmail");
    	String password = request.getParameter("loginPassword");

    	if (email == null || email.contentEquals(""))
    	{
    		missingData = true;
    	}
    	if (password == null || password.contentEquals(""))
    	{
    		missingData = true;
    	}
    	if (!missingData)
    	{

    
    	try {
			if(!Helper.checkPassword(email, password))
				{
					missingData = true;
				}
			else
			{
		    	String name = Helper.getUserName(email);
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
			}
			Helper.getUserID(email);
			Helper.getUserName(email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}


    		if (!missingData)
    		{
    			System.out.println("login Success");
//        		request.getRequestDispatcher("/index.jsp").forward(request, response);
    			response.sendRedirect("index.jsp");
    			
    		}
    		else
    		{
        		System.out.println("Login Failed!");
        		request.setAttribute("error", "yes");
        		request.setAttribute("email", email);
        		request.setAttribute("password", password);
        		request.getRequestDispatcher("auth.jsp").forward(request, response);
    		}
    	

        //TODO
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
