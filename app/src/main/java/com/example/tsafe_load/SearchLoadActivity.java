package com.example.tsafe_load;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skt.Tmap.TMapData;

public class SearchLoadActivity extends AppCompatActivity implements RecyclerViewAdapterCallback {
    double longitude;
    double latitude;
    TMapData tmapdata = new TMapData();
    location startpoint;
    location endpoint;
    location result;
    EditText editText;
    EditText editText1;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    private final long DELAY = 500;
    int k=0;
    String original;
    String destination;
    RecyclerViewAdapter adapter1;

    public location look_location(String data) {

        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_road);

        editText = findViewById(R.id.original);
        editText1 = findViewById(R.id.destination);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        final Intent intent = new Intent();

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    recyclerView.setAdapter(adapter);
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String keyword = s.toString();
                k=1;
                adapter.filter(keyword);

            }
        });

        editText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    recyclerView.setAdapter(adapter1);
                }
            }
        });
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String keyword = s.toString();
                k=2;
                adapter1.filter(keyword); }
        });

        Button button = findViewById(R.id.search_road);//경로 검색
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                original = editText.getText().toString();
                destination = editText1.getText().toString();
                Log.d( adapter.return_to_value(0), original);
                if(original.equals(adapter.return_to_value(0)) && destination .equals(adapter1.return_to_value(0))){
                    startpoint = point(adapter.return_to_point());
                    endpoint = point(adapter1.return_to_point());

                    intent.putExtra("startpoint", startpoint);
                    intent.putExtra("endpoint",endpoint);
                }
                else {
                    Toast.makeText(getApplicationContext(), "검색값과 위치값이 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                }
                /*
                Intent intent = new Intent();
                intent.putExtra("start_address", CustomPoiList_start.address);
                intent.putExtra("start_lat", CustomPoiList_start.lat);
                intent.putExtra("start_lon", CustomPoiList_start.lon);
                intent.putExtra("end_address", CustomPoiList_end.address);
                intent.putExtra("end_lat", CustomPoiList_end.lat);
                intent.putExtra("end_lon", CustomPoiList_end.lon);
                setResult(200,intent);
                finish();*/
            }
        });

//        //Log.d(startpoint.latitude, startpoint.longitude);
//
//
//
//        setResult(RESULT_OK, intent);
//        finishActivity(1000);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerViewAdapter();
        adapter1 = new RecyclerViewAdapter();
        recyclerView.setLayoutManager(layoutManager);

        adapter.setCallback(this);
        adapter1.setCallback(this);
    }

    @Override
    public void showToast(int position) {
        Toast.makeText(this, "Position: " + position, Toast.LENGTH_LONG).show();
        if(k == 1) {
            editText.setText(adapter.return_to_value(position));
        }
        else if(k == 2) {
            editText1.setText(adapter1.return_to_value(position));
        }
    }

    public location point(String location) {
        String black[] = new String[4];
        black = location.split(" ");

        result = new location(black[1], black[3]);
        return result;
    }
}