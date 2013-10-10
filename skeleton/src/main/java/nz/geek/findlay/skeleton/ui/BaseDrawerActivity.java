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
@OptionsMenu(R.menu.activity_base)
public abstract class BaseDrawerActivity extends ActionBarActivity {

    private static final String PREFIX = BaseDrawerActivity.class.getSimpleName() + "_";
    private static final String DRAWER_OPEN_INTENT = "BaseActivity_DrawerOpen";

    @Extra(DRAWER_OPEN_INTENT)
    Boolean drawerInitiallyOpen = null;
    @ViewById(R.id.main_layout)
    DrawerLayout mainLayout;
    @ViewById(R.id.content_frame)
    ViewGroup contentLayout;
    private ActionBarDrawerToggle drawerToggle;

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

    @Override
    protected void onNewIntent(Intent intent) {
        super.setIntent(intent);
        closeInitiallyOpenDrawer();
    }

    @AfterViews
    protected void configureDrawer() {
        if (drawerInitiallyOpen != null && drawerInitiallyOpen) {
            mainLayout.openDrawer(Gravity.LEFT);
            contentLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    closeInitiallyOpenDrawer();
                }
            });
        } else {
            mainLayout.closeDrawer(Gravity.LEFT);
        }

        drawerToggle = new ActionBarDrawerToggle(this, mainLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.app_name) {

            /** Called when a mainLayout has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a mainLayout has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mainLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @UiThread
    void closeInitiallyOpenDrawer() {
        getIntent().removeExtra(DRAWER_OPEN_INTENT);
        mainLayout.closeDrawer(Gravity.LEFT);
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
        // Top-Level tasks are started fresh.
        if (BaseDrawerActivity.class.isAssignableFrom(activity)) {
            intent.putExtra(DRAWER_OPEN_INTENT, Boolean.TRUE);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION );
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
            startActivity(intent);
            mainLayout.closeDrawer(Gravity.LEFT);
        }
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


}
