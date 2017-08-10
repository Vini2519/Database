package com.example.lenovo.database;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ArrayList<Items> actorsList;
    SQLiteDB dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba = new SQLiteDB(null);

        actorsList = new ArrayList<Items>();

        new JSONAsyncTask().execute("http://13.59.128.132:8080/foodcourt/fooditem/list?foodstall=22");

    }


    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... url) {
            try {
                //------------------>>
                HttpGet httppost = new HttpGet(url[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    dba.open();
                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("foodItems");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        Items actor = new Items();

                        try {
                            ContentValues values = new ContentValues();
                            actor.setName(object.getString("id"));
                            values.put(SQLiteDB.KEY_ID,object.getString("id"));
                            actor.setCusines(object.getString("name"));
                            values.put(SQLiteDB.KEY_NAME, object.getString("name"));
                            actor.setStallno(object.getString("basePrice"));
                            values.put(SQLiteDB.KEY_PHONE, object.getString("basePrice"));
                            actor.setRate(object.getString("takeawayPrice"));
                            values.put(SQLiteDB.KEY_CUSINE, object.getString("takeawayPrice"));
                            actor.setPhone(object.getString("maxQuantity"));
                            values.put(SQLiteDB.KEY_RATE, object.getString("maxQuantity"));
                            actor.setId(object.getString("cusine"));
                            values.put(SQLiteDB.KEY_STALLNO, object.getString("cusine"));
                            dba.insert(values);
                            } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Do not fetch data from server", Toast.LENGTH_LONG).show();

                        }
                        actorsList.add(actor);
                    }
                    dba.close();
                    return true;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
        }
    }
}

