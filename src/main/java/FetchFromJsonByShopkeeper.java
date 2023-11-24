

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


@WebServlet("/fetchByShopkeeper")
public class FetchFromJsonByShopkeeper extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("shopkeeper");
		
		String filePath = "/home/srikar/eclipse-workspace/21MCME19_LA2/src/main/webapp/jsonFiles/shopkeeper_data.json";
		
		try {
			StringBuilder json = new StringBuilder();
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line;
			
			while((line = reader.readLine()) != null) {
				json.append(line).append("\n");
			}
		
			reader.close();
			
			String jsonContent = json.toString();
			
			List<Product> allProducts = new ArrayList<>();
			
			boolean empty = true;
			
			
			JSONObject jsonObject = new JSONObject(jsonContent);
			JSONObject amazonObject = jsonObject.getJSONObject("Amazon");
			JSONObject shopkeeperList = amazonObject.getJSONObject("ShopkeeperList");
	            
	        JSONArray shopkeepers = shopkeeperList.getJSONArray("Shopkeeper");
	        
	        for (int i = 0; i < shopkeepers.length(); i++) {
                JSONObject shopkeeper = shopkeepers.getJSONObject(i);
                String shopkeeperName = shopkeeper.getString("Name");
                if(shopkeeperName.equals(name)) {
                	 JSONObject productList = shopkeeper.getJSONObject("ProductList");
                     JSONArray products = productList.getJSONArray("Product");
                	for (int j = 0; j < products.length(); j++) {
                		empty = false;
                        JSONObject product = products.getJSONObject(j);
                        String productName = product.getString("Id");
                        int quantity = product.getInt("Quantity");
                        allProducts.add(new Product(productName, quantity));
                    }
                }
            }
	        
	        StringBuilder table = new StringBuilder("<br><h2>").append(name).append("</h2><table border='1'><tr><th>Product</th><th>Quantity</th></tr>");

            for (Product product : allProducts) {
                table.append("<tr><td>").append(product.getName()).append("</td><td>").append(product.getQuantity()).append("</td></tr>");
            }

            table.append("</table>");
            
            if(empty) {
            	table.setLength(0);
            	table.append("<h2>No products sent by this Shopkeeper</h2>");
            }
            
	        response.getWriter().write(table.toString());
            
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
