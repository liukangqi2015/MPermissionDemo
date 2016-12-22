package com.liu.mpermissiondemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button start_permission_btn,start_user_PermissionsDispatcher_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intitView();
        setListener();
    }

    private void intitView() {
        start_permission_btn= (Button) findViewById(R.id.start_permission_btn);
        start_user_PermissionsDispatcher_btn= (Button) findViewById(R.id.start_user_PermissionsDispatcher_btn);
    }

    private void setListener() {
        start_permission_btn.setOnClickListener(this);
        start_user_PermissionsDispatcher_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_permission_btn:
                startActivity(new Intent(this,PermissionActivity.class));
                break;
            case R.id.start_user_PermissionsDispatcher_btn:
                startActivity(new Intent(this,PermissionsDispatcherActivity.class));
                break;
            default:
                break;
        }
    }
}
