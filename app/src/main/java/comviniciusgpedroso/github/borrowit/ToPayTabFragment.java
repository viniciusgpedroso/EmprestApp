package comviniciusgpedroso.github.borrowit;


import android.os.Bundle;
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


/**
 * Tab Fragment that shows the user an list of all the current and past
 * payments made.
 * A simple {@link Fragment} subclass.
 */
public class ToPayTabFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public ToPayTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        final FragmentActivity c = getActivity();
        ArrayList<Item> itemList = new ArrayList<>();
        Date firstDate = new Date();
        Date secondDate = new Date();
        itemList.add(new Item(15f, "Joao Silva", firstDate, secondDate, true, R.drawable
                .ic_checked));
        itemList.add(new Item(5f, "Maria Souza", firstDate, secondDate, true, R.drawable
                .ic_after_due_date));
        itemList.add(new Item(55f, "Jose Oliveira", firstDate, secondDate, true, R.drawable
                .ic_before_due_date));
        itemList.add(new Item(15f, "Pedro Agua", firstDate, secondDate, true, R.drawable
                .ic_checked));
        itemList.add(new Item(5.20f, "Juliana Terra", firstDate, secondDate, true, R.drawable
                .ic_after_due_date));
        itemList.add(new Item(0.5f, "Guilherme Sal", firstDate, secondDate, true, R.drawable
                .ic_before_due_date));
        itemList.add(new Item(1535.25f, "Gabriel Vapor", firstDate, secondDate, true, R.drawable
                .ic_checked));
        itemList.add(new Item(10.00f, "Teste Teste", firstDate, secondDate, true, R.drawable
                .ic_after_due_date));
        itemList.add(new Item(35.3f, "Nome Sobrenome", firstDate, secondDate, true, R.drawable
                .ic_before_due_date));

        View view = inflater.inflate(R.layout.fragment_to_pay_tab, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview);

        mLayoutManager = new LinearLayoutManager(c);
        mAdapter = new ItemCardAdapter(itemList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // Inflate the layout for this fragment
        return view;
    }

}
