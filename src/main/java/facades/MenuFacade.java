package facades;

import dtos.RecipeDTO;
import entities.Menu;
import entities.Recipe;
import entities.RenameMe;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MenuFacade {

    private static MenuFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private MenuFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MenuFacade getMenuFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MenuFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    //TODO Remove/Change this before use
    public long getMenuCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long menuCount = (long)em.createQuery("SELECT COUNT(m) FROM Menu m").getSingleResult();
            return menuCount;
        }finally{  
            em.close();
        }
        
    }
    
    public Menu addMenu(List<RecipeDTO> recipes) {
        EntityManager em = emf.createEntityManager();
        Menu menu = new Menu(7, 1990);
        recipes.forEach(r -> {
            Recipe recipe = new Recipe(r.getId(), r.getName(), r.getPrepTime(), r.getDirections());
            menu.addRecipe(recipe);
        });
        
          try {
            em.getTransaction().begin();
            em.persist(menu);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        
        return menu;
        
    }

}
