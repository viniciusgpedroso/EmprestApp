package comviniciusgpedroso.github.borrowit;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;


/**
 * Tab Fragment that shows the user an overview of the amount to be received
 * and to be paid.
 * A simple {@link Fragment} subclass.
 */
public class OverviewTabFragment extends Fragment {

    private ItemViewModel mItemViewModel;
    private TextView receiveTextView;
    private TextView payTextView;

    public OverviewTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_overview_tab, container, false);
        receiveTextView = view.findViewById(R.id.overview_receive_amount_tv);
        payTextView = view.findViewById(R.id.overview_pay_amount_tv);

        // Get a new or existing ViewModel from the ViewModelProvider
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        // Updates the cards if the values are changed
        mItemViewModel.getPaySum().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float aFloat) {
                updateAmountCards();
            }
        });

        mItemViewModel.getReceiveSum().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float aFloat) {
                updateAmountCards();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    /* TODO Add onClick listeners (Calendar os move to the tab) for the
    @+id/overview_pay and @+id/overview_receive */

    public void updateAmountCards() {
        // TODO Needs to fix currency
        Float payValue = mItemViewModel.getPaySum().getValue();
        Float receiveValue = mItemViewModel.getReceiveSum().getValue();
        DecimalFormat df = new DecimalFormat("#.##");
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);

        String payString = payValue == null ? "$0.00" : "$" + df.format(payValue);
        String receiveString = receiveValue == null ? "$0.00" : "$" + df.format(receiveValue);

        receiveTextView.setText(receiveString);
        payTextView.setText(payString);
    }

}
