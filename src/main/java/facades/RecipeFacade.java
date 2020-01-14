package facades;

import dtos.IngredientDTO;
import dtos.RecipeDTO;
import entities.Ingredient;
import entities.Recipe;
import entities.RenameMe;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class RecipeFacade {

    private static RecipeFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private RecipeFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static RecipeFacade getRecipeFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RecipeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getRecipeCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long recipeCount = (long) em.createQuery("SELECT COUNT(r) FROM Recipe r").getSingleResult();
            return recipeCount;
        } finally {
            em.close();
        }

    }

    public List<RecipeDTO> getRecipeAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Recipe> query = em.createQuery("SELECT r FROM Recipe r", Recipe.class);
        List<Recipe> recipes = query.getResultList();
        List<RecipeDTO> res = new ArrayList<>();
        recipes.forEach(r -> {
            System.out.println("No of Ingredients: " + r.getIngredients().size());
            System.out.println(r.getIngredients());
            List<Ingredient> ing = r.getIngredients();
            List<IngredientDTO> ingDTO = new ArrayList();

            ing.forEach(i -> {
                ingDTO.add(new IngredientDTO(i));
            });

            RecipeDTO rDTO = new RecipeDTO(r);
            rDTO.setIngredients(ingDTO);
            res.add(rDTO);
        });

        return res;
    }
    
    public Recipe addRecipe(String name, String prepTime, String directions) {
        Recipe recipe = new Recipe(name, prepTime, directions);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(recipe);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        
        return recipe;
    }
    
    public Recipe editRecipe(String id, String name,String prepTime, String directions) {
        EntityManager em = emf.createEntityManager();
        long find = Long.parseLong(id);
        Recipe recipe = em.find(Recipe.class, find);
        System.out.println("RECIPE: " + recipe);
        
        try {
            em.getTransaction().begin();
        recipe.setName(name);
        recipe.setPrepTime(prepTime);
        recipe.setDirections(directions);
        em.persist(recipe);
        em.getTransaction().commit();
        } finally {
            em.close();
        }
        
        return recipe;
    }

    public Recipe deleteRecipe(long id) {
        EntityManager em = emf.createEntityManager();
        Recipe recipe = em.find(Recipe.class, id);
        try {
            em.getTransaction().begin();
            em.remove(recipe);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return recipe;
    }

}
