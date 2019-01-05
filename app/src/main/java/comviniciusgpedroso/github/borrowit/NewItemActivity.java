package comviniciusgpedroso.github.borrowit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

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
    // TODO Implement Date Pickers
    private Date borrowDateView;
    private Date dueDateView;

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
                    replyIntent.putExtra(BORROW_DATE_REPLY, (new Date()).getTime());
                    replyIntent.putExtra(DUE_DATE_REPLY, (new Date()).getTime());
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

}
