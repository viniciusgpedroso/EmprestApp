package comviniciusgpedroso.github.borrowit;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ArchivedItemsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context mContext;
    private ItemViewModel mItemViewModel; // UI interaction with db
    private ItemCardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archived_items);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_archived_items);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        adapter = new ItemCardAdapter(this.getBaseContext());
        mRecyclerView.setAdapter(adapter);

        // Get a new or existing ViewModel from the ViewModelProvider
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        // Add an observer on the LiveData returned by getArchivedItems.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mItemViewModel.getAllArchivedItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                // Update the cached copy of the words in the adapter
                adapter.setItems(items);
            }
        });
    }

}
