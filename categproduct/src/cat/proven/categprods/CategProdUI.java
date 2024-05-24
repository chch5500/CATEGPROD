package cat.proven.categprods;

import cat.proven.categprods.model.Category;
import cat.proven.categprods.model.Product;
import cat.proven.categprods.model.StoreModel;
import java.util.List;
import java.util.Scanner;

/**
 * Store application: user interface layer (control logic and view)
 *
 * @author ProvenSoft
 */
public class CategProdUI {

    private final Menu mainMenu;
    private final Scanner uiReader;

    private boolean exit;

    private final StoreModel model;

    public CategProdUI(StoreModel model) {
        this.model = model;
        mainMenu = new MainMenu();
        uiReader = new Scanner(System.in);
        uiReader.useDelimiter("\n");
    }

    /**
     * application logic entry point
     */
    public void start() {
        exit = false;  //set exit flag to false
        //control loop
        do {
            //display menu and read user's choice
            mainMenu.show();
            String action = mainMenu.getSelectedOptionActionCommand();
            if (action == null) {
                action = "nooption";  //default option if any valid option selected
            }
            //process user's choice: one control method for each functionality
            switch (action) {
                case "exit":  //exit application
                    doExit();
                    break;
                case "category/all":  //list all categories
                    doListAllCategories();
                    break;
                case "category/code":  //list category given its code
                    doListCategoryByCode();
                    break;
                case "category/name":  //list category given its name
                    doListCategoriByName();
                    break;
                case "category/add":  //add a new category
                    doAddCategory();
                    break;
                case "category/modify":  //modify category
                    doModifyCategory();
                    break;
                case "category/remove":  //remove category
                    doRemoveCategory();
                    break;
                case "product/all":  //list all products
                    doListAllProducts();
                    break;
                case "product/code":  //list all products by code
                    doListProductsByCode();
                    break;
                case "product/name":  //list all products by name
                    doListProductsByName();
                    break;
                case "product/minstock":  //list all products by minStock
                    doListProductsByMinStock();
                    break;
                case "product/add":  //add a new product
                    doAddProduct();
                    break;
                case "product/modify":  //modify product
                    doModifyProduct();
                    break;
                case "product/category":  //list products given their category
                    doListProductsByCategory();
                    break;
                case "product/remove":  //Remove product
                    doRemoveProduct();
                    break;
                default:  //default option
                    doDefault();
                    break;
            }
        } while (!exit);
    }

    /**
     * Main method
     *
     * @param args argument for command line invocation (not necessari here)
     */
    public static void main(String[] args) {
        //instantiate model (data service)
        StoreModel model = new StoreModel();
        //instantiate presentation class (controller+view) and pass model to it
        CategProdUI ap = new CategProdUI(model);
        //start interacting with user
        ap.start();
    }

    /* ==== Control methods ==== */
    /**
     * asks for confirmation and exits application
     */
    public void doExit() {
        boolean confirm = doConfirm("Sure to exit? ");
        if (confirm) {
            exit = true;
        }
    }

    /**
     * process default action
     */
    public void doDefault() {
        //System.out.println("Unknown option!");
        System.out.println("Not implemented yet!");
    }

    /**
     * gets all categories and displays them
     */
    public void doListAllCategories() {
        List<Category> result = model.findAllCategories();
        if (result != null) {
            displayMultiple(result);
        } else {
            doAlert("No data has been obtained");
        }
    }

    /**
     * asks for a category code, gets category with given code and displays it
     */
    public void doListCategoryByCode() {
        String code = doInput("code: ");
        if (code != null) {
            Category result = model.findCategoryByCode(code);
            if (result != null) {
                displaySingle(result);
            } else {
                doAlert("Category not found");
            }
        }
    }

    private void doListCategoriByName() {
        String name = doInput("name: ");
        if (name != null) {
            Category result = model.findCategoryByName(name);
            if (result != null) {
                displaySingle(result);
            } else {
                doAlert("Category not found");
            }
        }
    }

    private void doModifyCategory() {
        int result = 0;
        String code = doInput("code:");
        if (code != null) {
            Category oldC = model.findCategoryByCode(code);
            if (oldC != null) {
                String newCode = doInput("New code: ");
                String newName = doInput("New name: ");
                Category newC = new Category(oldC.getId(), newCode, newName);
                result = model.modifyCategory(oldC, newC);
                if (result == 1) {
                    System.out.println("Category modified");
                } else {
                    System.out.println("Category not modified");
                }
            } else {
                doAlert("Category not found");
            }
        }
    }

    private void doRemoveCategory() {
        int result = 0;
        String code = doInput("code:");
        if (code != null) {
            Category cat = model.findCategoryByCode(code);
            if (cat != null) {
                result = model.dropCategory(cat);
                if (result == 1) {
                    System.out.println("Category removed");
                } else {
                    System.out.println("Category not removed");
                }
            }
        } else {
            doAlert("Category not found");
        }
    }

    /**
     * reads from user the data for a new category and adds it to database
     */
    public void doAddCategory() {
        Category cat = doInputCategory();
        if (cat != null) {
            int result = model.addCategory(cat);
            String message = (result == 1) ? "Successfully added" : "Not added";
            doAlert(message);
        } else {
            doAlert("Error validating data");
        }
    }

    /**
     * gets all products and displays them
     */
    public void doListAllProducts() {
        List<Product> result = model.findAllProducts();
        if (result != null) {
            displayMultiple(result);
        } else {
            doAlert("No data has been obtained");
        }
    }

    private void doListProductsByCode() {
        String code = doInput("code: ");
        Product result = model.findProductByCode(code);
        if (result != null) {
            displaySingle(result);
        } else {
            doAlert("No data has been obtained");
        }
    }

    private void doListProductsByName() {
        String name = doInput("name: ");
        Product result = model.findProductByName(name);
        if (result != null) {
            displaySingle(result);
        } else {
            doAlert("No data has been obtained");
        }
    }

    private void doListProductsByMinStock() {
        int minStock = doInputInt("Min Stock:  ");
        List<Product> result = model.findProductByMinStock(minStock);
        if (result != null) {
            displayMultiple(result);
        } else {
            System.out.println("Can't display");
        }

    }

    /**
     * reads from user the data for a new category and adds it to database
     */
    public void doAddProduct() {
        Product prod = doInputProduct();
        if (prod != null) {
            int result = model.addProduct(prod);
            String message = (result == 1) ? "Successfully added" : "Not added";
            doAlert(message);
        } else {
            doAlert("Error validating data");
        }
    }

    private void doModifyProduct() {
        int result = 0;
        String code = doInput("code:");
        if (code != null) {
            Product oldP = model.findProductByCode(code);
            if (oldP != null) {
                String newName = doInput("New name: ");
                int newStock = doInputInt("New Stock: ");
                double newPrice = doInputDouble("New Price: ");
                Product newP = new Product(oldP.getId(), oldP.getCode(), newName, newStock, newPrice, oldP.getCategory());
                result = model.modifyProduct(oldP, newP);
                if (result == 1) {
                    System.out.println("Product modified");
                } else {
                    System.out.println("Product not modified");
                }
            } else {
                doAlert("Product not found");
            }
        }
    }

    /**
     * asks for a category id, gets products with given category and displays
     * them
     */
    public void doListProductsByCategory() {
        String sid = doInput("Category id: ");
        try {
            long id = Long.parseLong(sid);
            Category cat = new Category(id);
            List<Product> result = model.findProductsByCategory(cat);
            if (result != null) {
                displayMultiple(result);
            } else {
                doAlert("Error getting data");
            }
        } catch (NumberFormatException ex) {
            doAlert("Invalid input");
        }

    }

    private void doRemoveProduct() {
        int result = 0;
        String code = doInput("code:");
        if (code != null) {
            Product p = model.findProductByCode(code);
            if (p != null) {
                result = model.dropProduct(p);
                if (result == 1) {
                    System.out.println("Product removed");
                } else {
                    System.out.println("Product not removed");
                }
            }
        } else {
            doAlert("Category not found");
        }
    }

    /* ==== View methods ==== */
    /**
     * displays a message to user
     *
     * @param message the message to display
     */
    public void doAlert(String message) {
        System.out.println(message);
    }

    /**
     * displays a message and gets ans answer from user
     *
     * @param message the message to display
     * @return user's answer
     */
    public String doInput(String message) {
        System.out.print(message);
        return uiReader.next();
    }

    public int doInputInt(String message) {
        System.out.print(message);
        return uiReader.nextInt();
    }

    public double doInputDouble(String message) {
        System.out.print(message);
        return uiReader.nextDouble();
    }

    /**
     * displays a message to user and asks for confirmation
     *
     * @param message the message to display
     * @return true is user confirms action, false otherwiser
     */
    public boolean doConfirm(String message) {
        final char yesAnswer = 'y';
        System.out.print(message);
        char answer = uiReader.next().toLowerCase().charAt(0);
        return (answer == yesAnswer);
    }

    /**
     * displays a single object
     *
     * @param <T> the type of the object
     * @param t the object to display
     */
    public <T> void displaySingle(T t) {
        System.out.println(t);
    }

    /**
     * displays a list of objects
     *
     * @param <T> the type of the object
     * @param data the list to display
     */
    public <T> void displayMultiple(List<T> data) {
        for (T t : data) {
            System.out.println(t);
        }
    }

    /**
     * reads from user data for a category
     *
     * @return category object or null in case of error
     */
    public Category doInputCategory() {
        Category c;
        try {
//            String sid = doInput("id: ");
//            long id = Long.parseLong(sid);
            long id = 0;  //id is autoincrement
            String code = doInput("code: ");
            String name = doInput("name: ");
            c = new Category(id, code, name);
        } catch (NumberFormatException ex) {
            c = null;
        }
        return c;
    }

    /**
     * reads from user data for a category
     *
     * @return category object or null in case of error
     */
    public Product doInputProduct() {
        Product p;
        try {
            //get a number formatter for our locale
//            String sid = doInput("id: ");
//            long id = Long.parseLong(sid);
            long id = 0;  //id is autoincrement
            String code = doInput("code: ");
            String name = doInput("name: ");
            String sstock = doInput("stock: ");
            int stock = Integer.parseInt(sstock);
            String sprice = doInput("price: ");
            double price = Double.parseDouble(sprice);
            String scatId = doInput("category id: ");
            long catId = Long.parseLong(scatId);
            Category cat = new Category(catId);
            p = new Product(id, code, name, stock, price, cat);
        } catch (NumberFormatException ex) {
            p = null;
        }
        return p;
    }
}
