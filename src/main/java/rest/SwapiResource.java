package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.EMF_Creator;
import facades.SwapiFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("swapi")
public class SwapiResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/CA3",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    private static final SwapiFacade FACADE =  SwapiFacade.getSwapiFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"SwapiTestResource..\"}";
    }
    
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("results")
    public String getResults() {
        //List<String> results = FACADE.getResults();
        List<String> results = FACADE.getSwapiList();
        return results.toString();
    }
}
