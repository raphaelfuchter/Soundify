package com.rf17.soundify.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.rf17.soundify.app.adapter.MyRecyclerViewAdapter;
import com.rf17.soundify.app.model.Message;
import com.rf17.soundify.library.Soundify;
import com.rf17.soundifyapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText messageSend;

    private RecyclerView.Adapter mAdapter;

    private ArrayList<Message> messages = new ArrayList<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

    private Soundify soundify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

            mRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MyRecyclerViewAdapter(messages);
            mRecyclerView.setAdapter(mAdapter);

            soundify = new Soundify(this);
            soundify.startListening();
            soundify.setSoundifyListener(new Soundify.SoundifyListener() {
                @Override
                public void OnReceiveData(byte[] bytes) {
                    Message message = new Message(Soundify.bytesToString(bytes), sdf.format(new Date()));
                    ((MyRecyclerViewAdapter) mAdapter).addItem(message, 0);
                }

                @Override
                public void OnReceiveError(int code, String msg) {
                    Toast.makeText(MainActivity.this, "Error! Code: " + code + " Exception: " + msg, Toast.LENGTH_SHORT).show();
                }
            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     */
    public void showDialog() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.message)
                .customView(R.layout.dialog_send_customview, true)
                .positiveText(R.string.send)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        soundify.send(Soundify.stringToBytes(messageSend.getText().toString()));
                    }
                })
                .build();
        if(dialog.getCustomView() != null) {
            messageSend = (EditText) dialog.getCustomView().findViewById(R.id.etx_send);
        }
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(soundify != null) {
            soundify.startListening();
        }
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                ((MyRecyclerViewAdapter) mAdapter).deleteItem(position);

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundify.stopListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        soundify.stopListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundify.stopListening();
    }

}
