package com.cnakhn.faradarscompletion.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.multidex.MultiDex;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cnakhn.faradarscompletion.Activities.JSONParser.JSONParserActivity;
import com.cnakhn.faradarscompletion.Contacts.ContactsActivity;
import com.cnakhn.faradarscompletion.EventBus.EventBusActivity;
import com.cnakhn.faradarscompletion.MainAdapter;
import com.cnakhn.faradarscompletion.R;
import com.cnakhn.faradarscompletion.Utils;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_LOGIN = 1100;
    public static final int RESULT_OK = 1101;
    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private CoordinatorLayout coordinatorLayout;
    private ConnectivityListener connectivityListener;
    private Snackbar connectivitySnackbar;
    private SwipeRefreshLayout refreshLayout;
    private FirebaseAnalytics analytics;
    private TextView headerUsername, headerEmail;
    private ImageView headerAvatar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        analytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.METHOD, "User Logged in.");
        analytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
        setupToolbar();
        setupNavigationView();
        initViews();
    }


    @Override
    public void onStart() {
        super.onStart();
        connectivityListener = new ConnectivityListener();
        registerReceiver(connectivityListener, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(connectivityListener);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.activity_items);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        refreshLayout = findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorOrange), ContextCompat.getColor(MainActivity.this, R.color.colorOrangeDark));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });
        adapter = new MainAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setupToolbar() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
    }

    private void setupNavigationView() {
        final NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_menu_edit_profile:
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        break;

                    case R.id.nav_menu_profile:
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        break;

                    case R.id.nav_menu_fragments:
                        startActivity(new Intent(MainActivity.this, FragmentsActivity.class));
                        break;

                    case R.id.nav_menu_products:
                        startActivity(new Intent(MainActivity.this, ProductsActivity.class));
                        break;

                    case R.id.nav_menu_contacts:
                        startActivity(new Intent(MainActivity.this, ContactsActivity.class));
                        break;

                    case R.id.nav_menu_multi_tab:
                        startActivity(new Intent(MainActivity.this, MultiTabActivity.class));
                        break;

                    case R.id.nav_menu_reveal_card:
                        startActivity(new Intent(MainActivity.this, CircularRevealActivity.class));
                        break;

                    case R.id.nav_menu_intro_slider:
                        startActivity(new Intent(MainActivity.this, IntroSliderActivity.class));
                        break;
                }
                return false;
            }
        });

        headerUsername = navigationView.getHeaderView(0).findViewById(R.id.header_username);
        headerEmail = navigationView.getHeaderView(0).findViewById(R.id.header_email);
        headerAvatar = navigationView.getHeaderView(0).findViewById(R.id.header_avatar);
        Utils utils = new Utils(getApplicationContext());
        String username = utils.getUsername();
        String email = utils.getEmail();
        setUserInfo(username, email);

    }

    private void setUserInfo(String username, String email) {
        headerUsername.setText(username);
        headerEmail.setText(email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu files = menu.addSubMenu("Files, Database & Parser");
        files.add("Internal File").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, FilesActivity.class));
                return false;
            }
        });

        files.add("XML Parser").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, XMLParserActivity.class));
                return false;
            }
        });

        files.add("JSON Parser").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, JSONParserActivity.class));
                return false;
            }
        });

        files.add("SQLite Database").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, SQLiteDatabaseActivity.class));
                return false;
            }
        });

        menu.add("Internet Connection (Network)").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, NetworkActivity.class));
                return false;
            }
        });

        menu.add("Incoming Number Receiver").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, NumberReceiverActivity.class));
                return false;
            }
        });

        menu.add("Custom Receiver").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, CustomReceiverActivity.class));
                return false;
            }
        });

        menu.add("Event Bus Example").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, EventBusActivity.class));
                return false;
            }
        });

        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_view_github:
                Toast.makeText(MainActivity.this, "Github.com/CnaKhn", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private class ConnectivityListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) MainActivity.this.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
            if (isConnected) {
                if (connectivitySnackbar != null) {
                    connectivitySnackbar.dismiss();
                    connectivitySnackbar = Snackbar.make(coordinatorLayout, "Connected.", Snackbar.LENGTH_LONG);
                    connectivitySnackbar.show();
                }
            } else {
                connectivitySnackbar = Snackbar.make(coordinatorLayout, "Waiting for network...", Snackbar.LENGTH_INDEFINITE);
                connectivitySnackbar.show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOGIN) {
            if (resultCode == RESULT_OK) {
                String username = data.getStringExtra("username");
                String email = data.getStringExtra("email");
                if (username != null && !username.isEmpty() && email != null && !email.isEmpty()) {
                    setUserInfo(username, email);
                }

            }
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
