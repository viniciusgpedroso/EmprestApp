package comviniciusgpedroso.github.borrowit;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


/**
 * Tab Fragment that shows the user an list of all the current payments to be
 * received.
 * A simple {@link Fragment} subclass.
 */
public class ToReceiveTabFragment extends Fragment {
    public static final int NEW_ITEM_ACTIVITY_REQUEST_CODE = 1;

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
        mItemViewModel.getAllReceiveItems().observe(this, new Observer<List<Item>>() {
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

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewItemActivity.class);
                startActivityForResult(intent, NEW_ITEM_ACTIVITY_REQUEST_CODE);

            }
        });

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
                int position = viewHolder.getAdapterPosition();
                Item myItem = adapter.getItemAtPosition(position);

                mItemViewModel.deleteItem(myItem);
                // Notify the adapter
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        return helper;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ITEM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Gets data back from the NewItemActivity Intent
            float valueAmount = data.getFloatExtra(NewItemActivity
                    .VALUE_REPLY, 0);
            boolean toReceive = data.getBooleanExtra(NewItemActivity
                    .TORECEIVE_REPLY, true);
            String contact = data.getStringExtra(NewItemActivity.CONTACT_REPLY);
            Long borrowDateTime = data.getLongExtra(NewItemActivity
                    .BORROW_DATE_REPLY, (new Date()).getTime());
            Long dueDateTime = data.getLongExtra(NewItemActivity
                    .DUE_DATE_REPLY, (new Date()).getTime());
            // If it is done use status == 2, otherwise let the Item class
            // deal with the results
            int alreadyPaidStatus = data.getBooleanExtra(NewItemActivity
                    .PAID_REPLY, false) ? 2 : -1;
            boolean isObject = false;

            // Creates a new item and adds to the view model
            Item item = new Item(UUID.randomUUID(), valueAmount, contact,
                    Converters.fromTimeStamp(borrowDateTime), Converters
                    .fromTimeStamp(dueDateTime), toReceive, false,
                    alreadyPaidStatus);
            mItemViewModel.insert(item);

        }
        // TODO Create failing result code
        else {
            Toast.makeText(
                    this.getContext(),
                    "Not saved",
                    Toast.LENGTH_LONG).show();
        }
    }
}
