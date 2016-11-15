package com.rf17.soundify.app.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.rf17.soundify.Soundify;
import com.rf17.soundify.app.adapter.MyRecyclerViewAdapter;
import com.rf17.soundify.app.model.Message;
import com.rf17.soundify.app.utils.AndroidUtils;
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

            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, 200);
            }

            soundify = new Soundify(this);
            soundify.startListening();
            soundify.setSoundifyListener((data) -> {
                String stringData = Soundify.bytesToString(data);
                if(stringData.length() < 50) {
                    Message message = new Message(stringData, sdf.format(new Date()));
                    recyclerViewAdapter.addItem(message);
                }
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
            byte[] bytes = Soundify.stringToBytes(msg);
            soundify.send(bytes);
        }catch (Exception e){
            AndroidUtils.showToast(this, e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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
