package tw.org.iii.android_json;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {
    private String id, name, addr, tel, hostwords, feature, latIng, picurl, heart;
    private ImageView imageView;
    private TextView textView;

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

        imageView = findViewById(R.id.detail_img);
        textView = findViewById(R.id.detail_Content);
    }
}
