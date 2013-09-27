package nz.geek.findlay.skeleton.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import nz.geek.findlay.skeleton.R;
import nz.geek.findlay.skeleton.ui.fragments.BaseFragment;
import nz.geek.findlay.skeleton.ui.fragments.HomeFragment_;
import nz.geek.findlay.skeleton.ui.fragments.SecondFragment;

@EActivity
public abstract class BaseActivity extends ActionBarActivity {

    private static final String PREFIX = BaseActivity.class.getSimpleName()+"_";
    public static final String CONTENT_INTENT = PREFIX+"Content";
    private ActionBarDrawerToggle drawerToggle;
    private BaseFragment currentFragment;

    @ViewById(R.id.main_layout)
    DrawerLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @AfterViews
    void afterViews() {
        drawerToggle = new ActionBarDrawerToggle(this, mainLayout,
                android.R.drawable.ic_delete, R.string.drawer_open, R.string.drawer_close) {

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

        if (currentFragment == null) {
            currentFragment = getDefaultFragment();
        }
        getSupportFragmentManager().beginTransaction().add(getContentView(),currentFragment).commit();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        currentFragment = (BaseFragment)getSupportFragmentManager().findFragmentById(savedInstanceState.getInt(CONTENT_INTENT, -1));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CONTENT_INTENT, currentFragment.getId());
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

    public  BaseFragment getDefaultFragment() {
        return HomeFragment_.builder().build();
    }
    public  int getContentView() {
        return R.id.content_frame;
    }

    @Click(R.id.sampleButton)
    protected void clickSampleButton() {
        if (!(this instanceof MainActivity)) {
            finish();
        }
    }

    @Click(R.id.sample2Button)
    protected void clickSample2Button() {
        if (!(currentFragment instanceof SecondFragment)) {

        }
    }
}
