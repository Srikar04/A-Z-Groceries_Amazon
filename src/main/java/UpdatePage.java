import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/UpdatePage")
public class UpdatePage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession();

	        // Get the current page number from the session
	        Integer pageNumber = Integer.parseInt((String)request.getSession().getAttribute("pageNumber"));

	        String direction = request.getParameter("direction");
	        if ("next".equals(direction)) {
	            pageNumber++;
	        } else if ("prev".equals(direction)) {
	            pageNumber--;
	            if (pageNumber < 1) {
	                pageNumber = 1;
	            }
	        }
	        session.setAttribute("pageNumber", Integer.toString(pageNumber));
	        response.sendRedirect("fetchProducts");
	}
}
