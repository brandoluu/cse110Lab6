import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.*;


public class RequestHandler implements HttpHandler {
    private final Map<String, String> data;


    public RequestHandler(Map<String, String> data) {
        this.data = data;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Request Recieved";
        String method = exchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
              response = handleGet(exchange);
            } else if (method.equals("POST")) {
              response = handlePost(exchange);
            } else if (method.equals("PUT")) {
                response = handlePut(exchange);
            } else if (method.equals("DELETE")){
                response = handleDelete(exchange);
            } else {
              throw new Exception("Not Valid Request Method");
            }
        } catch (Exception e) {
            System.out.println("An erroneous request");
            response = e.toString();
            e.printStackTrace();
        }

        //Sending back response to the cliend
        exchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = exchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }

    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
          String value = query.substring(query.indexOf("=") + 1);
          String year = data.get(value); // Retrieve data from hashmap
          if (year != null) {
            response = year;
            System.out.println("Queried for " + value + " and found " + year);
          } else {
            response = "No data found for " + value;
          }
        }
        return response;
    }

      private String handlePost(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        String language = postData.substring(
          0,
          postData.indexOf(",")
        ), year = postData.substring(postData.indexOf(",") + 1);
     
     
        // Store data in hashmap
        data.put(language, year);
     
     
        String response = "Posted entry {" + language + ", " + year + "}";
        System.out.println(response);
        scanner.close();
     
     
        return response;
    }

    private String handlePut(HttpExchange httpExchange){
        String response;
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        String language = postData.substring(
          0,
          postData.indexOf(",")
        ), year = postData.substring(postData.indexOf(",") + 1);

        if (data.get(language) == null){
            data.put(language, year);
            response = "Updated entry {" + language + ", " + year + "}";
        }
        return response;
    }

    private String handleDelete(HttpExchange httpExchange){
        return "";
    }

}
