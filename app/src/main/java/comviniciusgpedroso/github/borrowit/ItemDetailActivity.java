package comviniciusgpedroso.github.borrowit;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatDrawableManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import static comviniciusgpedroso.github.borrowit.Item.CURRENCY_2_DECIMALS_MULTIPLIER;

public class ItemDetailActivity extends AppCompatActivity {

    private LinearLayout layoutFabEdit;
    private LinearLayout layoutFabMarkAsDone;
    private LinearLayout layoutFabArchive;
    private FloatingActionButton fab;
    private FloatingActionButton fabEdit;
    private FloatingActionButton fabMarkAsDone;
    private FloatingActionButton fabArchive;
    private boolean fabExpanded;
    private Item parcelItem;
    private ImageView imageStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        parcelItem = intent.getParcelableExtra("parcelItem");

        // Menu of action buttons
        fab = findViewById(R.id.fab_detail);
        fabEdit = findViewById(R.id.fabEdit);
        fabMarkAsDone = findViewById(R.id.fabMarkAsDone);
        fabArchive = findViewById(R.id.fabArchive);
        layoutFabMarkAsDone = findViewById(R.id.layoutFabMarkAsDone);
        layoutFabEdit = findViewById(R.id.layoutFabEdit);
        layoutFabArchive = findViewById(R.id.layoutFabArchive);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded) {
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editThisItem();
            }
        });

        fabMarkAsDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markThisItemAsDone();
            }
        });

        fabArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                archiveThisItem();
            }
        });

        closeSubMenusFab();

        //Fills the details from the current item
        TextView valueAmount = findViewById(R.id.amount_object_tv);

        if(parcelItem.isObject()) {
            TextView valueDescription = findViewById(R.id.value_object_detail_tv);
            valueDescription.setText(R.string.object_description);
            valueAmount.setText(parcelItem.getObjectDescription());
        } else {
            DecimalFormat df = new DecimalFormat("#.##");
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);

            String valueString = "$ " + df.format(parcelItem.getAmount() /
                    CURRENCY_2_DECIMALS_MULTIPLIER);
            valueAmount.setText(valueString);
        }

        TextView contactTextView = findViewById(R.id.contact_tv);
        if(parcelItem.isToReceive()) {
            contactTextView.setText(R.string.receive_from);
        } else {
            contactTextView.setText(R.string.return_to);
        }

        TextView contactDescription = findViewById(R.id.contact_description_tv);
        contactDescription.setText(parcelItem.getContact());

        TextView borrowDateTextView = findViewById(R.id.borrow_date_tv_detail);
        borrowDateTextView.setText(Item.getSimpleDate(parcelItem.getBorrowDate()));

        imageStatusView = findViewById(R.id.status_img_detail);
        imageStatusView.setImageResource(parcelItem.getImageCodeFromStatus());

        TextView statusDescriptionTextView = findViewById(R.id.status_description);
        String dueDateDescription = getString(R.string.due_by) + " " + Item.getSimpleDate(parcelItem
                        .getDueDate());
        statusDescriptionTextView.setText(dueDateDescription);


    }

    /**
     * Collapses Object and Money submenus from the addFab
     */
    private void closeSubMenusFab() {
        layoutFabMarkAsDone.setVisibility(View.INVISIBLE);
        layoutFabEdit.setVisibility(View.INVISIBLE);
        layoutFabArchive.setVisibility(View.INVISIBLE);
        fab.setImageResource(R.drawable.ic_edit_white_24dp);
        fabExpanded = false;
    }

    private void openSubMenusFab() {
        layoutFabMarkAsDone.setVisibility(View.VISIBLE);
        layoutFabEdit.setVisibility(View.VISIBLE);
        layoutFabArchive.setVisibility(View.VISIBLE);
        fab.setImageResource(R.drawable.ic_close_white_24dp);
        fabExpanded = true;
    }

    private void markThisItemAsDone() {
        //TODO find way to change notification for done item if date has changed
        parcelItem.setDone();
        imageStatusView.setImageResource(parcelItem.getImageCodeFromStatus());
        closeSubMenusFab();
    }

    private void editThisItem() {
        //TODO find way to change notification for edited item if date has changed
    }

    private void archiveThisItem() {
        //TODO find way to disable notification for archived item

    }
}
