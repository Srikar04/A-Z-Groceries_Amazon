
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.input.SAXBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.io.StringReader;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/sendToAmazon")
public class SendToAmazon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
	            StringBuilder xmlData = new StringBuilder();
	            BufferedReader reader = request.getReader();
	            String line;
	            while ((line = reader.readLine()) != null) {
	                xmlData.append(line);
	            }
		
				// Modify the existing code to add/update the received products in shopkeeper_data.xml
		        String directoryPath = "/home/srikar/eclipse-workspace/21MCME19_LA2/src/main/webapp/xmlFiles";
		        File directory = new File(directoryPath);
		        if (!directory.exists()) {
		            directory.mkdirs();
		        }
		
		        String filePath = directoryPath + File.separator + "shopkeeper_data.xml";
		        Document document;
		        if (new File(filePath).exists()) {
		            document = readXmlDocument(filePath);
		        } else {
		            document = createNewXmlDocument(filePath);
		        }
		        
		        addShopkeeperProductsFromXml(document, xmlData.toString());

	            saveXmlDocument(document, filePath);

	            response.setStatus(HttpServletResponse.SC_OK);
	        } catch (IOException e) {
	            e.printStackTrace();
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        }
	}
	
	public Document readXmlDocument(String filePath) {
	    Document document = null;
	    
	    System.out.println("xml doc exists");

	    try {
	        SAXBuilder saxBuilder = new SAXBuilder();
	        document = saxBuilder.build(filePath);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return document;
	}

    
    public Document createNewXmlDocument(String filePath) {
        Element amazonElement = new Element("Amazon");
        Document document = new Document(amazonElement);
        
        Element shopkeeperListElement = new Element("ShopkeeperList");
        amazonElement.addContent(shopkeeperListElement);
        
        try {
            XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
            FileWriter writer = new FileWriter(filePath);
            xmlOutput.output(document, writer);
            writer.close();
            System.out.println("XML document created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return document;
    }
    
    public void addShopkeeperProductsFromXml(Document document, String xmlData) {
        try {
        	SAXBuilder saxBuilder = new SAXBuilder();
            Document receivedDoc = saxBuilder.build(new StringReader(xmlData));

            Element amazonElement = document.getRootElement();
            Element shopkeeperListElement = amazonElement.getChild("ShopkeeperList");
            
            String senderName = receivedDoc.getRootElement().getChild("Shopkeeper").getChild("Name").getText();
            
	  		
	  		// Iterate through the received products in the XML data
            List<Element> productElements = receivedDoc.getRootElement().getChild("Shopkeeper").getChild("ProductList").getChildren("Product");
            for (Element productElement : productElements) {
                String productId = productElement.getChildText("Id");
                int quantity = Integer.parseInt(productElement.getChildText("Quantity"));
                
                // Find the shopkeeper and product in the existing XML data
                Element existingShopkeeper = null;
                List<Element> existingShopkeepers = shopkeeperListElement.getChildren("Shopkeeper");
                for (Element shopkeeper : existingShopkeepers) {
                    Element nameElement = shopkeeper.getChild("Name");
                    if (nameElement != null && senderName.equals(nameElement.getText())) {
                        existingShopkeeper = shopkeeper;
                        break;
                    }
                }
                
                // If the shopkeeper doesn't exist, create a new Shopkeeper element
                Element shopkeeperElement;
                Element productListElement = null;
                if (existingShopkeeper == null) {
                    shopkeeperElement = new Element("Shopkeeper");
                    Element shopkeeperNameElement = new Element("Name").setText(senderName);
                    shopkeeperElement.addContent(shopkeeperNameElement);
                    shopkeeperListElement.addContent(shopkeeperElement);
                    productListElement = new Element("ProductList");
                    shopkeeperElement.addContent(productListElement);
                } else {
                    shopkeeperElement = existingShopkeeper; // Use the existing shopkeeper element
                    productListElement = shopkeeperElement.getChild("ProductList");
                }
            
                boolean productExists = false;
                List<Element> existingProducts = productListElement.getChildren("Product");
                for (Element existingProduct : existingProducts) {
                    Element productIdElement = existingProduct.getChild("Id");
                    if (productIdElement != null && productId.equals(productIdElement.getText())) {
                        Element quantityElement = existingProduct.getChild("Quantity");
                        if (quantityElement != null) {
                            // Update the quantity by adding old quantity + new quantity
                            int oldQuantity = Integer.parseInt(quantityElement.getText());
                            int newQuantity = quantity + oldQuantity;
                            quantityElement.setText(String.valueOf(newQuantity));
                            productExists = true;
                            break;
                        }
                    }
                }

                if (!productExists) {
                    // Create a new Product element
                    Element productElement1 = new Element("Product");
                    Element productIdElement = new Element("Id").setText(productId);
                    Element quantityElement = new Element("Quantity").setText(Integer.toString(quantity));

                    productElement1.addContent(productIdElement);
                    productElement1.addContent(quantityElement);

                    // Add the productElement to the productListElement
                    productListElement.addContent(productElement1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveXmlDocument(Document document, String filePath) {
        if (document == null || filePath == null || filePath.isEmpty()) {
            return;
        }
        
        XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
        try {
            FileWriter writer = new FileWriter(filePath);
            xmlOutput.output(document, writer);
            writer.close();
            System.out.println("XML document saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
