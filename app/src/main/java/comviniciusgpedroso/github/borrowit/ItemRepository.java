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
    private LiveData<Integer> objectReceiveSum;
    private LiveData<Integer> objectReturnSum;

    // TODO Check if AllItems is used anywhere
    ItemRepository(Application application) {
        ItemRoomDatabase db = ItemRoomDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAllItems();
        mAllPayItems = mItemDao.getAllPayItems();
        mAllReceiveItems = mItemDao.getAllReceiveItems();
        paySum = mItemDao.getPaySum();
        receiveSum = mItemDao.getReceiveSum();
        objectReceiveSum = mItemDao.getReceiveObjectsSum();
        objectReturnSum = mItemDao.getReturnObjectsSum();
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

    public LiveData<Integer> getObjectReceiveSum() {
        return objectReceiveSum;
    }

    public LiveData<Integer> getObjectReturnSum() {
        return objectReturnSum;
    }

    public void insert (Item item) {
        new insertAsyncTask(mItemDao).execute(item);
    }

    public void deleteAll() {
        new deleteAllItemsAsyncTask(mItemDao).execute();
    }

    public void deleteItem(Item item) {
        new deleteItemAsyncTask(mItemDao).execute(item);
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

    private static class deleteItemAsyncTask extends AsyncTask<Item, Void,
            Void> {
        private ItemDao mAsyncTaskDao;

        deleteItemAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.deleteItem(params[0]);
            return null;
        }
    }
}
