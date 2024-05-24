package cat.proven.categprods;
/** 
 * @author ProvenSoft
 */
public class MainMenu extends Menu {

    public MainMenu() {
        super("Category&Product Manager main menu");
        addOption(new Option("Exit","exit"));
        //options related to categories
        addOption(new Option("List all categories","category/all"));
        addOption(new Option("List category by code","category/code"));
        addOption(new Option("List categories like name","category/name"));
        addOption(new Option("Add a new category","category/add"));
        addOption(new Option("Modify a category","category/modify"));
        addOption(new Option("Remove a category","category/remove"));
        //options related to products
        addOption(new Option("List all products","product/all"));
        addOption(new Option("List product by code","product/code"));
        addOption(new Option("List products like name","product/name"));
        addOption(new Option("List products by min. stock","product/minstock"));
        addOption(new Option("Add a new product","product/add"));
        addOption(new Option("Modify a product","product/modify"));
        addOption(new Option("Remove a product","product/remove"));
        //options related to category-product relationship
        addOption(new Option("List products by category","product/category"));
    } 
}