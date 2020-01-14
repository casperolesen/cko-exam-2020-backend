package facades;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class SwapiFacade implements Callable<String> {

    private static SwapiFacade instance;
    private static EntityManagerFactory emf;
    private int index;
    private static int[] swapiList = {1, 2, 3, 4, 5};
    
    @Override
    public String call() throws Exception {
        int i = swapiList[index];
        String result = getSwapiData(i);
        return result;

    }

    //Private Constructor to ensure Singleton
    private SwapiFacade() {
    }
    
    // Used to get a value from the hardcoded lists at top and
    public SwapiFacade(int index) {
        this.index = index;
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static SwapiFacade getSwapiFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SwapiFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public String getSwapiData(int id) throws MalformedURLException, IOException {
        URL url = new URL("https://swapi.co/api/people/" + id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        con.setRequestProperty("User-Agent", "server"); //remember if you are using SWAPI
        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = null;
        if (scan.hasNext()) {
            jsonStr = scan.nextLine();
        }
        scan.close();
        return jsonStr;
    }

    public List<String> getSwapiList() {
        //Get ExecutorService from Executors utility class, thread pool size is 10
        ExecutorService executor = Executors.newFixedThreadPool(10);
        //create a list to hold the Future object associated with Callable
        List<Future<String>> list = new ArrayList<Future<String>>();
        //Create MyCallable instance
        Callable<String> callable;

        List<String> results = new ArrayList();

        for (int i = 0; i < swapiList.length; i++) {
            callable = new SwapiFacade(i);
            //submit Callable tasks to be executed by thread pool
            Future<String> future = executor.submit(callable);
            //add Future to the list, we can get return value using Future
            list.add(future);
        }

        for (Future<String> fut : list) {
            try {
                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                results.add(fut.get());
                //System.out.println(new Date() + "::" + fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        //shut down the executor service now
        executor.shutdown();

        return results;
    }
}
