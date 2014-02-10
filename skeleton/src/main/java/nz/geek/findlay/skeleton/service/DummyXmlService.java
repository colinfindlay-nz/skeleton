package nz.geek.findlay.skeleton.service;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.androidannotations.api.rest.RestClientHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import nz.geek.findlay.skeleton.model.Photo;
import nz.geek.findlay.skeleton.service.java.util.List_Photo;

@Rest(rootUrl = "http://api.flickr.com/services/rest/", converters = { PhotoConverter.class })
public interface DummyXmlService extends RestClientHeaders {

    // url variables are mapped to method parameter names.
    @Get("?method=flickr.interestingness.getList&api_key=2ce9d959cc02962998397006a002dfdf&format=xml&nojsoncallback=1&api_sig=25db9979adde7e8b836c06a41135edff")
    @Accept(MediaType.APPLICATION_XML)
    List<Photo> getPhotos();
}

class PhotoConverter extends GsonHttpMessageConverter {

    public PhotoConverter() {}

    @Override
    protected List_Photo readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        FlickrPhotosWrapper results = new Gson().fromJson(new JsonReader(new InputStreamReader(inputMessage.getBody())), FlickrPhotosWrapper.class);

        List_Photo photos = new List_Photo();
        for (FlickrPhoto fPhoto : results.photos.photo) {
            Photo photo = new Photo();
            photo.setTitle(fPhoto.title);
            photos.add(photo);
        }
        return photos;
    }


    @Override
    public boolean canRead(Class<?> clazz, org.springframework.http.MediaType mediaType) {
        return supports(clazz);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.equals(List_Photo.class);
    }

    private  static class FlickrPhotosWrapper {
        FlickrPhotos photos;

    }
    private  static class FlickrPhotos {
        List<FlickrPhoto> photo;

    }
   private static class FlickrPhoto {
        String title;
    }
}
