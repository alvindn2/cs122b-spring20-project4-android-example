package edu.uci.ics.fabflixmobile;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Login extends ActionBarActivity {

    private EditText username;
    private EditText password;
    private TextView message;
    private Button login;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // upon creation, inflate and initialize the layout
        setContentView(R.layout.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        message = findViewById(R.id.message);
        login = findViewById(R.id.login);
        /**
         * In Android, localhost is the address of the device or the emulator.
         * To connect to your machine, you need to use the below IP address
         * **/
        url = "https://10.0.2.2:8443/cs122b-spring20-project2-login-cart-example/api/";

        //assign a listener to call a function to handle the user request when clicking a button
        login.setOnClickListener(view -> login());
    }

    public void login() {

        message.setText("Trying to login");
        //request type is POST
        final StringRequest loginRequest = new StringRequest(Request.Method.POST, url + "login", response -> {
            //todo here should parse the json result and handle every response
            Log.d("login.success", response);
            //initialize the activity(page)/destination
            Intent listPage = new Intent(Login.this, ListViewActivity.class);
            //without starting the activity/page, nothing would happen
            startActivity(listPage);
        },
                error -> {
                    // error
                    Log.d("login.error", error.toString());
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Post request form data
                final Map<String, String> params = new HashMap<>();
                params.put("username", username.getText().toString());
                params.put("password", password.getText().toString());

                return params;
            }
        };
        // Use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        // !important: queue.add is where the login request is actually sent
        queue.add(loginRequest);

    }
}
