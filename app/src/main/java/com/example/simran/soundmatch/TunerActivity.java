package com.example.simran.soundmatch;

import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TunerActivity extends AppCompatActivity {
    private static final String TAG = TunerActivity.class.getSimpleName();
    private TunerFragment tunerFragment;
    public static List<MusicItem> wListMusic = new ArrayList<MusicItem>();
    private boolean showCancel;
    private String data = null;
    private JSONArray parts = null;

    private class FetchDataTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONArray doInBackground(String... strings) {

            String url = strings[0];
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                data = response.body().string();
            } catch (IOException e) {
                Log.e("RESPONSE", "Response error", e);
            }

            try {
                JSONObject jsonObj = new JSONObject(data);
                parts = jsonObj.getJSONArray("parts");
            } catch (JSONException e) {
                Log.e("ORDERS_JSON", "JSON Error while obtaining orders", e);
            }

            return parts;
        }

        @Override
        protected void onPostExecute(JSONArray parts) {
            try {
                JSONArray FirstArray = parts.getJSONArray(0);
                int length = FirstArray.length();

                for(int i = 0; i < length; ++i) {
                    try {
                        JSONObject noteInfo = FirstArray.getJSONObject(i);
                        MusicItem wMusicItem = new MusicItem();
                        wMusicItem.note = noteInfo.getString("noteName");
                        wMusicItem.freq = noteInfo.getDouble("freq");
                        wListMusic.add(wMusicItem);
                    } catch (JSONException E) {
                        throw new RuntimeException(E);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        new FetchDataTask().execute("https://enigmatic-bayou-80047.herokuapp.com/");
        showCancel = false;
        setContentView(R.layout.tuner_activity);
        tunerFragment = new TunerFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, tunerFragment, TunerFragment.TAG).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();
        if(showCancel) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }else{
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch(requestCode){
            case TunerFragment.AUDIO_PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(tunerFragment != null) {
                        tunerFragment.init();
                    }
                }else if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    Toast.makeText(TunerActivity.this, "SoundMatch needs access to the microphone to function.", Toast.LENGTH_LONG).show();
                    TunerActivity.this.finish();
                }
                break;
        }
    }


}

