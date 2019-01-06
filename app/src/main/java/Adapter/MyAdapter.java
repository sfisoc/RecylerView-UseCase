package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gohool.myrv.myrecyclerview.R;

import java.util.ArrayList;
import java.util.List;

import Model.Country;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<Country> countries;

    public MyAdapter(Context context, List listItem) {
        this.context = context;
        this.countries = listItem;
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
        holder.name.setText(country.getName());
        holder.description.setText("Capital: "+ country.getCapital()+" Region: "+ country.getRegion());
        /**
         * TODO add image icon
         */

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
