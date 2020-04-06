package com.example.irsis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.irsis.Fragment.RightFragment;
import com.example.irsis.Fragment.RightFragment1;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
        //replaceFragment(new RightFragment());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                //replaceFragment(new RightFragment1());
                break;
            default:
                break;
        }
    }

//    private void replaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.right_fragment_layout, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
}
