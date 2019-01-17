package comviniciusgpedroso.github.borrowit;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by endsieg on 03/01/19.
 */

@Dao
public interface ItemDao {
    //TODO Add isArchived boolean and change queries to not include archived
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Item item);

    @Query("DELETE FROM item_table")
    void deleteAll();

    @Delete
    void deleteItem(Item item);

    @Query("SELECT * " +
            "FROM item_table " +
            "ORDER BY status, borrowDate DESC")
    LiveData<List<Item>> getAllItems();

    @Query("SELECT * " +
            "FROM item_table " +
            "WHERE isToReceive = 1 AND isArchived = 0" +
            "ORDER BY status, borrowDate DESC")
    LiveData<List<Item>> getAllReceiveItems();

    @Query("SELECT * " +
            "FROM item_table " +
            "WHERE isToReceive = 0 AND isArchived = 0" +
            "ORDER BY status, borrowDate DESC")
    LiveData<List<Item>> getAllPayItems();

    @Query("SELECT SUM(amount) " +
            "FROM item_table  " +
            "WHERE isToReceive = 1 AND isObject = 0 " +
            "AND status != 2 AND isArchived = 0")
    LiveData<Long> getReceiveSum();

    @Query("SELECT SUM(amount) " +
            "FROM item_table  " +
            "WHERE isToReceive = 0 AND isObject = 0" +
            "AND status != 2 AND isArchived = 0")
    LiveData<Long> getPaySum();

    @Query("SELECT SUM(isObject) " +
            "FROM item_table " +
            "WHERE isToReceive = 1 AND isObject = 1 " +
            "AND status !=2 AND isArchived = 0")
    LiveData<Integer> getReceiveObjectsSum();

    @Query("SELECT SUM(isObject) " +
            "FROM item_table " +
            "WHERE isToReceive = 0 AND isObject = 1 " +
            "AND status !=2 AND isArchived = 0")
    LiveData<Integer> getReturnObjectsSum();

    @Query("SELECT * " +
            "FROM item_table " +
            "WHERE isArchived = 1 " +
            "ORDER BY status, borrowDate DESC")
    LiveData<List<Item>> getAllArchivedItems();
}
