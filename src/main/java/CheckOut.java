import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.CartItem;

@WebServlet("/checkOut")
public class CheckOut extends HttpServlet {
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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			HttpSession session= request.getSession();

			synchronized (session){
				@SuppressWarnings("unchecked")
				List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
				
				String customerName = (String) session.getAttribute("userName");
				
				if(cart == null || customerName == null) {
					response.sendRedirect("failure.jsp");
					return;
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
				Connection con = null;
				
			    try {
			        con = DriverManager.getConnection(url, user, passwd);
					con.setAutoCommit(false);
					con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
					for(CartItem item : cart) {
						String selectQuery = "select * from Product where ProductId = ?";
						int originalQuantity = 0;
						try (PreparedStatement stmt = con.prepareStatement(selectQuery)) {
				            stmt.setInt(1, item.getProductId());
				            try (ResultSet rs = stmt.executeQuery()) {
				                if (rs.next()) {
				                    originalQuantity = rs.getInt("quantity");
				                }
				            }
				        }
						
						if (originalQuantity < item.getQuantity()) {
		                    con.rollback(); // Roll back the transaction
		                    response.sendRedirect("failure.jsp"); 
		                    return; // Exit the loop and transaction
		                }
						
						// Update the Product table with the new quantity
				        int newQuantity = originalQuantity - item.getQuantity();
				        String updateQuery = "UPDATE Product SET quantity = ? WHERE ProductId = ?";
				        try (PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {
				            updateStmt.setInt(1, newQuantity);
				            updateStmt.setInt(2, item.getProductId());
				            updateStmt.executeUpdate();
				        }
				        
				        String shopkeeperName = "";
				        
				        // getting shopkeeper name of each product
				        String sqlQuery = "SELECT shopkeeper_username FROM Product WHERE ProductID = ?";
				        try( PreparedStatement preparedStatement = con.prepareStatement(sqlQuery)){
				        	preparedStatement.setInt(1,item.getProductId());
				        	 try (ResultSet resultSet = preparedStatement.executeQuery()) {
				                 if (resultSet.next()) {
				                     shopkeeperName = resultSet.getString("shopkeeper_username");
				                 }
				             }
				        }
				        
				        
				        // Adding values into transaction table
				        
				        String transactionQuery = "INSERT INTO Transaction (customer_username,product_id,quantity,transaction_date,shopkeeper_username, transaction_amount) "
				        		+ "VALUES (?, ?, ?,CURDATE(), ?, ?)";
				        try(PreparedStatement tranStmt = con.prepareStatement(transactionQuery)){
				        	tranStmt.setString(1, customerName);
				            tranStmt.setInt(2, item.getProductId());
				            tranStmt.setInt(3, item.getQuantity());  
				            tranStmt.setString(4, shopkeeperName);
				            tranStmt.setDouble(5, item.getCost());
				            tranStmt.executeUpdate();
				        }
					}
					
					con.commit();
					session.setAttribute("cart", null);
					response.sendRedirect("success.jsp");
				}catch (SQLException ex) {
					try {
						con.rollback();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String errorMessage = "Some error has occured";
					System.out.println(errorMessage);
					response.sendRedirect("failure.jsp");
				}finally {
				    try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	}

}
