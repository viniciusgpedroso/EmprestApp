package comviniciusgpedroso.github.borrowit;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by endsieg on 04/01/19.
 */

public class ItemRepository {
    private ItemDao mItemDao;
    private LiveData<List<Item>> mAllItems;
    private LiveData<Float> paySum;
    private LiveData<Float> receiveSum;

    ItemRepository(Application application) {
        ItemRoomDatabase db = ItemRoomDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAllItems();
        paySum = mItemDao.getPaySum();
        receiveSum = mItemDao.getReceiveSum();
    }

    LiveData<List<Item>> getAllItems() {
        return mAllItems;
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
}
