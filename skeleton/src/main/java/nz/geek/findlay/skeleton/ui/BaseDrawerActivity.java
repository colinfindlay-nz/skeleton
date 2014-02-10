package nz.geek.findlay.skeleton.ui;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import nz.geek.findlay.skeleton.R;

@EActivity
@OptionsMenu(R.menu.activity_base)
public abstract class BaseDrawerActivity extends ActionBarActivity {

    private static final String PREFIX = BaseDrawerActivity.class.getSimpleName() + "_";

    private DrawerToggle drawerToggle;
    @ViewById(R.id.main_layout)
    DrawerLayout mainLayout;
    @ViewById(R.id.content_frame)
    ViewGroup contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base);
        getLayoutInflater().inflate(layoutResID, (ViewGroup) findViewById(R.id.content_frame), true);
    }


    @AfterViews
    protected void configureDrawer() {

        drawerToggle = new DrawerToggle(this, mainLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.app_name);
        mainLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mainLayout.isDrawerOpen(R.id.drawer);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    protected void startActivity(Class activity) {
        final Intent intent = new Intent(getApplicationContext(), activity);
        if (BaseDrawerActivity.class.isAssignableFrom(activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            drawerToggle.setNextActivity(intent);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
            drawerToggle.setNextActivity(null);
            startActivity(intent);
        }
        mainLayout.closeDrawer(Gravity.LEFT);
    }

    @Click(R.id.sampleButton)
    protected void clickSampleButton() {
        startActivity(MainActivity_.class);
    }

    @Click(R.id.sample2Button)
    protected void clickSample2Button() {
        startActivity(SecondActivity_.class);
    }

    @Click(R.id.aboutButton)
    protected void clickAboutButton() {
        startActivity(AboutActivity_.class);
    }


    private static class DrawerToggle extends ActionBarDrawerToggle {

        private final Activity mActivity;
        private Intent nextActivity;

        public DrawerToggle(Activity activity, DrawerLayout drawerLayout, int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
            this.mActivity = activity;
        }

        /**
         * Called when a mainLayout has settled in a completely closed state.
         */
        public void onDrawerClosed(View view) {
            mActivity.invalidateOptionsMenu();
            if (nextActivity != null) {
                mActivity.startActivity(nextActivity);
                mActivity.finish();
            }
        }

        /**
         * Called when a mainLayout has settled in a completely open state.
         */
        public void onDrawerOpened(View drawerView) {
            mActivity.invalidateOptionsMenu();
        }

        public void setNextActivity(Intent nextActivity) {
            this.nextActivity = nextActivity;
        }
    }

    ;
}
