package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.RecipeDTO;
import errorhandling.NotFoundException;
import utils.EMF_Creator;
import facades.FacadeExample;
import facades.MenuFacade;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("menu")
public class MenuResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/cko_exam_2020",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final MenuFacade FACADE = MenuFacade.getMenuFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World..\"}";
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMenuCount() {
        long count = FACADE.getMenuCount();
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }
    
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMenuAll() {
        return Response.ok(FACADE.getMenuAll()).build();
    }

    @Path("add")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response addMenu(String content) {
        JsonArray json = new JsonParser().parse(content).getAsJsonArray();
        RecipeDTO recipes[] = GSON.fromJson(json, RecipeDTO[].class);
        List<RecipeDTO> menu = new ArrayList();
        System.out.println(Arrays.toString(recipes));

        for (RecipeDTO r : recipes) {
            menu.add(r);
        }

        try {
            return Response.ok(new Gson().toJson(FACADE.addMenu(menu))).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

}
