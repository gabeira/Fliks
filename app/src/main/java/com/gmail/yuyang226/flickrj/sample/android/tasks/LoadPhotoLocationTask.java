/**
 * 
 */
package com.gmail.yuyang226.flickrj.sample.android.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

import com.gmail.yuyang226.flickrj.sample.android.FlickrHelper;
import com.gmail.yuyang226.flickrj.sample.android.images.LazyAdapter;
import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.oauth.OAuth;
import com.googlecode.flickrjandroid.oauth.OAuthToken;
import com.googlecode.flickrjandroid.people.User;
import com.googlecode.flickrjandroid.photos.PhotoList;
import com.googlecode.flickrjandroid.photos.SearchParameters;

import java.util.HashSet;
import java.util.Set;

public class LoadPhotoLocationTask extends AsyncTask<OAuth, Void, PhotoList> {

	/**
	 *
	 */
	private ListView listView;
	private Activity activity;

	/**
	 * @param activity
	 */
	public LoadPhotoLocationTask(Activity activity,
                                 ListView listView) {
		this.activity = activity;
		this.listView = listView;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected PhotoList doInBackground(OAuth... arg0) {
		OAuthToken token = arg0[0].getToken();
//		Flickr f = FlickrHelper.getInstance().getFlickrAuthed(token.getOauthToken(), token.getOauthTokenSecret());
        Flickr f = FlickrHelper.getInstance().getFlickr();
		Set<String> extras = new HashSet<String>();
		extras.add("url_sq"); //$NON-NLS-1$
		extras.add("url_l"); //$NON-NLS-1$
		extras.add("views"); //$NON-NLS-1$
//		User user = arg0[0].getUser();
		try {
            //https://www.flickr.com/services/api/flickr.photos.search.html

            SearchParameters searchParameters = new SearchParameters();
//            searchParameters.setLatitude("-3.1161443");
//            searchParameters.setLongitude("-59.966028");
            searchParameters.setText("a");
            searchParameters.setLatitude("-3.1342762");
            searchParameters.setLongitude("-59.9783502");
            searchParameters.setMedia("photos");
//            searchParameters.setInCommons(true);
            searchParameters.setRadius(5);
//            searchParameters.setAccuracy(1);
            searchParameters.setRadiusUnits("km");
            searchParameters.setHasGeo(true);
            searchParameters.setSort(6);
//            searchParameters.setMachineTagMode();
			return f.getPhotosInterface().search(searchParameters, 500, 1);
//            return f.getPeopleInterface().getPhotos(user.getId(), extras, 20, 1);
//            return f.getPhotosInterface().getWithGeoData().getPhotos("72157625706540520", extras, 0, 20, 1).getPhotoList();

        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(PhotoList result) {
		if (result != null) {
			LazyAdapter adapter = 
					new LazyAdapter(this.activity, result);
			this.listView.setAdapter(adapter);
		}
	}
	
}