package bhanupro.volleyexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String JSON_URL = "https://www.simplifiedcoding.net/demos/view-flipper/heroes.php";

    RecyclerView rv;
    //ProgressBar mProgressBar;
    List<Hero> heroList = new ArrayList<>();
    HeroAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.recycler_view);
        //mProgressBar = findViewById(R.id.progress_bar);

        loadData();
        mAdapter = new HeroAdapter(heroList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(manager);
        rv.setAdapter(mAdapter);
        
    }
    public void loadData(){
        //mProgressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // mProgressBar.setVisibility(View.GONE);

                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("heroes");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject heroObj = array.getJSONObject(i);
                                Hero hero = new Hero(heroObj.getString("name"), heroObj.getString("imageurl"));
                                heroList.add(hero);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }
}
