package com.example.android.weatherapiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_CityID, btn_getWeatherByID, btn_weatherByName;
    EditText et_dataInput;
    ListView lv_WeatherReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assigns the values to each control on the layout


        btn_CityID = findViewById(R.id.btn_getCityID);
        btn_getWeatherByID = findViewById(R.id.btn_getwheatherByCityID);
        btn_weatherByName = findViewById(R.id.btn_getwheatherByCityName);

        et_dataInput = findViewById(R.id.et_dataInput);
        lv_WeatherReport = findViewById(R.id.lv_weatherReports);
       final WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);

        // click listener for each button
        btn_CityID.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                // this didnt returned anything
                weatherDataService.getCityID(et_dataInput.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this,"somtheing wrong", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String cityID) {
                        Toast.makeText(MainActivity.this,"returned an ID of " + cityID, Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });



        btn_getWeatherByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            weatherDataService.getCityForCastByID(et_dataInput.getText().toString(), new WeatherDataService.ForCastByIDResponse() {
                @Override
                public void onError(String message) {
                    Toast.makeText(MainActivity.this, "something wrong", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onResponse(List<WeatherReportModel> weatherReportModels) {
                    ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReportModels);
                    lv_WeatherReport.setAdapter(arrayAdapter);
                }
            });


            }

        });

        btn_weatherByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherDataService.getCityForCastByName(et_dataInput.getText().toString(), new WeatherDataService.getCityForCastByNameCallBack() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "something wrong", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReportModels);
                        lv_WeatherReport.setAdapter(arrayAdapter);
                    }
                });
            }
        });
    }
}