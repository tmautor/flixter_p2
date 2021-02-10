package projects.flixter.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.List;

import projects.flixter.DetailActivity;
import projects.flixter.R;
import projects.flixter.models.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>
{
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context c, List<Movie> m)
    {
        this.context = c;
        this.movies = m;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Movie m = movies.get(position);
        holder.bind(m);
    }

    @Override
    public int getItemCount()
    {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ConstraintLayout movie_container;
        ImageView movie_poster;
        TextView movie_title;
        TextView movie_overview;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            movie_container = itemView.findViewById(R.id.MOVIE_ENTRY_CONTAINER);
            movie_poster = itemView.findViewById(R.id.MOVIE_POSTER);
            movie_title = itemView.findViewById(R.id.MOVIE_TITLE);
            movie_overview = itemView.findViewById(R.id.MOVIE_OVERVIEW);
        }

        public void bind(Movie m)
        {
            Glide.with(context).load(m.getPoster_path()).into(movie_poster);
            movie_title.setText(m.getTitle());
            movie_overview.setText(m.getOverview());

            movie_container.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(context, DetailActivity.class);

                    i.putExtra("movie_data", Parcels.wrap(m));

                    context.startActivity(i);
                }
            });
        }
    }
}
