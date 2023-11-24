import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.Product;

@WebServlet("/searchProduct")
public class searchProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static Properties getConnectionData() {
		Properties props = new Properties();

		String fileName = "/home/srikar/eclipse-workspace/21MCME19_LA2/src/main/java/db.properties";

		try (FileInputStream in = new FileInputStream(fileName)) {
			props.load(in);
		} catch (IOException ex) {
			Logger lgr = Logger.getLogger(SignIn.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		return props;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String productName = request.getParameter("productName");
				
		Properties props = getConnectionData();
		String url = props.getProperty("db.url");
		String user = props.getProperty("db.user");
		String passwd = props.getProperty("db.passwd");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		List<Product> products = new ArrayList<>();
		
		try (Connection con = DriverManager.getConnection(url, user, passwd)) {
			String query = "select * from Product WHERE Name LIKE ? ";
			
			  try (PreparedStatement stmt = con.prepareStatement(query)) {
				  stmt.setString(1, "%"+productName+"%");
		            ResultSet rs = stmt.executeQuery();
		            while (rs.next()) {
		                int id = rs.getInt("ProductID");
		                String name = rs.getString("Name");
		                String description = rs.getString("Description");
		                double price = rs.getDouble("Price");
		                String imageUrl = rs.getString("Image_URL");
		                int quantity = rs.getInt("Quantity");
		                String shopkeeperUsername = rs.getString("shopkeeper_username"); // Retrieve shopkeeper_username
		                
		                // Create a Product object and add it to the ArrayList
		                Product product = new Product(id, name, description, price, imageUrl, quantity, shopkeeperUsername);
		                products.add(product);
		            }
		        }
			  
			    request.setAttribute("products", products);
			    request.getRequestDispatcher("homePage.jsp?productName"+productName).forward(request, response);
			
			con.close();
		} catch (SQLException ex) {
			String errorMessage = "Some error has occured";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("homePage.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
