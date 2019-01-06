package comviniciusgpedroso.github.borrowit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReceiveDetailActivity extends AppCompatActivity {

    TextView testDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_detail);

        testDates = (TextView) findViewById(R.id.detail_item_value);
        Intent intent = getIntent();
        String testDatesString = "Due date: " + intent.getStringExtra
                ("dueDate") + "\nBorrow Date" + intent.getStringExtra
                ("borrowDate");
        testDates.setText(testDatesString);
    }
}
