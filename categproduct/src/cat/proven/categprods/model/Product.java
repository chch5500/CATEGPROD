
package cat.proven.categprods.model;

import java.util.Objects;

/**
 *
 * @author ProvenSoft
 */
public class Product {
    private long id;
    private String code;
    private String name;
    private int stock;
    private double price;
    private Category category;

    public Product(long id, String code, String name, int stock, double price, Category category) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.category = category;
    }

    public Product() {
    }

    public Product(long id) {
        this.id = id;
    }

    public Product(Product other) {
        this.id = other.id;
        this.code = other.code;
        this.name = other.name;
        this.stock = other.stock;
        this.price = other.price;
        this.category = other.category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.code);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        return Objects.equals(this.code, other.code);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Product{");
        sb.append("id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", stock=").append(stock);
        sb.append(", price=").append(price);
        sb.append(", categoryId=").append(category.getId());
        sb.append('}');
        return sb.toString();
    }
    
}
