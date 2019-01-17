package comviniciusgpedroso.github.borrowit;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * Created by endsieg on 04/01/19.
 */

public class ItemViewModel extends AndroidViewModel {
    private ItemRepository mRepository;
    private LiveData<List<Item>> mAllItems;
    private LiveData<List<Item>> mAllPayItems;
    private LiveData<List<Item>> mAllReceiveItems;
    private LiveData<List<Item>> mAllArchivedItems;
    private LiveData<Long> paySum;
    private LiveData<Long> receiveSum;
    private LiveData<Integer> objectReceiveSum;
    private LiveData<Integer> objectReturnSum;

    public ItemViewModel (Application application) {
        super(application);
        mRepository = new ItemRepository(application);
        mAllItems = mRepository.getAllItems();
        mAllPayItems = mRepository.getAllPayItems();
        mAllReceiveItems = mRepository.getAllReceiveItems();
        mAllArchivedItems = mRepository.getAllArchivedItems();
        paySum = mRepository.getPaySum();
        receiveSum = mRepository.getReceiveSum();
        objectReceiveSum = mRepository.getObjectReceiveSum();
        objectReturnSum = mRepository.getObjectReturnSum();
    }
    
    public LiveData<List<Item>> getAllItems() {
        return mAllItems;
    }

    LiveData<List<Item>> getAllPayItems() {
        return mAllPayItems;
    }

    LiveData<List<Item>> getAllReceiveItems() {
        return mAllReceiveItems;
    }

    public LiveData<List<Item>> getAllArchivedItems() {
        return mAllArchivedItems;
    }

    public LiveData<Long> getPaySum() {
        return paySum;
    }

    public LiveData<Long> getReceiveSum() {
        return receiveSum;
    }

    public LiveData<Integer> getObjectReceiveSum() {
        return objectReceiveSum;
    }

    public LiveData<Integer> getObjectReturnSum() {
        return objectReturnSum;
    }

    public void insert(Item item) {
        mRepository.insert(item);
    }

    public void deleteAll() {mRepository.deleteAll();}

    public void deleteItem(Item item) {
        mRepository.deleteItem(item);
    }
}
