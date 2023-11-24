import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/UpdateNumber")
public class UpdateNumber extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Get the new pNumber value from the request parameters (you can modify this as needed)
        int newPNumber = Integer.parseInt(request.getParameter("newPNumber"));

        // Update the pNumber attribute in the session
        session.setAttribute("pNumber", Integer.toString(newPNumber));

        response.sendRedirect("fetchProducts"); // Redirect to the appropriate page
    }
}
