package com.cnakhn.faradarscompletion.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cnakhn.faradarscompletion.R;
import com.cnakhn.faradarscompletion.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class NetworkActivity extends AppCompatActivity {
    private static final String TAG = "NetworkActivity";
    private static final String URL = "https://cnadev.000webhostapp.com/my_site/files/sample.json";
    private TextView tvNetwork;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        tvNetwork = findViewById(R.id.tv_network);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Get Data (HTTP Client)").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Thread thread = new Thread(new Runnable() {
                    android.os.Handler handler = new Handler() {
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            super.handleMessage(msg);
                            String content = (String) msg.getData().get("content");
                            tvNetwork.setText(content);
                        }
                    };

                    @Override
                    public void run() {
                        String content = getDataHTTPClient();
                        android.os.Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("content", content);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                });
                thread.start();
                return false;
            }
        });

        menu.add("Get Data (Async Task)").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getDataAsyncTask();
                return false;
            }
        });

        menu.add("Reset").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                tvNetwork.setText("");
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private String getDataHTTPClient() { //Need Internet Permissions
        HttpClient client = new DefaultHttpClient();
        HttpGet method = new HttpGet(URL);
        try {
            HttpResponse response = client.execute(method);
            String content = Utils.inputStreamToString(response.getEntity().getContent());
            Log.i(TAG, "getData: " + content);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void getDataAsyncTask() {
        new TaskIntro("Task #" + (++counter))
        //.execute("Home", "Bank", "Code")
        .executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, "Code", "Bank", "Code");

    }

    public class TaskIntro extends AsyncTask<String, String, String> {
        String taskName = "";

        public TaskIntro(String taskName) {
            this.taskName = taskName;
        }

        @Override
        protected void onPreExecute() {
            tvNetwork.append(String.format("Starting %s...\n", taskName));
        }

        @Override
        protected String doInBackground(String... strings) {
            for (String p : strings) {
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(p);
            }

            return "Done!";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            tvNetwork.append(taskName + " " + values[0] + "\n");
        }

        @Override
        protected void onPostExecute(String s) {
            tvNetwork.append(taskName + " " + s + "\n");
        }
    }
}