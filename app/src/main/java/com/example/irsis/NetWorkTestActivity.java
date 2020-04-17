package com.example.irsis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.irsis.myclass.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetWorkTestActivity extends BaseActivity implements View.OnClickListener {

    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work_test);

        Button sendRequest =findViewById(R.id.send_request);
        sendRequest.setOnClickListener(this);

        responseText=findViewById(R.id.send_request);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.send_request){
            sendRequestWithOkHttp();
        }
    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client =new OkHttpClient();
                    Request request =new Request.Builder()
                            .url("http://10.0.2.2/user.json")
                            .build();
                    Response response =client.newCall(request).execute();
                    String responseData =response.body().string();
                    parseJSONWithGSON(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //解析方式
    //GSON
    private void parseJSONWithGSON(String jsonData){
        Gson gson=new Gson();
        List<User> userList=gson.fromJson(jsonData,new TypeToken<List<User>>(){}.getType());
        for (User user:userList){
            Log.d("tag","id="+user.getuId());
            Log.d("tag","name="+user.getUserName());
            Log.d("tag","account="+user.getUserAccount());
            Log.d("tag","password="+user.getUserPassword());
            Log.d("tag","limit="+user.getLimit());
        }
    }

    //jsonObject
    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONArray jsonArray=new JSONArray(jsonData);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String id= jsonObject.getString("id");
                String name= jsonObject.getString("name");
                String version= jsonObject.getString("version");
                Log.d("tag","id="+id);
                Log.d("tag","name="+name);
                Log.d("tag","version="+version);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void sendRequestWithHttpURLConnection(){
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try {
                    URL url =new URL("https://www.baidu.com");
                    connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in =connection.getInputStream();
                    //下面对获取到的输入流进行读取
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response =new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(reader!=null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示在界面上
                responseText.setText(response);
            }
        });
    }
}
