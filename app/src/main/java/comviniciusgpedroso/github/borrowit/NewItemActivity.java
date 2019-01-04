package comviniciusgpedroso.github.borrowit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewItemActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            "comviniciusgpedroso.github.borrowit.REPLY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
    }
}
