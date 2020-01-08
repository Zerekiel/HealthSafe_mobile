package com.eipteam.healthsafe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eipteam.healthsafe.network.MyJSONReq;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class Profile extends AppCompatActivity {
    private OkHttpClient client = new OkHttpClient();
    private Integer code = -2;
    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        try {
            getRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (code == -2);

        if (code == 200) {
//            try {
                /*JSONObject getUser = new JSONObject(msg);

                String lastName = getUser.getString("LastName");
                String firstName = getUser.getString("FirstName");
                String number = getUser.getString("Number");*/

                TextView last = findViewById(R.id.lastName);
                TextView first = findViewById(R.id.firstName);
                TextView num = findViewById(R.id.number);

                last.setText("Deprost");
                first.setText("Clément");
                num.setText("666");
/*           } catch () {
                e.printStackTrace();
            }*/
        }
    }

    private void getRequest() throws IOException {

        String url = getResources().getString(R.string.profile);
        client.newCall(new MyJSONReq().getRequest(url)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                code = -1;
                msg = e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                code = response.code();
                msg = response.body().string();
            }
        });
    }

    public void returnChoose(View view) {
        finish();
    }
}
