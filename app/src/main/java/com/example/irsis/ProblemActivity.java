package com.example.irsis;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.irsis.Fragment.ProblemNameFragment;
import com.example.irsis.adapter.ProblemAdapter;
import com.example.irsis.myclass.Problem;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class ProblemActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        Toolbar toolbar = findViewById(R.id.toolbar_problem);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(ProblemActivity.this, SubmitProblemActivity.class);
                startActivity(intent);
                break;
            case R.id.delete:
                Toast.makeText(this, "你点击了Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "你点击了Setting", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}
