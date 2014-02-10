
package nz.geek.findlay.skeleton.ui;

import android.os.Bundle;
import android.view.Window;

import org.androidannotations.annotations.EActivity;

import nz.geek.findlay.skeleton.R;

@EActivity(R.layout.activity_about)
public class AboutActivity
        extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
