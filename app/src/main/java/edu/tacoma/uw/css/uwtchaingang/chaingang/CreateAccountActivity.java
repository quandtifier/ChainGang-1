package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void gotoChooseChain(View view) {
        Intent intent = new Intent(this, ChooseChainActivity.class);
        startActivity(intent);
    }
}
