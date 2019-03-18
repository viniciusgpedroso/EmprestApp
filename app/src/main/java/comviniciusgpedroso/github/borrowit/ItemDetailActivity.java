package comviniciusgpedroso.github.borrowit;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatDrawableManager;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();

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

        if(intent.getBooleanExtra("isObject", false)) {
            TextView valueDescription = findViewById(R.id.value_object_detail_tv);
            valueDescription.setText(R.string.object_description);
            valueAmount.setText(intent.getStringExtra("objectDescription"));
        } else {
            DecimalFormat df = new DecimalFormat("#.##");
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);

            String valueString = "$ " + df.format(intent.getLongExtra("amount", 0) /
                    CURRENCY_2_DECIMALS_MULTIPLIER);
            valueAmount.setText(valueString);
        }



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

    }

    private void editThisItem() {
        //TODO find way to change notification for edited item if date has changed
    }

    private void archiveThisItem() {
        //TODO find way to disable notification for archived item
    }
}
