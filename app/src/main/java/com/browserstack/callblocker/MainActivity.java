package com.browserstack.callblocker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int ROLE_ACQUIRE_REQUEST_CODE = 4378;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!isRedirection())
            roleAcquire(RoleManager.ROLE_CALL_REDIRECTION);

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public boolean isRedirection() {
        return isRoleHeldByApp(RoleManager.ROLE_CALL_REDIRECTION);
    }

    public boolean isRoleHeldByApp(String roleName) {
        RoleManager roleManager;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            roleManager = (RoleManager) getSystemService(Context.ROLE_SERVICE);
            return roleManager.isRoleHeld(roleName);
        }
        return false;
    }

    public void roleAcquire(String roleName) {
        RoleManager roleManager;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (roleAvailable(roleName)) {
                roleManager = (RoleManager) getSystemService(Context.ROLE_SERVICE);
                Intent intent = roleManager.createRequestRoleIntent(roleName);
                startActivityForResult(intent, ROLE_ACQUIRE_REQUEST_CODE);
            } else {
                Toast.makeText(this, "Redirection call with role in not available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean roleAvailable(String roleName) {
        RoleManager roleManager;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            roleManager = (RoleManager) getSystemService(Context.ROLE_SERVICE);
            return roleManager.isRoleAvailable(roleName);
        }
        return false;
    }

}