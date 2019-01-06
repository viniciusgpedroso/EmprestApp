package comviniciusgpedroso.github.borrowit;

import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Date;

public class NewItemActivity extends AppCompatActivity {
    public static final String TORECEIVE_REPLY =
            "comviniciusgpedroso.github.borrowit.TORECEIVEREPLY";
    public static final String VALUE_REPLY =
            "comviniciusgpedroso.github.borrowit.VALUEREPLY";
    public static final String PAID_REPLY =
            "comviniciusgpedroso.github.borrowit.PAIDREPLY";
    public static final String CONTACT_REPLY =
            "comviniciusgpedroso.github.borrowit.CONTACTREPLY";
    public static final String BORROW_DATE_REPLY =
            "comviniciusgpedroso.github.borrowit.BORROWDATEREPLY";
    public static final String DUE_DATE_REPLY =
            "comviniciusgpedroso.github.borrowit.DUEDATEREPLY";

    private RadioGroup radioGroup;
    private EditText valueEditView;
    private EditText contactEditView;
    private CheckBox alreadyPaidCheckBox;
    // Date Default values are now
    private boolean borrowDatePickerWasChosen = true;
    private Date borrowDate = new Date();
    private Date dueDate = new Date();

    private boolean toReceive;
    private boolean alreadyPaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        radioGroup = (RadioGroup) findViewById(R.id.pay_radio_group);
        valueEditView = (EditText) findViewById(R.id.amount_et);
        contactEditView = (EditText) findViewById(R.id.contact_et);
        alreadyPaidCheckBox = (CheckBox) findViewById(R.id.already_paid_cb);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                Float value = Float.valueOf(valueEditView.getText().toString());
                String contact = contactEditView.getText().toString();
                toReceive = radioGroup.getCheckedRadioButtonId() == R.id.to_receive_rb;
                alreadyPaid = alreadyPaidCheckBox.isChecked();
                if (TextUtils.isEmpty(valueEditView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    replyIntent.putExtra(VALUE_REPLY, value);
                    replyIntent.putExtra(TORECEIVE_REPLY, toReceive);
                    replyIntent.putExtra(PAID_REPLY, alreadyPaid);
                    replyIntent.putExtra(CONTACT_REPLY, contact);
                    replyIntent.putExtra(BORROW_DATE_REPLY, borrowDate.getTime());
                    replyIntent.putExtra(DUE_DATE_REPLY, dueDate.getTime());
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    /**
     * Handles the button click to create a new date picker fragment and
     * show it.
     *
     * @param view View that was clicked
     */
    public void showBorrowDatePicker(View view) {
        borrowDatePickerWasChosen = true;
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "Date Picker");
    }

    /**
     * Handles the button click to create a new date picker fragment and
     * show it.
     *
     * @param view View that was clicked
     */
    public void showDueDatePicker(View view) {
        borrowDatePickerWasChosen = false;
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "Date Picker");
    }


    /**
     * Process the date picker result into strings that can be displayed in
     * a Toast.
     *
     * @param year Chosen year
     * @param month Chosen month
     * @param day Chosen day
     */
    public void processDatePicker(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (day_string +
                "/" + month_string +
                "/" + year_string);

        Toast.makeText(this, dateMessage,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Process the date picker result into strings that can be displayed in
     * a Toast.
     * @param chosenDate date chosen by the user trough the DatePickerFragment
     *                   class
     */
    public void processDatePickerResult(Date chosenDate) {
        if (borrowDatePickerWasChosen) {
            borrowDate = chosenDate;
        } else {
            dueDate = chosenDate;
        }
    }
}
