package org.c4q.androidpersistence;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity_2b_static_var extends ActionBarActivity {

    private EditText mUsername;
    private EditText mAge;
    private Button mCreateUser;
    private TextView mTvUserCount;
    private static int mUserCount;
    private List<User> userList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initializeData();

        intializeViews();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupEventListeners(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setupEventListeners(false);
    }

    private void initializeData() {
        userList = new ArrayList<>();
//       mUserCount = 0; //Test 1, non static, default initialized to 0
    }

    private void intializeViews(){
        mUsername = (EditText) findViewById(R.id.username);
        mAge = (EditText) findViewById(R.id.age);
        mCreateUser = (Button) findViewById(R.id.btnCreateUser);
        mTvUserCount = (TextView) findViewById(R.id.user_count);

        mTvUserCount.setText(mUserCount + "");
    }

    private void setupEventListeners(boolean setFlag) {

        if (!setFlag) {
            mCreateUser.setOnClickListener(null);

            return;
        }
        mCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mUsername.getText().toString();
                int age = Integer.parseInt(String.valueOf(mAge.getText())); // another way to get string from EditText

                User newUser = new User(username, age);
                userList.add(newUser);
                mUserCount++;
                mTvUserCount.setText(mUserCount + "");

            }
        });
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
