package projects.flixter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import projects.flixter.adapters.MovieAdapter;
import projects.flixter.models.Movie;

public class MainActivity extends AppCompatActivity {

    public static final String SAMPLE_REQUEST = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movies = new ArrayList<Movie>();

        //create recycler view as the movie recycler view from item_movie.xml
        RecyclerView rv_movies = findViewById(R.id.RV_MOVIES);
        //add an instance of the movie adapter to the recycler view
        MovieAdapter ma = new MovieAdapter(this, movies);
        rv_movies.setAdapter(ma);
        //create a simple linear layout manager for the recycler view
        rv_movies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient ahc = new AsyncHttpClient();

        ahc.get(SAMPLE_REQUEST, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int i, Headers headers, JSON json)
            {
                Log.d(TAG, "onSuccess");
                JSONObject response_json = json.jsonObject;

                try
                {
                    JSONArray response_array = response_json.getJSONArray("results");
                    Log.i(TAG, "Response data: " + response_array.toString());

                    movies.addAll(Movie.jtml(response_array));
                    ma.notifyDataSetChanged();
                    Log.i(TAG, "Movies: " + movies.size());
                }
                catch (JSONException e)
                {
                    Log.e(TAG, "Error parsing JSON response with key \"results\"", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable)
            {
                Log.d(TAG, "onFailure");
            }
        });
    }
}