package com.rf17.soundify.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.rf17.soundify.library.Soundify;
import com.rf17.soundifyapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private EditText messageSend;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Message> messages = new ArrayList<>();

    private Soundify soundify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            fab = (FloatingActionButton) findViewById(R.id.fab);

            soundify = new Soundify(this);
            soundify.setSoundifyListener(new Soundify.SoundifyListener() {
                @Override
                public void OnReceiveData(byte[] bytes) {
                    Message message = new Message("RF", Soundify.bytesToString(bytes));
                    messages.add(message);
                    getDataSet();
                }
            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog();
                }
            });

            mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MyRecyclerViewAdapter(getDataSet());
            mRecyclerView.setAdapter(mAdapter);

            // Code to Add an item with default animation
            //((MyRecyclerViewAdapter) mAdapter).addItem(obj, index);

            // Code to remove an item with default animation
            //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);

        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(viewGroup, "Error: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
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
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i("RecyclerView", " Clicked on Item " + position);
            }
        });
    }

    private ArrayList<Message> getDataSet() {
        return messages;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
