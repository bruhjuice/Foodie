import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.Business;
import Util.RestaurantDataParser;

import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Servlet implementation class SearchDispatcher
 */
@WebServlet("/search")
public class SearchDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public SearchDispatcher() {
    	
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        // TODO get json file as stream, Initialize FakeYelpAPI by calling its initalize
        // method
         RestaurantDataParser restaurantDataParser = new RestaurantDataParser();
         restaurantDataParser.Init(getServletInfo());
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        RestaurantDataParser restaurantDataParser = new RestaurantDataParser();
        ArrayList<Business> busisnesses = new ArrayList<Business>();
        try {
			busisnesses = RestaurantDataParser.getBusinesses(request.getParameter("searchbox"), request.getParameter("sortby"), request.getParameter("searchType"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        request.setAttribute("bArray", busisnesses);
		request.setAttribute("searchbox", request.getParameter("searchbox"));
		request.setAttribute("searchBy", request.getParameter("searchType"));
		request.getRequestDispatcher("search.jsp").forward(request, response);

        // TODO
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}