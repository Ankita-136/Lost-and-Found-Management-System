package com.lostfound.dao;

import com.lostfound.model.Item;
import com.lostfound.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    /**
     * Inserts a new reported item into the database.
     * @param item The Item object populated from the reporting form.
     * @return true if the database insertion is successful.
     */
    public boolean insertItem(Item item) {
        String query = "INSERT INTO items (item_name, description, category) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, item.getItemName());
            ps.setString(2, item.getDescription());
            ps.setString(3, item.getCategory());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Error while inserting item in ItemDAO:");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all items from the database sorted by the newest additions first.
     * @return A list containing all item records.
     */
    public List<Item> getAllItems() {
        List<Item> list = new ArrayList<>();
        String query = "SELECT id, item_name, description, category, date_added FROM items ORDER BY date_added DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setItemName(rs.getString("item_name"));
                item.setDescription(rs.getString("description"));
                item.setCategory(rs.getString("category"));

                // Safe mapping of the 'date_added' column to clear the build compilation error
                Timestamp timestamp = rs.getTimestamp("date_added");
                if (timestamp != null) {
                    item.setDateAdded(timestamp.toString());
                } else {
                    item.setDateAdded("");
                }

                list.add(item);
            }

        } catch (Exception e) {
            System.err.println("Error while retrieving all items in ItemDAO:");
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Deletes a specific item record based on its primary key identifier.
     * @param id The unique identifier of the item to be removed.
     * @return true if a row was successfully deleted.
     */
    public boolean deleteItem(int id) {
        String query = "DELETE FROM items WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Error while deleting item with ID " + id + " in ItemDAO:");
            e.printStackTrace();
            return false;
        }
    }
}