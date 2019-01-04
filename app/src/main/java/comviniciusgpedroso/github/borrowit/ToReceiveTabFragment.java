package comviniciusgpedroso.github.borrowit;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Tab Fragment that shows the user an list of all the current payments to be
 * received.
 * A simple {@link Fragment} subclass.
 */
public class ToReceiveTabFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ItemCardAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context mContext = getContext();
    private ItemViewModel mItemViewModel;

    public ToReceiveTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_receive_tab, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview_to_receive);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        adapter = new ItemCardAdapter(this.getContext());

        mRecyclerView.setAdapter(adapter);

        // Get a new or existing ViewModel from the ViewModelProvider
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        // Add an observer on the LiveData returned by getAllItems.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mItemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                // Update the cached copy of the words in the adapter
                adapter.setItems(items);
            }
        });

        // Helper class for creating swipe to dismiss
        ItemTouchHelper helper = createsSwipeClickHelper();

        // Attach the helper to the RecyclerView
        helper.attachToRecyclerView(mRecyclerView);

        return view;
    }

    /**
     * Helper class for creating swipe to dismiss. Drag and drop
     * functionality ignored.
     * @return helper for swiping cards
     */
    private ItemTouchHelper createsSwipeClickHelper() {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            /**
             * Defines the drag and drop functionality.
             *
             * @param recyclerView The RecyclerView that contains the list items
             * @param viewHolder The SportsViewHolder that is being moved
             * @param target The SportsViewHolder that you are switching the
             *               original one with.
             * @return true if the item was moved, false otherwise
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            /**
             * Defines the swipe to dismiss functionality.
             *
             * @param viewHolder The viewholder being swiped.
             * @param direction The direction it is swiped in.
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Remove the item from the dataset
                // TODO REMOVE FROM DATABASE
                // Notify the adapter
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        return helper;
    }
}
