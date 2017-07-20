package com.desafiolatam.desafioface.views.login;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.desafiolatam.desafioface.R;
import com.desafiolatam.desafioface.services.RecentUsersService;
import com.desafiolatam.desafioface.views.main.MainActivity;


public class LoginActivity extends AppCompatActivity implements LoginCallback{

    private TextInputLayout emailTil, passwordTil;
    private EditText emailEt, passwordEt;
    private Button button;
    private IntentFilter intentFilter;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTil = (TextInputLayout) findViewById(R.id.emailTil);
        passwordTil = (TextInputLayout) findViewById(R.id.passwordTil);
        emailEt = (EditText) findViewById(R.id.emailEt);
        passwordEt = (EditText) findViewById(R.id.passwordEt);
        button = (Button) findViewById(R.id.signBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();
                emailTil.setVisibility(View.GONE);
                passwordTil.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                emailTil.setError(null);
                passwordTil.setError(null);
                new Login(LoginActivity.this).session(email, password);
            }
        });

        intentFilter = new IntentFilter();
        intentFilter.addAction(RecentUsersService.UPDATE_USERS);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (RecentUsersService.UPDATE_USERS.equals(intent.getAction()))
                {
                    Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private void restoreViews()
    {
        emailTil.setVisibility(View.VISIBLE);
        passwordTil.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
    }

    @Override
    public void success() {
        RecentUsersService.startActionUpdate(this);
       /* Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();*/
    }

    @Override
    public void emailFormat() {
        restoreViews();
        emailTil.setError("Email Incorrecto");
    }

    @Override
    public void requiredFields() {
        restoreViews();
        emailTil.setError("Requerido");
        passwordTil.setError("Requerido");
    }

    @Override
    public void credentials() {
        restoreViews();
        Toast.makeText(this, "Nombre de usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
    }
}