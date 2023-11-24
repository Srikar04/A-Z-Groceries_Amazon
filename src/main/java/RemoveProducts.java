

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
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet("/removeProducts")
public class RemoveProducts extends HttpServlet {
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] productNames = request.getParameterValues("productNames");
        String[] quantities = request.getParameterValues("quantities");
        String[] originalQuantities = new String[productNames.length];
        
        String shopkeeperName = (String) request.getSession().getAttribute("userName");

        for (int i = 0; i < productNames.length; i++) {
            String originalQuantityFieldName = "originalQuantity_" + productNames[i].replace(" ", "_");
            originalQuantities[i] = request.getParameter(originalQuantityFieldName);
        }
        
        Properties props = getConnectionData();
		String url = props.getProperty("db.url");
		String user = props.getProperty("db.user");
		String passwd = props.getProperty("db.passwd");
		
        
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
				
		try (Connection con = DriverManager.getConnection(url, user, passwd)) {
            String updateQuery = "UPDATE Product SET Quantity = ? WHERE Name = ? AND shopkeeper_username = ?";
			for(int i = 0;i<productNames.length;i++) {
				int newQuantity = Integer.parseInt(originalQuantities[i]) - Integer.parseInt(quantities[i]);
				
				
				try (PreparedStatement  preparedStatement = con.prepareStatement(updateQuery)) {
		            preparedStatement.setInt(1, newQuantity);
	                preparedStatement.setString(2, productNames[i]);
	                preparedStatement.setString(3, shopkeeperName );

	                int updatedRows = preparedStatement.executeUpdate();
	                
	                if(updatedRows <= 0){
	                	String errorMessage = "Some error has occured";
	        			request.setAttribute("errorMessage", errorMessage);
	                	request.getRequestDispatcher("prodAmazon.jsp").forward(request, response);
	                	return;
	                }
		        }
			}
	  		request.getRequestDispatcher("createXML").forward(request, response);
			con.close();
		} catch (SQLException ex) {
			String errorMessage = "Some error has occured";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("prodAmazon.jsp").forward(request, response);
		}
	}

}
