package com.gohool.myrv.myrecyclerview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import Adapter.MyAdapter;
import Model.Country;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private RecyclerView reciclerView;
    private RecyclerView.Adapter adapter;
    private List<Country> countries;
    private List<Drawable> imagesDrwable;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reciclerView = (RecyclerView) findViewById(R.id.reciclerView);
        reciclerView.setHasFixedSize(true);
        //every item has a fixed size
        reciclerView.setLayoutManager(new
                LinearLayoutManager(this));

        context = this;

        getCountries();


    }


    private ArrayList<Country> getCountries()
    {
        try
        {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://restcountries.eu/rest/v2/all")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    } else {
                        // do something wih the result


                       // upDateListView(parseCountriesJSON(response.body().string()));

                         final ArrayList<Country> jsonCountries = parseCountriesJSON(response.body().string());



                                // Stuff that updates the UI
                        MainActivity.this.countries = jsonCountries;

                        new DownloadImageTask().execute(MainActivity.this.countries);



                    }
                }

            });
        }
        catch (Exception e)
        {
            Log.d(TAG,"DowloadFialed");
        }

        return null;
    }

    private ArrayList<Country> parseCountriesJSON(String jason)
    {
        ArrayList<Country> conutriesItems = new ArrayList<>();

        try
        {
            JSONArray contacts = new JSONArray(jason);

            for (int i = 0; i < contacts.length(); i++) {

                JSONObject c = contacts.getJSONObject(i);

                String name = c.getString("name");
                String capital = c.getString("capital");
                String region = c.getString("region");
                String flag = c.getString("flag");

                Country pc = new Country(name,flag,capital,region);

                conutriesItems.add(pc);

            }
        }
        catch (Exception e)
        {
            Log.d(TAG,"Json parsing failed");
        }

        return conutriesItems;

    }

    private class DownloadImageTask extends AsyncTask<List<Country>, Void, List<Drawable>> {

        public DownloadImageTask() {
        }

        protected List<Drawable> doInBackground(List<Country >... urls) {

            List<Country> coutryItems = urls[0];

            List<Drawable> mIcon11 = new ArrayList<>();
            try {
                for (Country c: coutryItems
                        ) {

                    InputStream in = new java.net.URL(c.getFlag()).openStream();

                    if(in != null)
                    {
                        SVG svg = SVGParser.getSVGFromInputStream(in);

                        if(svg != null)
                        {
                            mIcon11.add(svg.createPictureDrawable());

                        }
                        else
                        {
                            Log.d(TAG,"Null SVG");
                        }

                        svg = null;
                        

                    }
                    else
                    {
                        Log.d(TAG,"NULL INPUTSTREAM");
                    }



                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(final List<Drawable> result) {

            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    // Stuff that updates the UI
                    MainActivity.this.imagesDrwable = result;


                    if(MainActivity.this.countries != null && MainActivity.this.imagesDrwable != null)
                    {
                        adapter = new MyAdapter(context, MainActivity.this.countries,MainActivity.this.imagesDrwable);

                        reciclerView.setAdapter(adapter);
                    }

                }
            });
        }
    }
}
