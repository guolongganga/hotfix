package com.fund.myhotfix;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.taobao.sophix.SophixManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="MainActivity" ;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //热修复前的代码
                Toast.makeText(MainActivity.this,"点击",Toast.LENGTH_LONG).show();
                //热修复之后新添加的代码  平台自动下发
                Toast.makeText(MainActivity.this, "success",Toast.LENGTH_LONG).show();
            }
        });
        SophixManager.getInstance().queryAndLoadNewPatch();

    }
    }

