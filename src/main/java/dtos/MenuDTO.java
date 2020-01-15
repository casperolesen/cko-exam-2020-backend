package dtos;

import entities.Menu;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cko
 */
public class MenuDTO {
    
    private long id;
    private int weekNo;
    private int yearNo;
    private List<RecipeDTO> recipes = new ArrayList();

    public MenuDTO(Menu menu) {
        this.id = menu.getId();
        this.weekNo = menu.getWeekNo();
        this.yearNo = menu.getYear();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getWeekNo() {
        return weekNo;
    }

    public void setWeekNo(int weekNo) {
        this.weekNo = weekNo;
    }

    public int getYearNo() {
        return yearNo;
    }

    public void setYearNo(int yearNo) {
        this.yearNo = yearNo;
    }

    public List<RecipeDTO> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeDTO> recipes) {
        this.recipes = recipes;
    }
    
    
    
}
