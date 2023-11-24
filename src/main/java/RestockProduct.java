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


@WebServlet("/RestockProduct")
public class RestockProduct extends HttpServlet {
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
		
		Properties props = getConnectionData();
		String url = props.getProperty("db.url");
		String user = props.getProperty("db.user");
		String passwd = props.getProperty("db.passwd");
		
		int productId = Integer.parseInt(request.getParameter("productId"));
        int addItems = Integer.parseInt(request.getParameter("addItems"));
        int currentQuantity = Integer.parseInt(request.getParameter("quantity"));
        
        int newQuantity = currentQuantity + addItems;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
				
		try (Connection con = DriverManager.getConnection(url, user, passwd)) {
            String updateQuery = "UPDATE Product SET Quantity = ? WHERE ProductID = ?";
			
			  try (PreparedStatement  preparedStatement = con.prepareStatement(updateQuery)) {
		            preparedStatement.setInt(1, newQuantity);
	                preparedStatement.setInt(2, productId);

	                int updatedRows = preparedStatement.executeUpdate();
	                
	                if (updatedRows > 0) {
	                	System.out.println("Product quantity updated Sucessfully.");
				  		response.sendRedirect("ShopkeeperProd");
	                } else {
	                	System.out.println("Some error occured. Updating Product failed.");
				  		response.sendRedirect("ShopkeeperProd");
	                }
		        }
			con.close();
		} catch (SQLException ex) {
			String errorMessage = "Some error has occured";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("homePage.jsp").forward(request, response);
		}
	}

}
