package dtos;

import entities.Ingredient;
import entities.Menu;
import entities.Recipe;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cko
 */
public class RecipeDTO {

    private long id;
    private String name;
    private String prepTime;
    private String directions;
    private List<IngredientDTO> ingredients = new ArrayList();

    public RecipeDTO(Recipe r) {
        this.id = r.getId();
        this.name = r.getName();
        this.prepTime = r.getPrepTime();
        this.directions = r.getDirections();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDTO> ing) {
        this.ingredients = ing;
    }
    
    public void addIngredients(List<Ingredient> ing) {
        ing.forEach(i -> {
            System.out.println("Ingredient id: " + i.getId());
            this.ingredients.add(new IngredientDTO(i));
        });
    }
    
    

}
