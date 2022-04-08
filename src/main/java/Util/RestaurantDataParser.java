package Util;


import com.google.gson.*;

import Util.Business.BusinessHelper;
import Util.Business.Category;
import Util.Business.Location;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.servlet.annotation.*;
import javax.servlet.http.Cookie;

/**
 * A class that pretends to be the Yelp API
 */
public class RestaurantDataParser {
    private static Boolean ready = false;

    /**
     * Initializes the DB with json data
     *
     * @param responseString the Yelp json string
     */
    public static void Init(String responseString) {
        if (ready) {
            return;
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //TODO check if you've done the initialization
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ready = true;
        
        

		String initQuery = "DROP TABLE IF EXISTS Restaurant_details, rating_details, restaurant, category;";
		String initRestD = "CREATE TABLE Restaurant_details (\n"
				+ "  details_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n"
				+ "  image_url VARCHAR(400) NOT NULL,\n"
				+ "  address VARCHAR(300) NOT NULL,\n"
				+ "  phone_no VARCHAR(200) NOT NULL,\n"
				+ "  estimated_price VARCHAR(200),\n"
				+ "  yelp_url VARCHAR(400) NOT NULL\n"
				+ ");";
		String initRated = "CREATE TABLE Rating_details (\n"
				+ "  rating_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n"
				+ "  review_count INT NOT NULL,\n"
				+ "  rating double NOT NULL\n"
				+ ");";
		String initRest = "CREATE TABLE Restaurant (\n"
				+ "  restaurant_id VARCHAR(300) PRIMARY KEY NOT NULL,\n"
				+ "  restaurant_name VARCHAR(300) NOT NULL,\n"
				+ "  details_id int,\n"
				+ "  rating_id int,\n"
				+ "  FOREIGN KEY (details_id) REFERENCES Restaurant_details(details_id),\n"
				+ "  FOREIGN KEY (rating_id) REFERENCES Rating_details(rating_id)\n"
				+ ");";
		String initCategory = "CREATE TABLE Category (\n"
				+ "  category_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n"
				+ "  category_name VARCHAR(100) NOT NULL,\n"
				+ "  restaurant_id VARCHAR(300) NOT NULL\n"
				+ ");";



        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        	
        
        	try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SalEats", Constant.DBUserName, Constant.DBPassword);
        			PreparedStatement ps = conn.prepareStatement(initQuery);
        			PreparedStatement ps1 = conn.prepareStatement(initRestD);
        			PreparedStatement ps2 = conn.prepareStatement(initRated);
        			PreparedStatement ps3 = conn.prepareStatement(initRest);
        			PreparedStatement ps4 = conn.prepareStatement(initCategory);





        			) {


        			ps.executeUpdate();
        			ps1.executeUpdate();
        			ps2.executeUpdate();
        			ps3.executeUpdate();
        			ps4.executeUpdate();

        		} catch (SQLException sqle) {
        			System.out.println ("SQLException: " + sqle.getMessage());
        		}
        

        
        

        
    		String temp = "";
    		Scanner sc;
    		boolean flag = true;
    		if (flag)
    		{
        		System.out.println("beginning parse");

    		
	    			
	    		Gson gson = new Gson();
				
				InputStream inputStream = RestaurantDataParser.class.getResourceAsStream(Constant.FileName);
				if (inputStream == null)
				{
					System.out.println("file not found");
				}
				else
				{
					System.out.println("file found");
					
				}
				sc = new Scanner(inputStream);
				while (sc.hasNext())
				{
					temp += sc.nextLine();
					
				}
				BusinessHelper helper = new BusinessHelper();
				helper = gson.fromJson(temp, BusinessHelper.class);
				ArrayList<Business> allBusiness = new ArrayList<Business>();
				for(int i = 0; i < helper.getBusinesses().size(); i++)
				{
					if(helper.getBusinesses().get(i) != null)
					{
				
						allBusiness.add(helper.getBusinesses().get(i));
					}

				}
				
	    		String db = "jdbc:mysql://localhost:3306/SalEats";
	    		String user = Constant.DBUserName;
	    		String pwd = Constant.DBPassword;
	    		String firstQuery = "INSERT INTO Restaurant_details (image_url, address, phone_no,  estimated_price, yelp_url) VALUES (?, ?, ?, ?, ?);";
	    		String secondQuery = "INSERT INTO rating_details (review_count, rating) VALUES (?, ?);";
	    		String thirdQuery = "INSERT INTO restaurant (restaurant_id, restaurant_name, details_id, rating_id) VALUES (?, ?, ?, ?);";
	    		String fourthQuery = "INSERT INTO category (category_name, restaurant_id) VALUES (?, ?);";


	            try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            System.out.println("");
	            
	            for (int i = 0; i < allBusiness.size(); i++)
	            {
	            	int id = i + 1;
	            	Business biz = allBusiness.get(i);
	            	
	            
		        	try (Connection conn = DriverManager.getConnection(db, user, pwd);
		        			PreparedStatement ps = conn.prepareStatement(firstQuery);
		        			PreparedStatement ps2 = conn.prepareStatement(secondQuery);
		        			PreparedStatement ps3 = conn.prepareStatement(thirdQuery);
		        			PreparedStatement ps4 = conn.prepareStatement(fourthQuery);
	
	
		        			) {
		        			ps.setString(1, biz.getImage_url());
		        			ps.setString(2, biz.getLocation().getDisplayAddress());
		        			ps.setString(3, biz.getPhone());
		        			ps.setString(4, biz.getPrice());
		        			ps.setString(5, biz.getUrl());
		        			
		        			ps2.setInt(1, biz.getReviewCount());
		        			ps2.setDouble(2, biz.getRating());
		        			
		        			ps3.setString(1, biz.getId());
		        			ps3.setString(2, biz.getName());
		        			ps3.setInt(3, id);
		        			ps3.setInt(4, id);


		        			ps.executeUpdate();
		        			ps2.executeUpdate();
		        			ps3.executeUpdate();
		        			
		        			ps4.setString(2, biz.getId());
		        			ArrayList<String> categories = biz.getCategories(); 
		        			
		        			for (int j = 0; j < biz.getCategory().size(); j++)
		        			{
		        				String tempCategory = biz.getCategory().get(j).getTitle();
		        				ps4.setString(1, tempCategory);
		        				ps4.executeUpdate();
		        				
		        			}
	
		        		} catch (SQLException sqle) {
		        			System.out.println ("SQLException: " + sqle.getMessage());
		        		}
	            }

					
    		}
    		




    	}
        
        
        
        //TODO get businessHelper array from json
        //TODO iterate the businessHelper array and insert every business into the DB
    

    public static Business getBusiness(String id) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        String db = "jdbc:mysql://localhost:3306/SalEats";
		String user = Constant.DBUserName;
		String pwd = Constant.DBPassword;
		String sql = "SELECT r.restaurant_id, r.restaurant_name, restd.image_url, restd.address, restd.phone_no, restd.estimated_price, "
				+ "restd.yelp_url,  c.category_name, rated.review_count, rated.rating from restaurant r, restaurant_details restd, category c, rating_details rated " 
				+ "where r.restaurant_id = c.restaurant_id and r.details_id = restd.details_id and r.rating_id = rated.rating_id and r.restaurant_id = '"+id+"' ";
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
				CallableStatement stmt = conn.prepareCall(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);)
		{
			ResultSet resultSet = stmt.executeQuery();
			
			String selectId = "";
			String name = "";
			String image_url = "";
			String phoneNum = "";
			String price = "";
			String yelp_url = "";
			int reviewCount = 0;
			double rating = 0;
			String address = "";
			Location location = null;
			String category_name = "";
			ArrayList<String> categoryStrings = new ArrayList<String>();
			ArrayList<Category> categories = new ArrayList<Category>();
			while (resultSet.next())
			{
				id = resultSet.getString("restaurant_id");
				name = resultSet.getString("restaurant_name");
				image_url = resultSet.getString("image_url");
				phoneNum = resultSet.getString("phone_no");
				price = resultSet.getString("estimated_price");
				yelp_url = resultSet.getString("yelp_url");
				reviewCount = resultSet.getInt("review_count");
				rating = resultSet.getDouble("rating");
				address = resultSet.getString("address");
				category_name = resultSet.getString("category_name");
				List<String> list = new ArrayList<String>();
				list.add(address);
				location = new Location(list);
				Category cat = new Category(category_name);
				categories.add(cat);
				
			
				if (resultSet.next())
				{

					if (!resultSet.getString("restaurant_name").equals(name))
					{
						Business b = new Business(id, name, image_url, rating, location, categories, price, phoneNum, yelp_url, reviewCount);
						return b;
					}
					resultSet.previous();
					
				}
			else
				{
					Business b = new Business(id, name, image_url, rating, location, categories, price, phoneNum, yelp_url, reviewCount);
					return b;
				}
			
			}
			Business b = new Business(id, name, image_url, rating, location, categories, price, phoneNum, yelp_url, reviewCount);
			return b;
	}
		
        
        
        
        
        //TODO return business based on id
    }

    /**
     * @param keyWord    the search keyword
     * @param sort       the sort option (price, review count, rating)
     * @param searchType search in category or name
     * @return the list of business matching the criteria
     * @throws SQLException 
     */
    public static ArrayList<Business> getBusinesses(String keyWord, String sort, String searchType) throws SQLException {
        ArrayList<Business> busisnesses = new ArrayList<Business>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    	String db = "jdbc:mysql://localhost:3306/SalEats";
		String user = Constant.DBUserName;
		String pwd = Constant.DBPassword;
		String sql = "SELECT r.restaurant_id, r.restaurant_name, restd.image_url, restd.address, restd.phone_no, restd.estimated_price, "
				+ "restd.yelp_url,  c.category_name, rated.review_count, rated.rating from restaurant r, restaurant_details restd, category c, rating_details rated " 
				+ "where r.restaurant_id = c.restaurant_id and r.details_id = restd.details_id and r.rating_id = rated.rating_id and searchType LIKE ? ";
		String priceSort = "ORDER BY restd.estimated_price, r.restaurant_name ASC;";
		String ratingSort = "ORDER BY rated.rating DESC, r.restaurant_name ASC;";
		String reviewSort = "ORDER BY rated.review_count DESC, r.restaurant_name ASC;";
		if (searchType.equals("restaurant name"))
		{
			sql = sql.replace("searchType", "r.restaurant_name");
		}
		else
		{
			sql = sql.replace("searchType", "c.category_name");
		}
		if (sort.equals("price"))
		{
			sql += priceSort;
		}
		else if (sort.equals("review count"))
		{
			sql += reviewSort;
		}
		else if (sort.equals("rating"))
		{
			sql += ratingSort;
		}
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
				CallableStatement stmt = conn.prepareCall(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);)
		{
			stmt.setString(1, "%"+keyWord+"%");
			ResultSet resultSet = stmt.executeQuery();
			
			String id = "";
			String name = "";
			String image_url = "";
			String phoneNum = "";
			String price = "";
			String yelp_url = "";
			int reviewCount = 0;
			double rating = 0;
			String address = "";
			Location location = null;
			String category_name = "";
			ArrayList<Category> categories = new ArrayList<Category>();
			while (resultSet.next())
			{
				id = resultSet.getString("restaurant_id");
				name = resultSet.getString("restaurant_name");
				image_url = resultSet.getString("image_url");
				phoneNum = resultSet.getString("phone_no");
				price = resultSet.getString("estimated_price");
				yelp_url = resultSet.getString("yelp_url");
				reviewCount = resultSet.getInt("review_count");
				rating = resultSet.getDouble("rating");
				address = resultSet.getString("address");
				category_name = resultSet.getString("category_name");

				
				List<String> list = new ArrayList<String>();
				list.add(address);
				location = new Location(list);
				Category cat = new Category(category_name);
				categories.add(cat);
				
			
				if (resultSet.next())
				{

					if (!resultSet.getString("restaurant_name").equals(name))
					{
						Business b = new Business(id, name, image_url, rating, location, categories, price, phoneNum, yelp_url, reviewCount);
						busisnesses.add(b);
						categories.clear();
					}
					resultSet.previous();
					
				}
			else
				{
					Business b = new Business(id, name, image_url, rating, location, categories, price, phoneNum, yelp_url, reviewCount);
					busisnesses.add(b);
				}
			}
	}
        
        
        //TODO get list of business based on the param
        return busisnesses;

    }
    public static void main(String[] args)
    {
    	Init("d");
    }
}



////Code adapted from https://stackoverflow.com/questions/23070298/get-nested-json-object-with-gson-using-retrofit
//class BusinessDeserializer implements JsonDeserializer<BusinessHelper> {
//    @Override
//    public BusinessHelper deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
//        JsonElement content = je.getAsJsonObject();
//        return new Gson().fromJson(content, BusinessHelper.class);
//    }
//}