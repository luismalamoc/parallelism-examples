import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static Integer sendRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        System.out.println("["+con.getResponseCode()+"] "+urlString);
        con.disconnect();
        return con.getResponseCode();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length < 2){
            throw new RuntimeException("\"Usage: java main <url1> <url2> .. <urln>\"");
        }
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        List<Callable<Integer>> callableTasks = new ArrayList<>();
        for (String url: args) {
            Callable<Integer> callableTask = () -> sendRequest("https://"+url);
            callableTasks.add(callableTask);
        }
        threadPool.invokeAll(callableTasks);
        threadPool.shutdown();
    }
}