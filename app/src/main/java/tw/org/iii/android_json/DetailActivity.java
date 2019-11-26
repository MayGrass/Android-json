package tw.org.iii.android_json;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        HashMap<String,String> row = (HashMap<String, String>)(intent.getSerializableExtra("data"));
        Log.v("DCH", row.get("Name"));
    }
}
