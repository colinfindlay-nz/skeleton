package nz.geek.findlay.skeleton.ui.fragments;

import android.app.ProgressDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.util.List;

import nz.geek.findlay.skeleton.R;
import nz.geek.findlay.skeleton.model.Photo;
import nz.geek.findlay.skeleton.service.DummyRestService;

@EFragment(R.layout.fragment_sample2)
public class SecondFragment extends BaseFragment {

    @RestService
    DummyRestService dummyRestService;

    @ViewById(R.id.root)
    ViewGroup root;

    @ViewById(R.id.loading)
    ProgressBar loading;

    @ViewById(R.id.label_text)
    TextView textView;

    @AfterViews
    void initData() {
        textView.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        getPhotos();
    }

    @Background
    void getPhotos() {
        List<Photo> photos = dummyRestService.getPhotos ();
        displayPhotos(photos);
    }

    @UiThread
    void displayPhotos(List<Photo> photos) {
        textView.setText("Results from DummyRestService\n\n");
        for (Photo photo : photos) {
            textView.append(photo.getTitle()+"\n");
        }
        textView.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }

}
