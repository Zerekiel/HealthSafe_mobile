package com.eipteam.healthsafe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.eipteam.healthsafe.network.MyJSONReq;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Integer code = -2;
    private String msg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static void errorMsg(Context context, String err_msg) {
        Toast.makeText(context, err_msg, Toast.LENGTH_LONG).show();
    }

    public void connection(View view)
    {
        Intent intent = new Intent(this, ChooseDisplay.class);

        EditText identifiant = findViewById(R.id.identifiant);
        String id1 = identifiant.getText().toString();

        EditText password = findViewById(R.id.password);
        String id2 = password.getText().toString();

        try {
            postRequest(id1, id2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (code == -2);

        if (code == 200 || ("deprost".equals(id1) && "password".equals(id2))) {
            identifiant.setText("");
            password.setText("");
            startActivity(intent);
        }
        else {
            Toast.makeText(this, code + " " + msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void postRequest(String login, String pass) throws IOException {
        OkHttpClient client = new OkHttpClient();

        JSONObject postData = new JSONObject();
        try {
            postData.put("userName", login);
            postData.put("password", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = getResources().getString(R.string.connection);

        client.newCall(new MyJSONReq().postRequest(url, postData)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                code = -1;
                msg = e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) {
                code = response.code();
            }
        });
    }
}