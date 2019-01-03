package comviniciusgpedroso.github.borrowit;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by endsieg on 31/12/18.
 */

// TODO Finish the RecyclerView and ItemCardHolder class
    //https://codelabs.developers.google.com/codelabs/android-training-create-recycler-view/index.html?index=..%2F..%2Fandroid-training#3
public class ItemCardAdapter extends
        RecyclerView.Adapter<ItemCardAdapter.ItemCardHolder> {

    private ArrayList<Item> mItemArrayList;
    private boolean mIsToReceive;
    private Context mContext;
    private ViewGroup pr;

    public ItemCardAdapter(Context context, ArrayList<Item> itemArrayList, boolean isToReceive) {
        this.mItemArrayList = itemArrayList;
        this.mIsToReceive = isToReceive;
        this.mContext = context;
    }

    @Override
    public ItemCardAdapter.ItemCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        // Chooses the layout
        pr = parent;
        if(mIsToReceive) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.receive_item, parent,
                    false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pay_item, parent,
                    false);
        }
        ItemCardHolder itemCardHolder = new ItemCardHolder(v);
        return itemCardHolder;
    }

    @Override
    public void onBindViewHolder(ItemCardAdapter.ItemCardHolder holder, int position) {
        Item currentItem = mItemArrayList.get(position);

        holder.mImageStatus.setImageResource(currentItem.getStatus());
        String valueWithCurrencySymbol = "$" + currentItem.getValue(); // TODO Add currency here too
        holder.mValue.setText(valueWithCurrencySymbol);
        holder.mContact.setText(currentItem.getContact());
        holder.mDueDate.setText(currentItem.getSimpleDueDate());
    }

    @Override
    public int getItemCount() {
        return mItemArrayList.size();
    }

    class ItemCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImageStatus;
        public TextView mValue;
        public TextView mContact;
        public TextView mDueDate;

        public ItemCardHolder(View itemView) {
            super(itemView);
            mValue = itemView.findViewById(R.id.item_value);
            mImageStatus = itemView.findViewById(R.id.item_status_img);
            mContact = itemView.findViewById(R.id.item_contact_name);
            mDueDate = itemView.findViewById(R.id.item_due_date);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Item currentItem = mItemArrayList.get(getAdapterPosition());
            Intent detailIntent;
            if(mIsToReceive) {
                detailIntent = new Intent(pr.getContext(), ReceiveDetailActivity.class);

            } else {
                detailIntent = new Intent(pr.getContext(), PayDetailActivity.class);
            }

            detailIntent.putExtra("amount", currentItem.getAmount());
            detailIntent.putExtra("contact", currentItem.getContact());

            pr.getContext().startActivity(detailIntent);
        }

    }
}
