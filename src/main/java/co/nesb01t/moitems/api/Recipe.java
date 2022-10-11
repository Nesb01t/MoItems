package co.nesb01t.moitems.api;

import co.nesb01t.moitems.MoItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.KnowledgeBookMeta;

import java.util.Set;

public class Recipe {
    static NamespacedKey key;
    public static void clearRecipeList(Player p){
        Set<NamespacedKey> recipes = p.getDiscoveredRecipes();
        for(NamespacedKey key:recipes){
            p.undiscoverRecipe(key);
        }
    }

    public static void addPlayerTool(Player p){
        ItemStack tool = p.getInventory().getItemInMainHand();
        key = new NamespacedKey(Bukkit.getPluginManager().getPlugin("MoItems"), "test");
        ShapedRecipe recipe = new ShapedRecipe(key, tool);
        recipe = recipe.shape("ccc", "cxc", "ccc");
        recipe = recipe.setIngredient('c', Material.COAL).setIngredient('x', Material.IRON_INGOT);
        Bukkit.addRecipe(recipe);

        p.sendMessage(key.getNamespace()+"\n"+key.getKey());
    }
    public static void giveRecipe(Player p){
        if (p.discoverRecipe(key)) {
            p.sendMessage("find new recipe");
        } else {
            p.sendMessage("can't find");
        }
    }
    public static void giveRecipeBook(Player p){
        ItemStack book = new ItemStack(Material.KNOWLEDGE_BOOK);
        KnowledgeBookMeta meta = (KnowledgeBookMeta) book.getItemMeta();
        meta.addRecipe(key);
        book.setItemMeta(meta);
        p.getInventory().addItem(book);
    }
}
