package comviniciusgpedroso.github.borrowit;


import android.content.Context;
import android.os.Bundle;
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
    private ArrayList<Item> itemList;
    private Context mContext = getContext();

    public ToReceiveTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final FragmentActivity c = getActivity();
        itemList = new ArrayList<>();

        // Fills the itemList with placeholder data
        createsPlaceHolderData();


        View view = inflater.inflate(R.layout.fragment_to_receive_tab, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview_to_receive);

        mLayoutManager = new LinearLayoutManager(c);
        adapter = new ItemCardAdapter(this.getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // Inflate the layout for this fragment

        // Helper class for creating swipe to dismiss
        ItemTouchHelper helper = createsSwipeClickHelper();

        // Attach the helper to the RecyclerView
        helper.attachToRecyclerView(mRecyclerView);
        return view;
    }

    /**
     * Creates placeholder data to fill and test the recyclerview
     */
    private void createsPlaceHolderData() {
        Date firstDate = new Date();
        Date secondDate = new Date();
        itemList.add(new Item(UUID.randomUUID(), 15f, "Joao Silva", firstDate, secondDate, true,
                false, R.drawable
                .ic_checked));
        itemList.add(new Item(UUID.randomUUID(), 5f, "Maria Souza", firstDate, secondDate, true,
                false, R.drawable
                .ic_after_due_date));
        itemList.add(new Item(UUID.randomUUID(), 55f, "Jose Oliveira", firstDate, secondDate,
                true, false, R
                .drawable
                .ic_before_due_date));
        itemList.add(new Item(UUID.randomUUID(), 15f, "Pedro Agua", firstDate, secondDate, true,
                false, R.drawable
                .ic_checked));
        itemList.add(new Item(UUID.randomUUID(), 5.20f, "Juliana Terra", firstDate, secondDate,
                true, false, R
                .drawable
                .ic_after_due_date));
        itemList.add(new Item(UUID.randomUUID(),0.5f, "Guilherme Sal", firstDate, secondDate,
                true, false, R
                .drawable
                .ic_before_due_date));
        itemList.add(new Item(UUID.randomUUID(), 1535.25f, "Gabriel Vapor", firstDate, secondDate,
                true, false, R
                .drawable
                .ic_checked));
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
                itemList.remove(viewHolder.getAdapterPosition());
                // Notify the adapter
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        return helper;
    }
}
