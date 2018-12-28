package comviniciusgpedroso.github.borrowit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Tab Fragment that shows the user an list of all the current payments to be
 * received.
 * A simple {@link Fragment} subclass.
 */
public class ToReceiveTabFragment extends Fragment {


    public ToReceiveTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_receive_tab, container, false);
    }

}
