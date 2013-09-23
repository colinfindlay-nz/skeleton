
package nz.geek.findlay.skeleton;

import android.support.v7.app.ActionBarActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity
        extends ActionBarActivity {

    @AfterViews
    void afterViews() {
    }
}
