package Util;

import java.util.ArrayList;
import java.util.List;

/**
 * The class used to model a business
 */
public class Business {
	private String id;
	private String name;
	private String image_url;
	private double rating;
	// is display address
	private Location location;
	private ArrayList<Category> categories;
	private String price;
	private String phone;
	private String url;
	private int review_count;
	
	
    

    public Business(String id2, String name2, String image_url2, double rating2, Location location2, ArrayList<Category> categories2, String price2, String phone2, String url2, int reviewCount2) {
        //TODO Initialization code
    	this.id = id2;
    	this.name = name2;
    	this.image_url = image_url2;
    	this.rating = rating2; 
    	this.location = location2;
    	this.categories = categories2;
    	this.price = price2;
    	this.phone = phone2;
    	this.url = url2;
    	this.review_count = reviewCount2;
    	
    }



	public String getId() {
		return id;
	}




	public String getName() {
		return name;
	}




	public String getImage_url() {
		return image_url;
	}




	public double getRating() {
		return rating;
	}




	public Location getLocation() {
		return location;
	}




	public ArrayList<Category> getCategory() {
		return categories;
	}




	public String getPrice() {
		return price;
	}




	public String getPhone() {
		return phone;
	}




	public String getUrl() {
		return url;
	}
	public ArrayList<String> getCategories()
	{
		ArrayList<String> catList = new ArrayList();
		for (Category c: categories)
		{
			catList.add(c.getTitle());
			
		}
		return catList;
		
	}
	
	public String getCategoryString()
	{
		String temp = "";
		for (Category c: categories)
		{
			temp += (c.getTitle());
			temp += ", ";
			
		}
		return temp;
		
	}
	
	
	public String getLocationAddy()
	{
		return location.getDisplayAddress();
	}




	public int getReviewCount() {
		return review_count;
	}
	public String toString()
	{
		return id + name + image_url + price + phone + url;
		
		
	}
	static class Location
	{
		private ArrayList<String> display_address;
		public Location(List<String> list) {
			display_address = (ArrayList<String>) list;
			// TODO Auto-generated constructor stub
		}
		public String getDisplayAddress()
		{
			String address = "";
			for(int i = 0; i < display_address.size(); i++)
			{
				if(display_address.get(i) != null)
				{
					address += display_address.get(i);
				}
				if(i != display_address.size()-1)
				{
					address += " ";
				}
		
			}
			return address;	
		}
	}
	static class Category
	{
		private String alias;
		private String title;
		public Category(String category_name) {
			title = category_name;
			// TODO Auto-generated constructor stub
		}
		public String getAlias() {
			return alias;
		}
		public void setAlias(String alias) {
			this.alias = alias;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		
	}
	
	static class BusinessHelper
	{
		private ArrayList<Business> businesses;
		public ArrayList<Business> getBusinesses()
		{
			return businesses;
		}
	}

    //TODO Add Getters (No Setters as the business does not change in our assignment once constructed)
}

