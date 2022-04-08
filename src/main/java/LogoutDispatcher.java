import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

/**
 * Servlet implementation class LogoutDispatcher
 */
@WebServlet("/logout")
public class LogoutDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    	{
    		Cookie[] cookies  = request.getCookies();

    		response.setContentType("text/html");

    		PrintWriter out = response.getWriter();
    		String title = "Delete Cookies";
    		out.println("<html>");
    		out.println("<head><title>" + title + "</title></head>");
    		out.println("<body>");
    		out.println("<h2>Deleted Cookies</h2>");
    		for (Cookie aCookie : cookies)
    			if((aCookie.getName( )).equals("name"))
    			{
    				aCookie.setMaxAge(0);
    				response.addCookie(aCookie);
    				out.print("Name = " + aCookie.getName( ) + ", Value = " + aCookie.getValue( )  + "<br/>");
    			}
    		out.println("</body>");
    		out.println("</html>");
    	}

		response.sendRedirect("index.jsp");


        // TODO Remove userID cookie

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doGet(request, response);
    }

}
