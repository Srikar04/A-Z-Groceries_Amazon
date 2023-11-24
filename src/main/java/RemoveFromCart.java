import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import it.CartItem;

public class RemoveFromCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int itemId = Integer.parseInt(request.getParameter("itemId"));

        // Get the cart from session
        @SuppressWarnings("unchecked")
		List<CartItem> cart = (List<CartItem>) request.getSession().getAttribute("cart");

        // Find and remove the item from the cart based on itemId
        for (CartItem item : cart) {
            if (item.getProductId() == itemId) {
                cart.remove(item);
                break;
            }
        }
        // Redirect back to the cart page
        response.sendRedirect("addToCart");
	}

}
