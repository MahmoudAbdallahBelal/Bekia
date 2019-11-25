package bekia.recycle.views.home;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import bekia.recycle.BuildConfig;
import bekia.recycle.R;
import bekia.recycle.adapters.TabsAdapter;
import bekia.recycle.helper.Utils;
import bekia.recycle.requests.login.LoginResponse;
import bekia.recycle.views.LocaleManager;
import bekia.recycle.views.chat.user_chats.UserChatsActivity;
import bekia.recycle.views.login.LoginActivity;
import bekia.recycle.views.search.SearchActivity;
import bekia.recycle.views.profile.UserProfileActivity;
import bekia.recycle.views.sell_item.ItemSellActivity;
import bekia.recycle.views.settings.SettingsActivity;
import bekia.recycle.views.user_items.UserItemsActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import static bekia.recycle.R.*;
import static bekia.recycle.helper.Utils.retrieveUserLanguage;
import static bekia.recycle.views.settings.SettingsActivity.lan;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CircleImageView imageProfile ;
    ImageView imageSellItem , imageSearchItem;
    TextView categoriesTxt , mostPopularTxt;

    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base,lan));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(id.toolbar);
        setSupportActionBar(toolbar);



        tabLayout=(TabLayout)findViewById(id.tabLayout);
        viewPager=(ViewPager)findViewById(id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.categories)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.mostPopular)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final TabsAdapter adapter = new TabsAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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

        DrawerLayout drawer = (DrawerLayout) findViewById(id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, string.navigation_drawer_open, string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View header = ((NavigationView)findViewById(id.nav_view)).getHeaderView(0);
        TextView txtName = header.findViewById(id.textView_profile_name);
        imageProfile  = header.findViewById(id.imageView_user_profile_home);
        imageSellItem = findViewById(id.imageView_toolbar_sell_item);
        imageSearchItem = findViewById(id.imageView_toolbar_search_item);
        categoriesTxt = findViewById(id.text_home_select_categories);
        mostPopularTxt = findViewById(id.text_home_select_most_populars);
        imageSearchItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this , SearchActivity.class));
            }
        });

//        categoriesTxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//             categoriesTxt.setBackground(getResources().getDrawable(R.drawable.layout_border_selected));
//             categoriesTxt.setTextColor(getResources().getColor(R.color.colorBlack));
//             mostPopularTxt.setBackground(getResources().getDrawable(R.drawable.layout_border));
//             mostPopularTxt.setTextColor(getResources().getColor(R.color.colorWhite));
//
//             recyclerViewCategories.setVisibility(View.GONE);
//             getCategories();
//            }
//        });
//
//        mostPopularTxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mostPopularTxt.setBackground(getResources().getDrawable(R.drawable.layout_border_selected));
//                mostPopularTxt.setTextColor(getResources().getColor(R.color.colorBlack));
//                categoriesTxt.setBackground(getResources().getDrawable(R.drawable.layout_border));
//                categoriesTxt.setTextColor(getResources().getColor(R.color.colorWhite));
//                recyclerViewCategories.setVisibility(View.GONE);
//
//            }
//        });
//
//

        imageSellItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this , ItemSellActivity.class);
                startActivity(intent);
            }
        });


        if(Utils.retrieveUserInfo(HomeActivity.this) != null)
        {
            LoginResponse loginResponse = Utils.retrieveUserInfo(HomeActivity.this);
            if(!loginResponse.getName().equals(" ")) {
               txtName.setText(loginResponse.getName().toString());
            }
            if(loginResponse.getProfile_image() != null) {

            Bitmap bitmap =    StringToBitMap(loginResponse.getProfile_image());
              if(bitmap != null)
              {
                  imageProfile.setImageBitmap(bitmap);
              }
            }

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.fab), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.action), null).show();
            }
        });


    }

//    private void defaultSelected() {
//        categoriesTxt.setBackground(getResources().getDrawable(R.drawable.layout_border_selected));
//        categoriesTxt.setTextColor(getResources().getColor(R.color.colorBlack));
//        mostPopularTxt.setBackground(getResources().getDrawable(R.drawable.layout_border));
//        mostPopularTxt.setTextColor(getResources().getColor(R.color.colorWhite));
//        recyclerViewCategories.setVisibility(View.VISIBLE);
//
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_sell_item) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_categories) {

            if(!(this instanceof HomeActivity))
            {
                Intent intent = new Intent(this , HomeActivity.class);
                startActivity(intent);
            }

            // Handle the camera action
        } else if (id == R.id.nav_profile) {

            Intent intent = new Intent(this , UserProfileActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_sell_item) {
            Intent intent = new Intent(this , ItemSellActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(HomeActivity.this , SettingsActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_user_item) {
            Intent intent = new Intent(HomeActivity.this , UserItemsActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_share) {
            shareApp();

        } else if (id == R.id.nav_rate) {
            rateApp();

        }
        else if (id == R.id.nav_logout) {
            logOut();

        }
        else if (id == R.id.nav_inbox) {
            Intent intent = new Intent(HomeActivity.this , UserChatsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, getString(R.string.appNotFound), Toast.LENGTH_LONG).show();
        }
    }

    private void logOut()
    {
        //clear all cached data
        Utils.saveUserInfoForId(this,null);
        Utils.saveUserInfo(this,null);
        Utils.saveCreateChatResponse(this,null);
        Utils.saveUserLanguage(this,null);
        Intent in = new Intent(HomeActivity.this , LoginActivity.class);
       in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);

       startActivity(in);
    }
    private void shareApp(){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage= getString(R.string.recomndationMessage);
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.chooseOne)));
        } catch(Exception e) {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();        }

    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.NO_WRAP);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

}
