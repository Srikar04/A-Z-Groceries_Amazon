import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

@WebServlet("/mergeStocks")
public class MergeStocks extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filePath = "/home/srikar/eclipse-workspace/21MCME19_LA2/src/main/webapp/xmlFiles//shopkeeper_data.xml";
		try {
	            StringBuilder xml = new StringBuilder();
	            
	            BufferedReader reader = new BufferedReader(new FileReader(filePath));
	            String line;

	            while ((line = reader.readLine()) != null) {
	                xml.append(line).append("\n");
	            }
	            
	            reader.close();

	            String xmlContent = xml.toString();

	            try {  
	            	JSONObject json = XML.toJSONObject(xmlContent);   
	        	        String jsonString = json.toString(4);  
	        	        
	        	        String directoryPath = "/home/srikar/eclipse-workspace/21MCME19_LA2/src/main/webapp/jsonFiles";
	        	        
	        	        File directory = new File(directoryPath);
	                    if (!directory.exists()) {
	                        directory.mkdir();
	                    }

	                    // Define the file path for the JSON file
	                    String jsonFilePath = directoryPath + File.separator + "shopkeeper_data.json";

	                    // Write the JSON content to the file
	                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFilePath))) {
	                        writer.write(jsonString);
	                    }
	                    
	                    request.setAttribute("message","JSON file has been generated");
	        	}catch (JSONException e) {  
	        		System.out.println(e.toString());
	        	}  
	            
	            request.getRequestDispatcher("/amazonHome.jsp").forward(request, response);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
