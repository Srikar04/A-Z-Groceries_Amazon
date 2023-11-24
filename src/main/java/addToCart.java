import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.CartItem;

@WebServlet("/addToCart")
public class addToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<CartItem> cart = (List<CartItem>)session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }
        double totalCost = 0.0;
        for (CartItem item : cart) {
            totalCost += item.getCost();
        }
        session.setAttribute("totalCost", totalCost);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String productName = request.getParameter("productName");
	    int productId = Integer.parseInt(request.getParameter("productId"));
	    if(request.getParameter("quantity") == "") {
	    	response.sendRedirect("fetchProducts");
	    	return;
	    }
	    int quantity = Integer.parseInt(request.getParameter("quantity"));
	    double price = Double.parseDouble(request.getParameter("price"));
	    String imageURL = request.getParameter("image");

	    HttpSession session = request.getSession();

	    // Synchronize access to the cart to make it thread-safe
	    synchronized (session) {
	        @SuppressWarnings("unchecked")
	        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
	        if (cart == null) {
	            cart = new ArrayList<CartItem>();
	        }
	        // Check if the product is already in the cart
	        boolean productExists = false;
	        for (CartItem item : cart) {
	            if (item.getProductId() == productId) {
	                item.setQuantity(item.getQuantity() + quantity);
	                productExists = true;
	                break;
	            }
	        }
	        if (!productExists) {
	            cart.add(new CartItem(productId, productName, quantity, price, imageURL));
	        }
	        // Update the user's cart in the session
	        session.setAttribute("cart", cart);
	    }
	    // Redirect back to the home page or cart page
	    response.sendRedirect("fetchProducts");
	}

}
