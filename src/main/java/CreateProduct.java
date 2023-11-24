import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet("/createProduct")
@MultipartConfig
public class CreateProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
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
	
	private String extractFileName(Part part) {
		//gets the fileName from the headers of part object
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
        	/*this line extracts the file name
        	 * by finding the index of the equal sign (=) in the part and adding 2 
        	 * to skip the equal sign and double-quote  
        	*/          
        	if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
	
	private void saveFile(Part part, String filePath) {
        try (InputStream input = part.getInputStream();
             OutputStream output = new FileOutputStream(filePath)) {
            int bytesRead;
            final byte[] buffer = new byte[1024];
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		// Get the product information from the request
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Part imagePart = request.getPart("image");
        
        String shopkeeper_name = (String)request.getSession().getAttribute("userName");
        
        String imageName = extractFileName(imagePart);
        
        String imagePath = getServletContext().getRealPath("/images/" + imageName);
        saveFile(imagePart, imagePath);
        
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
			String query = "INSERT INTO Product (Name, Description, Price, Quantity, Image_URL, shopkeeper_username) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
			  try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
				  	preparedStatement.setString(1, name);
		            preparedStatement.setString(2, description);
		            preparedStatement.setDouble(3, price);
		            preparedStatement.setInt(4, quantity);
		            preparedStatement.setString(5,imageName);
		            preparedStatement.setString(6, shopkeeper_name);
				  	int rowsInserted = preparedStatement.executeUpdate();
				  	if (rowsInserted > 0) {
				  		System.out.println("Product succesfully added.");
				  		response.sendRedirect("ShopkeeperProd");
		            } else {
		            	System.out.println("Some error has Occured.");
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
