package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import utils.EMF_Creator;
import facades.FacadeExample;
import facades.RecipeFacade;
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
@Path("recipe")
public class RecipeResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/cko_exam_2020",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final RecipeFacade FACADE = RecipeFacade.getRecipeFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello Recipe..\"}";
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRecipeCount() {
        long count = FACADE.getRecipeCount();
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRecipeAll() {
        return Response.ok(FACADE.getRecipeAll()).build();
    }

    @Path("add")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response addRecipe(String content) {
        JsonObject json = new JsonParser().parse(content).getAsJsonObject();
        System.out.println("ADD: " + json);
        String name = json.get("name").getAsString();
        String prepTime = json.get("prepTime").getAsString();
        String directions = json.get("directions").getAsString();

        try {
            return Response.ok(new Gson().toJson(FACADE.addRecipe(name, prepTime, directions))).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
    
    @Path("edit")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response editRecipe(String content) {
        JsonObject json = new JsonParser().parse(content).getAsJsonObject();
        System.out.println("EDIT: " + json);
        String id = json.get("id").getAsString();
        String name = json.get("name").getAsString();
        String prepTime = json.get("prepTime").getAsString();
        String directions = json.get("directions").getAsString();
          try {
            return Response.ok(new Gson().toJson(FACADE.editRecipe(id, name, prepTime, directions))).build();
        } catch (Exception e) {
              e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @Path("delete")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public Response deleteRecipe(String content) {
        JsonObject json = new JsonParser().parse(content).getAsJsonObject();
        String id = json.get("id").getAsString();
        return Response.ok(FACADE.deleteRecipe(Long.parseLong(id))).build();
    }

}
