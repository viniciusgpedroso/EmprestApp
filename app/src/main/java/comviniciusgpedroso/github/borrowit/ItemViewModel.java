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
    private LiveData<Float> paySum;
    private LiveData<Float> receiveSum;

    public ItemViewModel (Application application) {
        super(application);
        mRepository = new ItemRepository(application);
        mAllItems = mRepository.getAllItems();
        mAllPayItems = mRepository.getAllPayItems();
        mAllReceiveItems = mRepository.getAllReceiveItems();
        paySum = mRepository.getPaySum();
        receiveSum = mRepository.getReceiveSum();
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

    public LiveData<Float> getPaySum() {
        return paySum;
    }

    public LiveData<Float> getReceiveSum() {
        return receiveSum;
    }

    public void insert(Item item) {
        mRepository.insert(item);
    }

    public void deleteAll() {mRepository.deleteAll();}
}
