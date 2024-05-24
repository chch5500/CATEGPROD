package cat.proven.categprods.model.persist;

import cat.proven.categprods.model.Category;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for category table
 *
 * @author ProvenSoft
 */
public class CategoryDao {

    private final DbConnect dbConnect;

    public CategoryDao() {
        this.dbConnect = new DbConnect();
    }

    private Category fromResultSet(ResultSet rs) throws SQLException {
        Category cat;
        long id = rs.getLong("id");
        String code = rs.getString("code");
        String name = rs.getString("name");
        cat = new Category(id, code, name);
        return cat;
    }

    public int insert(Category category) {
        int result = 0;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "insert into categories values (null, ?, ?)";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, category.getCode());
            st.setString(2, category.getName());
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int update(Category currentCategory, Category updatedCategory) {
        int result = 0;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = """
                           update categories set 
                           code=?, name=?  
                           where id=?
                           """;
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, updatedCategory.getCode());
            st.setString(2, updatedCategory.getName());
            st.setLong(3, currentCategory.getId());
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int delete(Category deleteCategory) {
        int result = 0;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query2 = "delete from products where category_id =?";
            PreparedStatement st2 = conn.prepareStatement(query2);
            st2.setLong(1, deleteCategory.getId());
            st2.executeUpdate();
            String query = "delete from categories where id=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, deleteCategory.getId());
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public Category select(Category category) {
        Category cat = null;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from categories where id=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, category.getId());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                cat = fromResultSet(rs);

            } else {
                cat = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return cat;
    }

    public Category selectWhereCode(String code) {
        Category cat = null;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from categories where code=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, code);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                cat = fromResultSet(rs);

            } else {
                cat = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return cat;
    }

    public Category selectWhereName(String name) {
        Category cat = null;
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from categories where name=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                cat = fromResultSet(rs);

            } else {
                cat = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return cat;
    }

    public List<Category> selectAll() {
        List<Category> result = new ArrayList<>();
        //get a connection and perform query
        try ( Connection conn = dbConnect.getConnection()) {
            String query = "select * from categories";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Category cat = fromResultSet(rs);
                if (cat != null) {
                    result.add(cat);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
