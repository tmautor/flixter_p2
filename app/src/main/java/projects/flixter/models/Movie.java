package projects.flixter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie
{
    static String image_prefix = "https://image.tmdb.org/t/p/w342/%s";

    boolean adult;
    String backdrop_path;
    int[] genre_ids;
    int id;
    String original_language;
    String original_title;
    String overview;
    double popularity;
    String poster_path;
    String release_date;
    String title;
    boolean video;
    double vote_average;
    int vote_count;

    //empty constructor needed for Parceler library
    public Movie(){}

    public Movie(JSONObject j) throws JSONException
    {
        this.adult = j.getBoolean("adult");
        this.backdrop_path = j.getString("backdrop_path");

        JSONArray genre_ids_ja = j.getJSONArray("genre_ids");
        this.genre_ids = new int[genre_ids_ja.length()];
        for(int x = 0; x < genre_ids_ja.length(); ++x)
        {
            this.genre_ids[x] = genre_ids_ja.optInt(x);
        }

        this.id = j.getInt("id");
        this.original_language = j.getString("original_language");
        this.original_title = j.getString("original_title");
        this.overview = j.getString("overview");
        this.popularity = j.getDouble("popularity");
        this.poster_path = j.getString("poster_path");
        this.release_date = j.getString("release_date");
        this.title = j.getString("title");
        this.video = j.getBoolean("video");
        this.vote_average = j.getDouble("vote_average");
        this.vote_count = j.getInt("vote_count");
    }

    public boolean isAdult()
    {
        return this.adult;
    }

    public String getBackdrop_path()
    {
        return this.backdrop_path;
    }

    public int[] getGenre_ids()
    {
        return this.genre_ids;
    }

    public int getId()
    {
        return this.id;
    }

    public String getLanguage()
    {
        return this.original_language;
    }

    public String getOriginal_title()
    {
        return this.original_title;
    }

    public String getOverview()
    {
        return this.overview;
    }

    public double getPopularity()
    {
        return this.popularity;
    }

    public String getPoster_path()
    {
        return String.format(image_prefix, this.poster_path);
    }

    public String getRelease_date()
    {
        return this.release_date;
    }

    public String getTitle()
    {
        return this.title;
    }

    public boolean isVideo()
    {
        return this.video;
    }

    public double getVote_average()
    {
        return this.vote_average;
    }

    public int getVote_count()
    {
        return this.vote_count;
    }

    public static List<Movie> jtml(JSONArray ja) throws JSONException
    {
        List<Movie> m = new ArrayList<Movie>();

        for(int x = 0; x < ja.length(); ++x)
        {
            m.add(new Movie(ja.getJSONObject(x)));
        }

        return m;
    }
}
