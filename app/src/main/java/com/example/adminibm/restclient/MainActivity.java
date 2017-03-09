package com.example.adminibm.restclient;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Context context ;
    final String BASE_URL = "http://movieservice20170309124049.azurewebsites.net/api/movie/";


    public interface VolleyCallback {
        void onSuccessResponse(JSONArray result);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this.getBaseContext();

        Button getAll = (Button) findViewById(R.id.get_all);
        Button getKeyword = (Button) findViewById(R.id.getKeyword);

        final TextView getAllResponse = (TextView) findViewById(R.id.allResult);
        final TextView getKeywordResponse = (TextView) findViewById(R.id.keyWordResponse);

        final EditText enterKeyword = (EditText) findViewById(R.id.keywordText);

        getAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callApi(new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(JSONArray result) {
                        try {
                            getAllResponse.setText("");
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject j = (JSONObject) result.get(i);
                                getAllResponse.append(j.getString("Title") + "\n");
                            }
                        } catch (JSONException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                });

            }
        });

        getKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callApiKeyWord(new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(JSONArray result) {
                        try {
                            getKeywordResponse.setText("");
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject j = (JSONObject) result.get(i);
                                getKeywordResponse.append(j.getString("title") + "\n");
                            }
                        } catch (JSONException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }, enterKeyword.getText().toString());

            }
        });
    }

    public void callApi(final VolleyCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                BASE_URL + "all",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        callback.onSuccessResponse(response);
                    }
                }
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });
        queue.add(jsonObjectRequest);
    }

    public void callApiKeyWord(final VolleyCallback callback, String keyword) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                BASE_URL + "keyword/" + keyword,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        callback.onSuccessResponse(response);
                    }
                }
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });
        queue.add(jsonObjectRequest);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
