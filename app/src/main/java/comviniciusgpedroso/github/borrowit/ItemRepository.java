package comviniciusgpedroso.github.borrowit;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by endsieg on 04/01/19.
 */

public class ItemRepository {
    private ItemDao mItemDao;
    private LiveData<List<Item>> mAllItems;
    private LiveData<List<Item>> mAllPayItems;
    private LiveData<List<Item>> mAllReceiveItems;
    private LiveData<Float> paySum;
    private LiveData<Float> receiveSum;

    // TODO Check if AllItems is used anywhere
    ItemRepository(Application application) {
        ItemRoomDatabase db = ItemRoomDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAllItems();
        mAllPayItems = mItemDao.getAllPayItems();
        mAllReceiveItems = mItemDao.getAllReceiveItems();
        paySum = mItemDao.getPaySum();
        receiveSum = mItemDao.getReceiveSum();
    }

    LiveData<List<Item>> getAllItems() {
        return mAllItems;
    }

    LiveData<List<Item>> getAllPayItems() {
        return mAllPayItems;
    }

    LiveData<List<Item>> getAllReceiveItems() {
        return mAllReceiveItems;
    }

    LiveData<Float> getPaySum() {
        return paySum;
    }

    LiveData<Float> getReceiveSum() {
        return receiveSum;
    }

    public void insert (Item item) {
        new insertAsyncTask(mItemDao).execute(item);
    }

    public void deleteAll() {
        new deleteAllItemsAsyncTask(mItemDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDao mAsyncTaskDao;

        insertAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllItemsAsyncTask extends AsyncTask<Void, Void,
            Void> {
        private ItemDao mAsyncTaskDao;

        deleteAllItemsAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
