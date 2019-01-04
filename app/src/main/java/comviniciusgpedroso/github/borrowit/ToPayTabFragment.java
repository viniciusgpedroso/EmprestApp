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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Tab Fragment that shows the user an list of all the current and past
 * payments made.
 * A simple {@link Fragment} subclass.
 */
public class ToPayTabFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context mContext;
    private ItemViewModel mItemViewModel; // UI interaction with db

    public ToPayTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);



        /*ArrayList<Item> itemList = new ArrayList<>();
        Date firstDate = new Date();
        Date secondDate = new Date();
        itemList.add(new Item(15f, "Joao Silva", firstDate, secondDate, true, false, R.drawable
                .ic_pay_checked));
        itemList.add(new Item(5f, "Maria Souza", firstDate, secondDate, true, false, R.drawable
                .ic_pay_after_due_date));
        itemList.add(new Item(55f, "Jose Oliveira", firstDate, secondDate, true, false, R
                .drawable
                .ic_pay_before_due_date));
        itemList.add(new Item(15f, "Pedro Agua", firstDate, secondDate, true, false, R.drawable
                .ic_pay_checked));
        itemList.add(new Item(5.20f, "Juliana Terra", firstDate, secondDate, true, false, R
                .drawable
                .ic_pay_after_due_date));
        itemList.add(new Item(0.5f, "Guilherme Sal", firstDate, secondDate, true, false, R
                .drawable
                .ic_pay_before_due_date));
        itemList.add(new Item(1535.25f, "Gabriel Vapor", firstDate, secondDate, true, false, R
                .drawable
                .ic_pay_checked));
        itemList.add(new Item(10.00f, "Teste Teste", firstDate, secondDate, true, false, R
                .drawable
                .ic_pay_after_due_date));
        itemList.add(new Item(35.3f, "Nome Sobrenome", firstDate, secondDate, true, false, R
                .drawable
                .ic_pay_before_due_date));*/

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_pay_tab, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview_to_pay);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        final ItemCardAdapter adapter = new ItemCardAdapter(this.getContext());

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



        //mAdapter = new ItemCardAdapter(mContext, itemList, false);





        return view;
    }

}
