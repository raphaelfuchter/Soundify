package com.rf17.soundify.app.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.rf17.soundify.Soundify;
import com.rf17.soundify.app.adapter.MyRecyclerViewAdapter;
import com.rf17.soundify.app.model.Message;
import com.rf17.soundify.app.utils.AndroidUtils;
import com.rf17.soundifyapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Soundify soundify;

    private EditText messageSend;
    private MyRecyclerViewAdapter recyclerViewAdapter;

    private ArrayList<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
            messageSend = (EditText) findViewById(R.id.etx_send);

            mRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            RecyclerView.Adapter mAdapter = new MyRecyclerViewAdapter(messages);
            mRecyclerView.setAdapter(mAdapter);

            recyclerViewAdapter = (MyRecyclerViewAdapter) mAdapter;

            soundify = new Soundify(this);
            soundify.startListening();
            soundify.setSoundifyListener((data) -> {
                String stringData = Soundify.bytesToString(data);
                Message message = new Message(stringData, 1);
                recyclerViewAdapter.addItem(message);
            });

            fab.setOnClickListener((view) -> {
                sendMessage();

                messageSend.setText("");
                AndroidUtils.hideKeyboard(this, view);
            });

            recyclerViewAdapter.setOnItemClickListener((position, view) -> recyclerViewAdapter.deleteItem(position));

        } catch (Exception e) {
            AndroidUtils.showToast(this, e);
        }
    }

    /**
     * Envia a mensagem
     */
    public void sendMessage() {
        try{
            String msg = messageSend.getText().toString();

            soundify.stopListening();
            soundify.send(Soundify.stringToBytes(msg));
            soundify.startListening();

            Message message = new Message(msg, 0);
            recyclerViewAdapter.addItem(message);

        }catch (Exception e){
            AndroidUtils.showToast(this, e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(soundify != null) {
            try {
                soundify.startListening();
            } catch (Exception e) {
                AndroidUtils.showToast(this, e);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            soundify.stopListening();
        } catch (Exception e) {
            AndroidUtils.showToast(this, e);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            soundify.stopListening();
        } catch (Exception e) {
            AndroidUtils.showToast(this, e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            soundify.stopListening();
        } catch (Exception e) {
            AndroidUtils.showToast(this, e);
        }
    }

}
