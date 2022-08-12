package com.cain.androidpro.net;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cain.androidpro.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "OkHttpActivity";
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        findViewById(R.id.btn_okhttp_synchronous_get).setOnClickListener(this);
        findViewById(R.id.btn_okhttp_asynchronous_get).setOnClickListener(this);
        findViewById(R.id.btn_okhttp_posting_string).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_okhttp_synchronous_get:
                synchronousGet();
                break;
            case R.id.btn_okhttp_asynchronous_get:
                asynchronousGet();
                break;
            case R.id.btn_okhttp_posting_string:
                postingString();
                break;
            default:
                break;
        }
    }

    /**
     * post提交字符串
     */
    private void postingString() {
        Log.d(TAG, "postingString: into");
        String postBody = ""
                          + "Releases\n"
                          + "--------\n"
                          + "\n"
                          + " * _1.0_ May 6, 2013\n"
                          + " * _1.1_ June 15, 2013\n"
                          + " * _1.2_ August 11, 2013\n";
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))//仅仅是多加了一个post数据部分，其他部分跟get请求获取数据一样
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e(TAG, "postingString#onFailure: error msg: " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    Log.d(TAG, "postingString#onResponse: response is successful.");
                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        Log.d(TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    Log.d(TAG, "postingString#onResponse: response body = " + responseBody.string());
                    //System.out.println(responseBody.string());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "postingString#onResponse: error msg: " + e.getLocalizedMessage());
                }
            }
        });
    }

    /**
     * 异步请求
     */
    private void asynchronousGet() {
        Log.d(TAG, "asynchronousGet: into");
        Request request = new Request.Builder().url("https://trendings.herokuapp.com").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e(TAG, "asynchronousGet#onFailure: error msg: " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    Log.d(TAG, "asynchronousGet#onResponse: response is successful.");
                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        Log.d(TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    Log.d(TAG, "asynchronousGet#onResponse: response body = " + responseBody.string());
                    //System.out.println(responseBody.string());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "asynchronousGet#onResponse: error msg: " + e.getLocalizedMessage());
                }
            }
        });
    }

    /**
     * 同步Get请求
     */
    private void synchronousGet() {
        // 需要在子线程运行
        new Thread(() -> {
            // 首先构建请求
            Request request = new Request.Builder().url("https://trendings.herokuapp.com").build();
            // 客户端client发起请求
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                Log.d(TAG, "synchronousGet: response is successful.");
                // 解析响应头数据
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    Log.d(TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                Log.d(TAG, "response body: " + response.body());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "synchronousGet: " + e.getLocalizedMessage());
            }
        }).start();
    }
}