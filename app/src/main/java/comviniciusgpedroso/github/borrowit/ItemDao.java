package comviniciusgpedroso.github.borrowit;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by endsieg on 03/01/19.
 */

@Dao
public interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Item item);

    @Query("DELETE FROM item_table")
    void deleteAll();

    @Query("SELECT * from item_table ORDER BY borrowDate ASC")
    LiveData<List<Item>> getAllItems();

    @Query("SELECT * from item_table WHERE isToReceive = true ORDER BY borrowDate ASC")
    LiveData<List<Item>> getAllReceiveItems();

    @Query("SELECT * from item_table WHERE isToReceive = false ORDER BY borrowDate ASC")
    LiveData<List<Item>> getAllPayItems();

    @Query("SELECT SUM(amount) " +
            "FROM item_table  " +
            "WHERE isToReceive = false AND isObject = false")
    Float getPaySum();

    @Query("SELECT SUM(amount) " +
            "FROM item_table  " +
            "WHERE isToReceive = true AND isObject = false" +
            "ORDER BY borrowDate ASC")
    Float getReceiveSum();
}
