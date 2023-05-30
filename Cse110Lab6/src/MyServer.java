import com.sun.net.httpserver.*;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;




public class MyServer {


 // initialize server port and hostname
 private static final int SERVER_PORT = 8100;
 private static final String SERVER_HOSTNAME = "127.0.0.1"; // my local machine

    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)
        Executors.newFixedThreadPool(10); //Threadpool with 10 threads 

        Map<String,String> data = new HashMap<>();
        HttpServer Server = HttpServer.create(new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT),0);


        HttpContext context = Server.createContext("/", new RequestHandler(data));
        
        Server.setExecutor(threadPoolExecutor);
        Server.start();

        System.out.println("Server Started on port " + SERVER_PORT);


    }


}
