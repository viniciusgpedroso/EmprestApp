package comviniciusgpedroso.github.borrowit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by endsieg on 31/12/18.
 */


//https://codelabs.developers.google.com/codelabs/android-training-create-recycler-view/index.html?index=..%2F..%2Fandroid-training#3
public class ItemCardAdapter extends
        RecyclerView.Adapter<ItemCardAdapter.ItemCardHolder> {

    private final LayoutInflater mInflater;
    private ArrayList<Item> mItemArrayList;
    private ViewGroup pr;
    private Context mContext;

    public ItemCardAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public ItemCardAdapter.ItemCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        pr = parent;
        v = mInflater.inflate(R.layout.card_item, parent, false);
        return new ItemCardHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemCardHolder holder, int position) {
        Item currentItem = mItemArrayList.get(position);

        // Changes text color from red to green
        if(currentItem.isToReceive()) {
            holder.mValue.setTextColor(pr.getResources().getColor(R.color
                    .colorPrimary));
            holder.mObjectDescription.setTextColor(pr.getResources().getColor(R.color
                    .colorPrimary));
        }

        // Object or money
        if(currentItem.isObject()) {
            String objectDescrWithSymbol = "*" + currentItem
                    .getObjectDescription();
            holder.mObjectDescription.setText(objectDescrWithSymbol);
            holder.mValue.setVisibility(View.INVISIBLE);
            holder.mObjectDescription.setVisibility(View.VISIBLE);
        } else {
            String valueWithCurrencySymbol = "$" + currentItem.getValue(); // TODO Add currency here too
            holder.mValue.setText(valueWithCurrencySymbol);
            holder.mObjectDescription.setVisibility(View.INVISIBLE);
            holder.mValue.setVisibility(View.VISIBLE);
        }

        // Overdue emphasis
        if(currentItem.getStatus() == Item.OVERDUE) {
            holder.mOverdueText.setVisibility(View.VISIBLE);
            holder.mDueDate.setTypeface(holder.mDueDate.getTypeface(),
                    Typeface.BOLD);
            holder.mDueDateText.setTypeface(holder.mDueDate.getTypeface(),
                                        Typeface.BOLD);
        }

        holder.mDueDate.setText(currentItem.getSimpleDueDate());
        holder.mContact.setText(currentItem.getContact());
        holder.mImageStatus.setImageResource(currentItem.getImageCodeFromStatus());
    }

    public Item getItemAtPosition(int position) {
        return mItemArrayList.get(position);
    }

    // getItemCount() is called many times, and when it is first called,
    // mItemArrayList has not been updated (means initially, it's null).
    @Override
    public int getItemCount() {
        if (mItemArrayList != null)
            return mItemArrayList.size();
        else return 0;
    }

    void setItems(List<Item> items) {
        mItemArrayList = (ArrayList<Item>) items;
        notifyDataSetChanged();
    }

    class ItemCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImageStatus;
        public TextView mValue;
        public TextView mObjectDescription;
        public TextView mContact;
        public TextView mDueDate;
        public TextView mDueDateText;
        public TextView mOverdueText;

        public ItemCardHolder(View itemView) {
            super(itemView);
            mValue = itemView.findViewById(R.id.item_value);
            mImageStatus = itemView.findViewById(R.id.item_status_img);
            mContact = itemView.findViewById(R.id.item_contact_name);
            mDueDate = itemView.findViewById(R.id.item_due_date);
            mObjectDescription = itemView.findViewById(R.id
                    .item_object_description);
            mDueDateText = itemView.findViewById(R.id.item_due_date_text);
            mOverdueText = itemView.findViewById(R.id.item_is_overdue);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Item currentItem = mItemArrayList.get(getAdapterPosition());
            Intent detailIntent;
            detailIntent = new Intent(pr.getContext(), PayDetailActivity.class);

            detailIntent.putExtra("amount", currentItem.getAmount());
            detailIntent.putExtra("contact", currentItem.getContact());
            detailIntent.putExtra("borrowDate", currentItem.getBorrowDate());
            detailIntent.putExtra("dueDate", currentItem.getDueDate());

            pr.getContext().startActivity(detailIntent);
        }

    }
}
