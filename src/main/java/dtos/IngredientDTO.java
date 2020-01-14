package dtos;

import entities.Ingredient;
import entities.Item;

/**
 *
 * @author cko
 */
public class IngredientDTO {
    private Long id;
    private int amount;
    private Long item_id;
    private int stock;

    public IngredientDTO(Ingredient i) {
        this.id = i.getId();
        this.amount = i.getAmount();
        this.item_id = i.getItem().getId();
        this.stock = i.getItem().getStorage().getAmount();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    
}
