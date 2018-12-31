package comviniciusgpedroso.github.borrowit;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by endsieg on 31/12/18.
 */

// TODO Finish the RecyclerView and ItemCardHolder class
    //https://codelabs.developers.google.com/codelabs/android-training-create-recycler-view/index.html?index=..%2F..%2Fandroid-training#3
public class ItemCardAdapter extends
        RecyclerView.Adapter<ItemCardAdapter.ItemCardHolder> {
    @Override
    public ItemCardAdapter.ItemCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ItemCardAdapter.ItemCardHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ItemCardHolder extends RecyclerView.ViewHolder {
        public final CardView itemCard;
        final ItemCardAdapter mAdapter;

        public ItemCardHolder(View itemCardView, ItemCardAdapter adapter) {
            super(itemCardView);
            itemCard = itemView.findViewById(R.id.itemReceiveCard);
            this.mAdapter = adapter;
        }

    }
}
