package cat.proven.categprods.model.persist;

import cat.proven.categprods.model.Category;
import cat.proven.categprods.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for product table
 *
 * @author ProvenSoft
 */
public class ProductDao {

    private final DbConnect dbConnect;

    public ProductDao() {
        this.dbConnect = new DbConnect();
    }

    private Product fromResultSet(ResultSet rs) throws SQLException {
        Product prod;
        long id = rs.getLong("id");
        String code = rs.getString("code");
        String name = rs.getString("name");
        int stock = rs.getInt("stock");
        double price = rs.getDouble("price");
        long categoryId = rs.getLong("category_id");
        prod = new Product(id, code, name, stock, price, new Category(categoryId));
        return prod;
    }

    public int insert(Product product) throws SQLException {
        int result = 0;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "insert into products values (null, ?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, product.getCode());
            st.setString(2, product.getName());
            st.setInt(3, product.getStock());
            st.setDouble(4, product.getPrice());
            st.setLong(5, product.getCategory().getId());
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int delete(Product p) {
        int result = 0;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "delete from products where code=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, p.getCode());
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public Product select(Product product) {
        Product prod = null;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from products where id=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, product.getId());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                prod = fromResultSet(rs);

            } else {
                prod = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return prod;
    }

    public int update(Product oldP, Product newP) {
        int result = 0;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = """
                           update products set 
                           name=?, stock=?, price=? 
                           where id=?
                           """;
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, newP.getName());
            st.setInt(2, newP.getStock());
            st.setDouble(3, newP.getPrice());
            st.setLong(4, oldP.getId());
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public Product selectWhereCode(String code) {
        Product prod = null;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from products where code=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, code);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                prod = fromResultSet(rs);

            } else {
                prod = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return prod;
    }

    public Product selectWhereName(String name) {
        Product prod = null;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from products where name=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                prod = fromResultSet(rs);

            } else {
                prod = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return prod;
    }

    public List<Product> selectWhereMinStock(int minStock) {
        List<Product> result = new ArrayList<>();
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from products where stock<?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, minStock);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product prod = fromResultSet(rs);
                if (prod != null) {
                    result.add(prod);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public List<Product> selectAll() {
        List<Product> result = new ArrayList<>();
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from products";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Product prod = fromResultSet(rs);
                if (prod != null) {
                    result.add(prod);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public List<Product> selectWhereCategory(Category category) {
        List<Product> result = new ArrayList<>();
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from products where category_id=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, category.getId());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product prod = fromResultSet(rs);
                if (prod != null) {
                    result.add(prod);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
