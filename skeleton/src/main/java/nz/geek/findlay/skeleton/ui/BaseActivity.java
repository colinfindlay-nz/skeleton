package nz.geek.findlay.skeleton.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import com.googlecode.androidannotations.annotations.*;
import nz.geek.findlay.skeleton.R;

@EActivity
public abstract class BaseActivity extends ActionBarActivity {

    private static final String PREFIX = BaseActivity.class.getSimpleName()+"_";
    public static final String INTENT_DRAWER_INITIALLY_OPEN = PREFIX+"DRAWER_OPEN";

    private ActionBarDrawerToggle drawerToggle;
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

        findViewById(R.id.content_frame).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                closeInitiallyOpenDrawer();
            }
        });
    }


    @AfterViews
    protected void configureDrawer() {
        if (getIntent().hasExtra(INTENT_DRAWER_INITIALLY_OPEN)) {
            mainLayout.openDrawer(Gravity.LEFT);
        }

        drawerToggle = new ActionBarDrawerToggle(this, mainLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.app_name) {

            /** Called when a mainLayout has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                getActionBar().setTitle("Closed");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a mainLayout has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("Opened");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mainLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @UiThread
    void closeInitiallyOpenDrawer() {
        if (getIntent().hasExtra(INTENT_DRAWER_INITIALLY_OPEN)) {
            getIntent().removeExtra(INTENT_DRAWER_INITIALLY_OPEN);
            mainLayout.closeDrawer(Gravity.LEFT);
        }
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

    @Click(R.id.sampleButton)
    protected void clickSampleButton() {
        startActivity(MainActivity_.class);
    }

    @Click(R.id.sample2Button)
    protected void clickSample2Button() {
        startActivity(SecondActivity_.class);
    }

    protected void startActivity(Class activity) {
        if (!this.getClass().isAssignableFrom(activity)) {
            final Intent intent = new Intent(this,activity);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(INTENT_DRAWER_INITIALLY_OPEN,true);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);
        } else {
            mainLayout.closeDrawers();
        }
    }
}
