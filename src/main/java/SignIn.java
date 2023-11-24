import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/SignIn")
public class SignIn extends HttpServlet {
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

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("Please go back!");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");

		String userType = request.getParameter("usertype");
		String userName = request.getParameter("username");
		String password = request.getParameter("password");

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
			String tableName = "";
			if (userType.equals("Shopkeeper")) {
			    tableName = "Shopkeeper";
			} else if (userType.equals("Customer")) {
			    tableName = "Customer";
			} else if (userType.equals("Amazon")) {
			    tableName = "Amazon";
			}
			String query = "SELECT * FROM " + tableName + " WHERE username = ?";
			try (PreparedStatement preparedStatement = con.prepareStatement(query);) {
				preparedStatement.setString(1, userName);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (!resultSet.next()) {
					String errorMessage = "No such user exists.";
					request.setAttribute("errorMessage", errorMessage);
					request.getRequestDispatcher("SignIn.jsp").forward(request, response);
				} else {
					String dbPassword = resultSet.getString("password");
					if (dbPassword.equals(password)) {
						if (userType.equals("Customer")) {
							//create a session
							HttpSession session = request.getSession();
								session.setAttribute("userName", userName);
								session.setAttribute("userType", "Customer");								session.setAttribute("pageNumber", "1");
								session.setAttribute("pNumber", "10");
							session.setMaxInactiveInterval(30 * 60); // 30 minutes in seconds
							
							// Redirect to a successful login page or perform further actions
							response.sendRedirect("fetchProducts");
						} else if(userType.equals("Shopkeeper")) {
							HttpSession session = request.getSession();
								session.setAttribute("userName", userName);
								session.setAttribute("userType", "Shopkeeper");
							response.sendRedirect("ShopkeeperProd");
						}else {
							HttpSession session = request.getSession();
								session.setAttribute("userName", userName);
								session.setAttribute("userType", "Amazon");
							response.sendRedirect("amazonHome.jsp");
						}
					} else {
						// Password mismatch, send an error message
						String errorMessage = "Password mismatch. Please try again.";
						request.setAttribute("errorMessage", errorMessage);
						request.getRequestDispatcher("SignIn.jsp").forward(request, response);
					}
				}
			}
			con.close();
		} catch (SQLException ex) {
			String errorMessage = "Incorrect Password.Try Again";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("SignIn.jsp").forward(request, response);
		}
	}
}
