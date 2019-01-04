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

    public ItemViewModel (Application application) {
        super(application);
        mRepository = new ItemRepository(application);
        mAllItems = mRepository.getAllItems();
    }

    LiveData<List<Item>> getAllItems() {
        return mAllItems;
    }

    private void insert(Item item) {
        mRepository.insert(item);
    }
}
