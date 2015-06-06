package org.c4q.androidpersistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity_internal_storage extends ActionBarActivity {

    private EditText mUsername;
    private EditText mAge;
    private Button mCreateUser;
    private TextView mTvUserCount;
    private int mUserCount;
    private List<User> mUserList = null;
    private SharedPreferences mSharedPreferences;
    public static final String PREFERENCE_FILE = "org.c4q.androidPersistence_3";
    public static final String PREF_KEY_USER_COUNT = "user_count";
    public static final String INTERNAL_USER_LIST_FILE = "org.c4q.internal_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = this.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);

        initializeData();

        // restoreDataFromSharedPref();
        restoreUserListFromInternalStorage();

        setContentView(R.layout.activity_main);

        intializeViews();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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

    private void saveUserListToInternalStorage() {
        try {
            FileOutputStream fos = this.openFileOutput(INTERNAL_USER_LIST_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(mUserList);
            fos.close();
            os.close();
        } catch (Exception ex) {
            Toast.makeText(this, "error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    private void restoreUserListFromInternalStorage() {
        FileInputStream fis = null;
        try {
            fis = this.openFileInput(INTERNAL_USER_LIST_FILE);
            ObjectInputStream ois = new ObjectInputStream ( fis );
            mUserList = ( ArrayList<User> ) ois.readObject ();
            mUserCount = mUserList.size();
            ois.close ();
            return;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void restoreDataFromSharedPref() {
        mUserCount = mSharedPreferences.getInt(PREF_KEY_USER_COUNT, 0);
    }

    private void saveDataToSharedPref() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(PREF_KEY_USER_COUNT, mUserCount);
        editor.commit();
    }

    private void initializeData() {
        mUserList = new ArrayList<>();
    }

    private void intializeViews() {
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
                mUserList.add(newUser);
                mUserCount++;
                mTvUserCount.setText(mUserCount + "");

                //saveDataToSharedPref();
                saveUserListToInternalStorage();

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
