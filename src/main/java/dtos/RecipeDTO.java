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
    private String prepTime;
    private String directions;

    public RecipeDTO(Recipe r) {
        this.id = r.getId();
        this.prepTime = r.getPrepTime();
        this.directions = r.getDirections();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

}
