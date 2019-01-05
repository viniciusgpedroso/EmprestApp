package comviniciusgpedroso.github.borrowit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Tab Fragment that shows the user an overview of the amount to be received
 * and to be paid.
 * A simple {@link Fragment} subclass.
 */
public class OverviewTabFragment extends Fragment {


    public OverviewTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overview_tab, container, false);
    }

    /* TODO Add onClick listeners for the @+id/overview_pay and
    @+id/overview_receive */

}
