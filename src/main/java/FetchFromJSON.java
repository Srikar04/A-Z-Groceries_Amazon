import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


class Product {
    private String name;
    private int quantity;

    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

	public void setQuantity(int i) {
		this.quantity = i;
	}
}


@WebServlet("/fetchFromJson")
public class FetchFromJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filePath = "/home/srikar/eclipse-workspace/21MCME19_LA2/src/main/webapp/jsonFiles/shopkeeper_data.json";
		try {
	            StringBuilder json = new StringBuilder();
	            
	            BufferedReader reader = new BufferedReader(new FileReader(filePath));
	            String line;

	            while ((line = reader.readLine()) != null) {
	                json.append(line).append("\n");
	            }
	            
	            reader.close();

	            String jsonContent = json.toString();
	            
	            List<Product> allProducts = new ArrayList<>();
	            
	            JSONObject jsonObject = new JSONObject(jsonContent);
	            JSONObject amazonObject = jsonObject.getJSONObject("Amazon");
	            JSONObject shopkeeperList = amazonObject.getJSONObject("ShopkeeperList");
	            
	            JSONArray shopkeepers = shopkeeperList.getJSONArray("Shopkeeper");
	            
	            for (int i = 0; i < shopkeepers.length(); i++) {
	                JSONObject shopkeeper = shopkeepers.getJSONObject(i);
	                JSONObject productList = shopkeeper.getJSONObject("ProductList");
	                JSONArray products = productList.getJSONArray("Product");
	                
	                for (int j = 0; j < products.length(); j++) {
	                    JSONObject product = products.getJSONObject(j);
	                    String productName = product.getString("Id");
	                    int quantity = product.getInt("Quantity");

	                    // Check if the product already exists in the list
	                    boolean productExists = false;
	                    for (Product p : allProducts) {
	                        if (p.getName().equals(productName)) {
	                            p.setQuantity(p.getQuantity() + quantity);
	                            productExists = true;
	                            break;
	                        }
	                    }
	                    
	                 // If the product doesn't exist in the list, add it
	                    if (!productExists) {
	                        allProducts.add(new Product(productName, quantity));
	                    }
	                }
	            }
	            
	            StringBuilder table = new StringBuilder("<table border='1'><tr><th>Product</th><th>Total Quantity</th></tr>");

	            for (Product product : allProducts) {
	                table.append("<tr><td>").append(product.getName()).append("</td><td>").append(product.getQuantity()).append("</td></tr>");
	            }

	            table.append("</table>");
	            
	            request.setAttribute("allProducts", table.toString());
	            
	           response.getWriter().write(table.toString());
	           
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
