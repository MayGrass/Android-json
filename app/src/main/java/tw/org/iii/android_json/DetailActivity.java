package tw.org.iii.android_json;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {
    private String id, name, addr, tel, hostwords, feature, latIng, picurl, heart;
    private ImageView img;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        HashMap<String,String> row = (HashMap<String, String>)(intent.getSerializableExtra("data"));
        //Log.v("DCH", row.get("Name"));

        id = row.get("ID");
        name = row.get("Name");
        addr = row.get("Address");
        tel = row.get("Tel");
        hostwords = row.get("HostWords");
        feature = row.get("FoodFeature");
        latIng = row.get("Coordinate");
        picurl = row.get("PicURL");

        img = findViewById(R.id.detail_img);
        content = findViewById(R.id.detail_Content);

        fetchRemoteImage();
    }

    private void fetchRemoteImage() {
        ImageRequest request = new ImageRequest(picurl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                img.setImageBitmap(response);
            }
        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("DCH", error.toString());
            }
        });
        MainApp.queue.add(request);
    }

}
