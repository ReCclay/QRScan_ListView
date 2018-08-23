package com.clay.qrscan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.android.CaptureActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SCAN = 0x0000;
    private static final String DECODED_CONTENT_KEY = "codedContent";

    AdapterDevices mAdapterDevices;
    ArrayList<ArrayList<Object>> mArrayList = new ArrayList<ArrayList<Object>>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listViewActiMain1);
        listView.setBackgroundColor(Color.WHITE);
        listView.setOnItemLongClickListener(ItemLongClickListener);//长按
        listView.setOnItemClickListener(OnItemClickListener);

        mAdapterDevices = new AdapterDevices(MainActivity.this, mArrayList);
        mAdapterDevices.setonItemSwitchClickListener(new AdapterDevices.onItemSwitchClickListener() {
            @Override
            public void onClick(View imageView, int index, String DeviceName, String isClick) {

            }
        });
        mAdapterDevices.setonItemSwitchClickListener(mItemSwitchClickListener);
        listView.setAdapter(mAdapterDevices);
        mAdapterDevices.notifyDataSetChanged();


    }

    private OnItemClickListener OnItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "您点击了"+arg2, 500).show();
        }
    };


    private OnItemLongClickListener ItemLongClickListener = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
            Toast.makeText(getApplicationContext(), "您长按了"+arg2, 500).show();
            return false;
        }
    };

    private AdapterDevices.onItemSwitchClickListener mItemSwitchClickListener = new AdapterDevices.onItemSwitchClickListener() {
        @Override
        public void onClick(View imageView, int index, String DeviceName, String isClick) {

            ArrayList<Object> arrayList = new ArrayList<Object>();
            if (isClick.equals("1")) {//气死我了，注意这里是字符串类型
                arrayList.add(0,DeviceName);
                arrayList.add(1,"0");//这里也是字符串类型
            }
            else {
                arrayList.add(0,DeviceName);
                arrayList.add(1,"1");
            }
            mArrayList.set(index, arrayList);
            mAdapterDevices.notifyDataSetChanged();
        }
    };



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {

            if (data != null) {
                String content = data.getStringExtra(DECODED_CONTENT_KEY);

                ArrayList<Object> arrayList = new ArrayList<Object>();
                arrayList.add(0,content);
                arrayList.add(1,"1");
                mArrayList.add(arrayList);
                mAdapterDevices.notifyDataSetChanged();
            }
        }
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

            Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SCAN);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
