package tw.org.iii.android_json;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private MyAdapter myAdapter;
    private RequestQueue queue;
    private LinkedList<HashMap<String,String>> data;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Downloading.....");

        data = new LinkedList<>();
        queue = Volley.newRequestQueue(this);
        listView = findViewById(R.id.listView);
        initListView();
        fetchRemoteData();
    }

    private void fetchRemoteData() {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.GET,
                "http://data.coa.gov.tw/Service/OpenData/ODwsv/ODwsvTravelFood.aspx",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("DCH", response);
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("DCH", error.toString());
                        progressDialog.dismiss();
                    }
                });
        queue.add(request);
    }

    private void parseJSON(String json) {
        try {
            JSONArray root = new JSONArray(json);
            for (int i=0; i<root.length(); i++) {
                HashMap<String, String> dd = new HashMap<>();
                JSONObject row = root.getJSONObject(i);
                dd.put("ID", row.getString("ID"));
                dd.put("Name", row.getString("Name"));
                dd.put("Address", row.getString("Address"));
                dd.put("Tel", row.getString("Tel"));
                dd.put("HostWords", row.getString("HostWords"));
                dd.put("FoodFeature", row.getString("FoodFeature"));
                dd.put("Coordinate", row.getString("Coordinate"));
                dd.put("PucURL", row.getString("PicURL"));
                dd.put("Heart", "xx");
                data.add(dd);
            }
            myAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.v("DCH", e.toString());
        }
        progressDialog.dismiss();
    }

    private void initListView() {
        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this); //inflater 浮動視窗
            View view = inflater.inflate(R.layout.item, null);

            TextView name = view.findViewById(R.id.item_name);
            name.setText(data.get(position).get("Name"));

            TextView tel = view.findViewById(R.id.item_tel);
            tel.setText(data.get(position).get("Tel"));

            TextView address = view.findViewById(R.id.item_address);
            address.setText(data.get(position).get("Address"));

            ImageView heart = view.findViewById(R.id.item_heart);
            heart.setImageResource(data.get(position).get("Heart").equals("ok")?R.drawable.heart:R.drawable.heart_no);
            heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("DCH", "post" + position);
                    data.get(position).put("Heart", data.get(position).get("Heart").equals("ok")?"xx":"ok");
                    ((ImageView)v).setImageResource(data.get(position).get("Heart").equals("ok")?R.drawable.heart:R.drawable.heart_no);
                }
            });

            return view;
        }
    }
}
