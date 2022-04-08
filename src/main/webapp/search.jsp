<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
            href="https://fonts.googleapis.com/css2?family=Lobster&family=Roboto:wght@300&display=swap"
            rel="stylesheet">
    <link rel="stylesheet" href="index.css">
    <script src="https://kit.fontawesome.com/3204349982.js"
            crossorigin="anonymous"></script>


</head>
         <%
         boolean loggedIn = false;
         Cookie cookie = null;
         Cookie[] cookies = null;
         
         // Get an array of Cookies associated with the this domain
         cookies = request.getCookies();
         

      %>
<body>

        <ul class = "navBar">
  			<li><a href="index.jsp" class = "logo">Foodie!</a></li>
  			  			<% if (cookies != null){
  				for (int i = 0; i < cookies.length; i++){
  					cookie = cookies[i];
  					if((cookie.getName( )).equals("name")  )

  					{
  						
  						String realName = "";
 						String cookieName = cookie.getValue();
 						for (int j= 0; j < cookieName.length(); j++)
 						{
 							if (cookieName.charAt(j) == '=')
 							{
 								realName += ' ';
 							}
 							else
 							{
 								realName += cookieName.charAt(j);
 							}
 						}
  						loggedIn = true;
  						out.print("<li id = 'topHelloBar'><a id = 'helloBar'> Hi " + realName +"!" +  "</a></li>");
  					}
  					
  				}
  				
  			}%>
  			<%if (loggedIn) {
  	  			out.print("<li style = 'float:right'><a href='logout'>Logout</a></li>");
  				
  			}
  			else
  			{
  	  			out.print("<li style = 'float:right'><a href='auth.jsp'>Login/Register</a></li>");
	
  			}
  			
  			%>
  			<li style = "float:right"><a href="index.jsp">Home</a></li>
		</ul>
	
	    <form action = "search" method = "GET" class = homepageForm>
		<select id="searchType" name="searchType">
  		<option value="category">Category</option>
  		<option value="restaurant name">Restaurant Name</option>
  		</select>
  				
  				<%
  				String searchType = (String) request.getAttribute("searchBy");
  				String searched = (String) request.getAttribute("searchbox");
				if (searched == null)
				{
					searched = "isnull";
				}

				%>
  		<input type = "text" id = "searchbox" name = "searchbox" value = "<%=searched%>">
        <button type = "submit"> <i class = 'fa fa-search'> </i> </button>
        <label>
        <input class = "options" type = "radio" name = "sortby" value = "price" checked = "checked">
        Price
        </label>
        <label>
        <input class = "options" type = "radio" name = "sortby" value = "review count">
        Review Count
        </label>
       <label>
        <input class = "options" type = "radio" name = "sortby" value = "rating">
        Rating
        </label>
        
        </form>
        <br>
        <br>
        <br>
     
        <h2 id = "resultLabel"> Results for <%=searched%> in <%=searchType %></h2>
        <hr>
     <%@page import="java.util.ArrayList"
      import = "Util.Business"%>
        
                
         <%
         ArrayList<Business> biz = new ArrayList<Business>();
         biz = (ArrayList<Business>)request.getAttribute("bArray");
        if (biz.size() == 0 || biz == null)
        {
        	out.print("<h2 id = 'noResults'> No results were found... </h2>");
        }
        else
        {
        	
        

        for (Business b: biz)
        {
        	%>
        	
        	<div class = "container">
        		<img class = "restrauntImage" src = <%=b.getImage_url()%>>
        		<div class = "restText">
        		<a href = "details.jsp?bizId=<%=b.getId()%>"> <%=b.getName()%> </a>
        		<%
        		String price = b.getPrice();
        		if (price == null || price.contentEquals("") || price.contentEquals("null"))
        		{
        			price = "N/A";
        		}        		
        		%>
        		<p>Price: <%=price%></p>
        		<p>Review Count: <%=b.getReviewCount()%></p>
        		
        		<%
        		int stars = (int) b.getRating();
        		for (int i = 0; i < stars; i++)
        		{
        			if (i == 0)
        			{
        				out.print("Rating: ");
        			}
        			out.print("<i class = 'fa fa-star'> </i>");

        		}
        		%>
        		<br><br>
        		<a href = "<%=b.getUrl()%>" target = "_blank" >Yelp Link</a>
        		
        		</div>
        		
        	
        	</div>
        	<hr>
        	
        	<%
        }
    }

      %> 
        
        
        
</body>
</html>