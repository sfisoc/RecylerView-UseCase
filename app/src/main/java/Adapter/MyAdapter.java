package Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gohool.myrv.myrecyclerview.R;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Model.Country;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<Country> countries;
    private List<Drawable> imagesDrwable;
    private  boolean imagesLoaded = false;

    public MyAdapter(Context context, List listItem, List<Drawable> drawables) {
        this.context = context;
        this.countries = listItem;
        this.imagesDrwable = drawables;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new ViewHolder(v, context, (ArrayList<Country>) countries);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Country country = countries.get(position);
        Drawable d = imagesDrwable.get(position);
        holder.name.setText(country.getName());
        holder.description.setText("Capital: "+ country.getCapital()+" Region: "+ country.getRegion());

        /**
         * TODO add image icon
         */
        try {
            
            if(d != null)
            {
                holder.pic.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                holder.pic.setImageDrawable(d);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView description;
        public ImageView pic;

        public ViewHolder(View view, Context ctx, ArrayList<Country> items) {
            super(view);
            countries = items;
            //get the Activity Context
            context = ctx;

            view.setOnClickListener(this);

            name = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            pic = (ImageView) view.findViewById(R.id.pic);

        }

        @Override
        public void onClick(View v) {
            //Get int position
            int position = getAdapterPosition();
            Country item = countries.get(position);
          //  Intent intent = new Intent(context, MyActivity.class);
            Toast.makeText(context, item.getName(), Toast.LENGTH_LONG).show();
        }
    }
}
