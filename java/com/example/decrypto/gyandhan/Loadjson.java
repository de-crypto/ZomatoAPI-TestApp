package com.example.decrypto.gyandhan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Loadjson extends AppCompatActivity {

    private ProgressDialog pDialog;
    private String TAG = Loadjson.class.getSimpleName();
    private static String APIkey = "528161af4e40b29e14c07ea119e80214";
    List<RowItem> categoryList;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadjson);

        setTitle("Loaded JSON ListView");

        //Displaying Back Button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        categoryList = new ArrayList<RowItem>();
        lv = (ListView) findViewById(R.id.list);

        try {

            // CALL GetText method to make post method call
            new GetCategory().execute();
        } catch (Exception ex) {
            Toast.makeText(this,"URL Exception",Toast.LENGTH_LONG).show();
            GotoMainActivity();
        }

    }

    // Create GetText Metod
    protected class GetCategory extends AsyncTask<Void, Void, Void> {
        // Create data variable for sent values to server
        /*JSONObject jsonParam = new JSONObject();
        JSONObject mainjson = new JSONObject();
        */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Loadjson.this);
            pDialog.setMessage("Request is Under Process.....");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            JSONObject jsonParam = new JSONObject();
            try {
                jsonParam.put("user-key", APIkey);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://developers.zomato.com/api/v2.1/categories";
            String str = jsonParam.toString();
            String jsonStr = sh.excutePost(url,str);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {

                try {
                    //getting JSON Object "c"
                    JSONObject jsonObject = new JSONObject(jsonStr);


                    if(jsonObject!=null){
                        //get the json array
                        JSONArray jsonArray=jsonObject.getJSONArray("categories");
                        if(jsonArray!=null){
                            //iterate your json array
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject cobject=jsonArray.getJSONObject(i);
                                String name,id;
                                JSONObject object = cobject.getJSONObject("categories");
                                name = object.getString("name");
                                id = object.getString("id");
                                RowItem item=new RowItem(id,name);
                                categoryList.add(item);
                            }
                        }
                    }
                }
                catch(JSONException ex){
                    ex.printStackTrace();
                }
            }
            else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            CustomListViewAdapter adapter = new CustomListViewAdapter(
                    Loadjson.this, R.layout.list_item, categoryList);

            lv.setAdapter(adapter);
            Toast.makeText(Loadjson.this,"URL : https://developers.zomato.com/api/v2.1/categories ",Toast.LENGTH_LONG).show();
        }
    }

    public void GotoMainActivity(){
        Intent j = new Intent(this, MainActivity.class);
        j.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(j);
        this.finish();
        // overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            GotoMainActivity();
            return true;
        }
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                GotoMainActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
