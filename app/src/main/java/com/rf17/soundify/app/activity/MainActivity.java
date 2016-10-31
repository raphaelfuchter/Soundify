package com.rf17.soundify.app.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.rf17.soundify.Soundify;
import com.rf17.soundify.app.adapter.MyRecyclerViewAdapter;
import com.rf17.soundify.app.model.Message;
import com.rf17.soundify.utils.DebugUtils;
import com.rf17.soundifyapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Soundify soundify;

    private EditText messageSend;
    private MyRecyclerViewAdapter recyclerViewAdapter;

    private ArrayList<Message> messages = new ArrayList<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

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
                Message message = new Message(stringData, sdf.format(new Date()));
                recyclerViewAdapter.addItem(message, 0);
            });

            fab.setOnClickListener((view) -> sendMessage());

            recyclerViewAdapter.setOnItemClickListener((position, view) -> recyclerViewAdapter.deleteItem(position));

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     */
    public void sendMessage() {
        try{
            DebugUtils.log("msg: "+messageSend.getText().toString());
            soundify.send(Soundify.stringToBytes(messageSend.getText().toString()));
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(soundify != null) {
            try {
                soundify.startListening();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            soundify.stopListening();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            soundify.stopListening();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            soundify.stopListening();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
