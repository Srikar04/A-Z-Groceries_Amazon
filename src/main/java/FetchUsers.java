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


@WebServlet("/fetchUsers")
public class FetchUsers extends HttpServlet {
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
		
		Properties props = getConnectionData();
		String url = props.getProperty("db.url");
		String user = props.getProperty("db.user");
		String passwd = props.getProperty("db.passwd");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		List<String> users = new ArrayList<String>();
		
		try (Connection con = DriverManager.getConnection(url, user, passwd)) {
			String query = "SELECT * FROM Customer";
			
			  try (PreparedStatement stmt = con.prepareStatement(query)) {
		            ResultSet rs = stmt.executeQuery();
		            while (rs.next()) {
		            	users.add(rs.getString("username")); 
		                
		                System.out.println(rs.getString("username"));
		            }
		        }
			  
			    request.getSession().setAttribute("users", users);
			    request.getRequestDispatcher("userNameReport.jsp").forward(request, response);
			
			con.close();
		} catch (SQLException ex) {
			String errorMessage = "Some error has occured";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("ShopHomepage.jsp").forward(request, response);
		}
	}

}
