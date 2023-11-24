import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.Transaction;

@WebServlet("/reportUsers")
public class ReportUsers extends HttpServlet {
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
		
		String shopkeeperName = (String) request.getSession().getAttribute("userName");
		String customerName = request.getParameter("userName");
		
		
		Properties props = getConnectionData();
		String url = props.getProperty("db.url");
		String user = props.getProperty("db.user");
		String passwd = props.getProperty("db.passwd");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		try (Connection con = DriverManager.getConnection(url, user, passwd)) {
			String query = "SELECT * FROM Transaction WHERE customer_username = ? AND shopkeeper_username = ?";
			
			  try (PreparedStatement stmt = con.prepareStatement(query)) {
				  	stmt.setString(1, customerName);
				  	stmt.setString(2, shopkeeperName);
		            ResultSet rs = stmt.executeQuery();
		            while (rs.next()) {
		            	int transactionId = rs.getInt("transaction_id");
		                String customerUsername = rs.getString("customer_username");
		                int productId = rs.getInt("product_id");
		                Date transactionDate = rs.getDate("transaction_date");
		                String shopkeeperUsername = rs.getString("shopkeeper_username");
		                double transactionAmount = rs.getDouble("transaction_amount");
		                int quantity = rs.getInt("quantity"); 
		                
		                
		                System.out.println(transactionId);

		                // Create a Transaction object
		                Transaction transaction = new Transaction(transactionId, customerUsername, productId,
		                		quantity, transactionDate, shopkeeperUsername, transactionAmount);

		                transactions.add(transaction);
		            }
		        }
			  
			    request.setAttribute("Transactions", transactions);
			    request.getRequestDispatcher("userNameReport.jsp").forward(request, response);
			
			con.close();
		} catch (SQLException ex) {
			String errorMessage = "Some error has occured";
			System.out.println(errorMessage);
		}
	}

}
