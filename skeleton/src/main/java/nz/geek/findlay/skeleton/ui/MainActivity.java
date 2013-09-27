
package nz.geek.findlay.skeleton.ui;

import com.googlecode.androidannotations.annotations.EActivity;
import nz.geek.findlay.skeleton.R;
import nz.geek.findlay.skeleton.ui.fragments.BaseFragment;
import nz.geek.findlay.skeleton.ui.fragments.HomeFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity
        extends BaseActivity {


    @Override
    public BaseFragment getDefaultFragment() {
        return HomeFragment_.builder().build();
    }

    @Override
    public int getContentView() {
        return R.id.content_frame;
    }


}
