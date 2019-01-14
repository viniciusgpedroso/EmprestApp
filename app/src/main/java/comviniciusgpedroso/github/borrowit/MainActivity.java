package comviniciusgpedroso.github.borrowit;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final int NEW_ITEM_ACTIVITY_REQUEST_CODE = 1;
    public static final String NEW_ITEM_ACTIVITY_IS_OBJECT = "comviniciusgpedroso.github.borrowit" +
            ".ISOBJECT";

    private ItemViewModel mItemViewModel;

    private boolean fabExpanded = false;
    FloatingActionButton fabAdd;
    FloatingActionButton fabAddMoney;
    FloatingActionButton fabAddObject;
    private LinearLayout layoutFabAddMoney;
    private LinearLayout layoutFabAddObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ----
        // Tabs
        // ----

        // Create an instance of the tab layout from the view
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        // Set the text for each tab.
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_overview));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_receive));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_return));
        // Set the height
//        ViewGroup.LayoutParams tabParams = tabLayout.getLayoutParams();
//        tabParams.height = 25;
//        tabLayout.setLayoutParams(tabParams);
        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        // Use PagerAdapter to manage page views in fragments.
        // Each page is represented by its own fragment.
        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new comviniciusgpedroso.github.borrowit
                .PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        // Setting a listener for clicks.
        viewPager.addOnPageChangeListener(new TabLayout
                .TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // Get a new or existing ViewModel from the ViewModelProvider
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        // Menu of action buttons
        fabAdd = this.findViewById(R.id.fab);
        fabAddMoney = this.findViewById(R.id.fabMoney);
        fabAddObject = this.findViewById(R.id.fabObject);

        layoutFabAddObject = (LinearLayout) this.findViewById(R.id
                .layoutFabObject);
        layoutFabAddMoney = (LinearLayout) this.findViewById(R.id
                .layoutFabMoney);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fabExpanded) {
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
                //addNewItemActivity();

            }
        });

        fabAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewItemActivity(false);
            }
        });

        fabAddObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewItemActivity(true);
            }
        });

        closeSubMenusFab();

        // Settings
        android.support.v7.preference.PreferenceManager.setDefaultValues
                (this, R.xml.preferences, false);
        SharedPreferences sharedPref = android.support.v7.preference
                .PreferenceManager.getDefaultSharedPreferences(this);
        Boolean switchNotificationPref = sharedPref.getBoolean
                (SettingsActivity.KEY_PREF_NOTIFICATION_SWITCH, false);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {
            addNewItemActivity(false);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_schedule) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Collapses Object and Money submenus from the addFab
     */
    private void closeSubMenusFab() {
        layoutFabAddMoney.setVisibility(View.INVISIBLE);
        layoutFabAddObject.setVisibility(View.INVISIBLE);
        fabAdd.setImageResource(R.drawable.ic_add_white_24dp);
        fabExpanded = false;
    }

    private void openSubMenusFab() {
        layoutFabAddMoney.setVisibility(View.VISIBLE);
        layoutFabAddObject.setVisibility(View.VISIBLE);
        fabAdd.setImageResource(R.drawable.ic_close_white_24dp);
        fabExpanded = true;
    }

    public void addNewItemActivity(boolean isObject) {
        // TODO Add isObject to the intent and deal with the layout for
        // NewItemActivity
        Intent intent = new Intent(MainActivity.this, NewItemActivity
                .class);
        intent.putExtra(NEW_ITEM_ACTIVITY_IS_OBJECT, isObject);
        startActivityForResult(intent, NEW_ITEM_ACTIVITY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ITEM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Gets data back from the NewItemActivity Intent
            Long valueAmount = data.getLongExtra(NewItemActivity
                    .VALUE_REPLY, 0);
            boolean toReceive = data.getBooleanExtra(NewItemActivity
                    .TORECEIVE_REPLY, true);
            String contact = data.getStringExtra(NewItemActivity.CONTACT_REPLY);
            Long borrowDateTime = data.getLongExtra(NewItemActivity
                    .BORROW_DATE_REPLY, (new Date()).getTime());
            Long dueDateTime = data.getLongExtra(NewItemActivity
                    .DUE_DATE_REPLY, (new Date()).getTime());
            // If it is done use status == 2, otherwise let the Item class
            // deal with the results
            int alreadyPaidStatus = data.getBooleanExtra(NewItemActivity
                    .PAID_REPLY, false) ? Item.DONE : -1;
            boolean isObject = data.getBooleanExtra(NewItemActivity.ISOBJECT_REPLY, false);
            String objectDescr = data.getStringExtra(NewItemActivity.OBJECT_DESCRIPTION_REPLY);

            // Creates a new item and adds to the view model
            Item item = new Item(UUID.randomUUID(), valueAmount, contact,
                    Converters.fromTimeStamp(borrowDateTime), Converters
                    .fromTimeStamp(dueDateTime), toReceive, isObject,
                    objectDescr, alreadyPaidStatus);
            mItemViewModel.insert(item);

        }
        else {
            Toast.makeText(
                    this,
                    R.string.not_saved_toast,
                    Toast.LENGTH_LONG).show();
        }

        if(fabExpanded) {
            closeSubMenusFab();
        }
    }
}
