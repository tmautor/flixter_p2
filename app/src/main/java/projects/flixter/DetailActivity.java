package projects.flixter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;
import projects.flixter.models.Movie;

public class DetailActivity extends YouTubeBaseActivity
{
    public static final String YOUTUBE_API_KEY = "AIzaSyB9f2e14-0MLPTBgtsY1iywqIf_GRYbt68";
    public static final String MOVIE_VIDEO_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    ConstraintLayout movie_full_container;
    YouTubePlayerView movie_full_video_player;
    TextView movie_full_title;
    RatingBar movie_full_rating;
    TextView movie_full_overview;

    Movie movie_data;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movie_full_container = findViewById(R.id.MOVIE_FULL_CONTAINER);
        movie_full_video_player = findViewById(R.id.MOVIE_FULL_VIDEO_PLAYER);
        movie_full_title = findViewById(R.id.MOVIE_FULL_TITLE);
        movie_full_rating = findViewById(R.id.MOVIE_FULL_RATING);
        movie_full_overview = findViewById(R.id.MOVIE_FULL_OVERVIEW);

        movie_data = Parcels.unwrap(getIntent().getParcelableExtra("movie_data"));

        movie_full_title.setText(movie_data.getTitle());
        movie_full_rating.setRating((float)movie_data.getVote_average());
        movie_full_overview.setText(movie_data.getOverview());

        AsyncHttpClient ahc = new AsyncHttpClient();
        ahc.get(String.format(MOVIE_VIDEO_URL, movie_data.getId()), new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int i, Headers headers, JSON json)
            {
                try
                {
                    JSONArray results = json.jsonObject.getJSONArray("results");

                    if (results.length() > 0)
                    {
                        String movie_youtube_key = results.getJSONObject(0).getString("key");
                        Log.d("DetailActivity", "key retrieved successfully: " + movie_youtube_key);
                        init_youtube_player(movie_youtube_key);
                    }
                    else
                    {
                        Log.d("DetailActivity", "key not retrieved successfully.");
                        return;
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.d("DetailActivity", "JSON parse failure.");
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable)
            {
                Log.d("DetailActivity", "GET request failure.");
            }
        });
    }

    private void init_youtube_player(String movie_youtube_key)
    {
        movie_full_video_player.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener()
        {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b)
            {
                youTubePlayer.cueVideo(movie_youtube_key);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
            {
                Log.d("DetailActivity", youTubeInitializationResult.toString());
            }
        });
    }
}