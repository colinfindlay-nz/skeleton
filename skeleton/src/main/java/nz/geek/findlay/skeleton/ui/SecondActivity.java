
package nz.geek.findlay.skeleton.ui;

import com.googlecode.androidannotations.annotations.EActivity;
import nz.geek.findlay.skeleton.R;
import nz.geek.findlay.skeleton.ui.fragments.BaseFragment;
import nz.geek.findlay.skeleton.ui.fragments.SecondFragment_;

@EActivity(R.layout.activity_main)
public class SecondActivity
        extends BaseActivity {


    @Override
    public BaseFragment getDefaultFragment() {
        return SecondFragment_.builder().build();
    }

    @Override
    public int getContentView() {
        return R.id.content_frame;
    }
}
