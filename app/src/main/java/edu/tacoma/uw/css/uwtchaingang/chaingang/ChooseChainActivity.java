package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.tacoma.uw.css.uwtchaingang.chaingang.chain.ChainContent;

public class ChooseChainActivity extends AppCompatActivity
        implements ChainListFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_chain);

        if(findViewById(R.id.chain_fragment_container) != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.chain_fragment_container, new ChainListFragment())
                    .commit();
        }
    }

    @Override
    public void onListFragmentInteraction(ChainContent.ChainItem item) {

    }
}
