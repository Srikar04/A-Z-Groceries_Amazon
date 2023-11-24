

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


@WebServlet("/createXML")
public class CreateXML extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] productNames = request.getParameterValues("productNames");
        String[] quantities = request.getParameterValues("quantities");
        
        String shopkeeperName = (String) request.getSession().getAttribute("userName");
        
        Document document = generateXmlData(shopkeeperName, productNames, quantities);
        
        // Convert the XML document to a string
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        String xmlString = xmlOutputter.outputString(document);
        
        System.out.println("Generated XML:\n"+xmlString);
        
        try {
            String servletUrl = "http://localhost:8080/21MCME19_LA2/sendToAmazon";
            
            URL url = new URL(servletUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/xml");
            
            connection.setDoOutput(true);
            
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = xmlString.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }
            
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response1 = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response1.append(inputLine);
                    }
                    String servletResponse = response1.toString();
                    
                    System.out.println("Response from sendToAmazon: " + servletResponse);
                }
            } else {
                System.out.println("HTTP POST request failed with response code: " + responseCode);
            }
            
            
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect("prodAmazon.jsp");
	}
	
	 private Document generateXmlData(String shopkeeperName, String[] productNames, String[] quantities) {
		 Element amazonElement = new Element("Amazon", "http://www.w3.org/2001/XMLSchema");
	        Document document = new Document(amazonElement);
	        
	        Element shopkeeperElement = new Element("Shopkeeper");
	        amazonElement.addContent(shopkeeperElement);
	        
	        Element nameElement = new Element("Name");
	        nameElement.setText(shopkeeperName);
	        shopkeeperElement.addContent(nameElement);
	        
	        Element productListElement = new Element("ProductList");
	        shopkeeperElement.addContent(productListElement);
	        
	        for (int i = 0; i < productNames.length; i++) {
	            Element productElement = new Element("Product");
	            productListElement.addContent(productElement);

	            Element idElement = new Element("Id");
	            idElement.setText(productNames[i]);
	            productElement.addContent(idElement);

	            Element quantityElement = new Element("Quantity");
	            quantityElement.setText(quantities[i]);
	            productElement.addContent(quantityElement);
	        }
	        
	        return document;
	 }

}
