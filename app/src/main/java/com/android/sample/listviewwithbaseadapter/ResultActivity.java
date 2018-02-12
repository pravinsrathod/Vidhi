package com.android.sample.listviewwithbaseadapter;

import android.Manifest;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sample.listviewwithbaseadapter.adapter.SecondAdapter;
import com.android.sample.listviewwithbaseadapter.model.ListPojo;

import java.util.ArrayList;

/**
 * Created by KIT927 on 1/17/2018.
 */

public class ResultActivity extends AppCompatActivity implements View.OnClickListener  {
    private static String smsToSend = "";
    private static final String TAG = "MainActivity";
    private static final String PREF_USER_MOBILE_PHONE = "pref_user_mobile_phone";
    private static final int SMS_PERMISSION_CODE = 0;
    private ArrayList<ListPojo> arrlistpojo= new ArrayList<>();

    private EditText mNumberEditText;
    private String mUserMobilePhone;
    private SharedPreferences mSharedPreferences;
    private ArrayList<String> arr = new ArrayList<>();
    private ListView lv;
    private SecondAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle bundle = getIntent().getExtras();
        lv= (ListView) findViewById(R.id.lv);
        arr =  bundle.getStringArrayList("message");
        new Thread(new Runnable() {
            @Override
            public void run() {
                getDataIntoPojot(arr);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter= new SecondAdapter(ResultActivity.this,arrlistpojo);
                        lv.setAdapter(adapter);
                    }
                });
            }
        }).start();

//        TextView txtView = (TextView) findViewById(R.id.text_view_item_name_result);

        if (!hasReadSmsPermission()) {
            showRequestPermissionsInfoAlertDialog();
        }

        initViews();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserMobilePhone = mSharedPreferences.getString(PREF_USER_MOBILE_PHONE, "");
        if (!TextUtils.isEmpty(mUserMobilePhone)) {
            mNumberEditText.setText(mUserMobilePhone);
        }
    }

    private void getDataIntoPojot(ArrayList<String> arr) {

        ListPojo list;
        for(int i=0;i<arr.size();i++)
        {
            list= new ListPojo();
            list.setItemName(arr.get(i));
            list.setQty(0);
            arrlistpojo.add(list);
        }
    }

    private void initViews() {
        mNumberEditText = (EditText) findViewById(R.id.et_number);
        findViewById(R.id.btn_normal_sms).setOnClickListener(this);
        findViewById(R.id.btn_conditional_sms).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_conditional_sms:
                if (!hasValidPreConditions()) return;
                checkAndUpdateUserPrefNumber();

                SmsHelper.sendDebugSms(String.valueOf(mNumberEditText.getText()), SmsHelper.SMS_CONDITION +" "+smsToSend);
                Toast.makeText(getApplicationContext(), R.string.toast_sending_sms, Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_normal_sms:
                if (!hasValidPreConditions()) return;
                checkAndUpdateUserPrefNumber();

                SmsHelper.sendDebugSms(String.valueOf(mNumberEditText.getText()), smsToSend);
                Toast.makeText(getApplicationContext(), R.string.toast_sending_sms, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * Checks if stored SharedPreferences value needs updating and updates \o/
     */
    private void checkAndUpdateUserPrefNumber() {
        if (TextUtils.isEmpty(mUserMobilePhone) && !mUserMobilePhone.equals(mNumberEditText.getText().toString())) {
            mSharedPreferences
                    .edit()
                    .putString(PREF_USER_MOBILE_PHONE, mNumberEditText.getText().toString())
                    .apply();
        }
    }


    /**
     * Validates if the app has readSmsPermissions and the mobile phone is valid
     *
     * @return boolean validation value
     */
    private boolean hasValidPreConditions() {
        if (!hasReadSmsPermission()) {
            requestReadAndSendSmsPermission();
            return false;
        }

        if (!SmsHelper.isValidPhoneNumber(mNumberEditText.getText().toString())) {
            Toast.makeText(getApplicationContext(), R.string.error_invalid_phone_number, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Optional informative alert dialog to explain the user why the app needs the Read/Send SMS permission
     */
    private void showRequestPermissionsInfoAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.permission_alert_dialog_title);
        builder.setMessage(R.string.permission_dialog_message);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                requestReadAndSendSmsPermission();
            }
        });
        builder.show();
    }

    /**
     * Runtime permission shenanigans
     */
    private boolean hasReadSmsPermission() {
        return ContextCompat.checkSelfPermission(ResultActivity.this,
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(ResultActivity.this, Manifest.permission.READ_SMS)) {
            Log.d(TAG, "shouldShowRequestPermissionRationale(), no permission requested");
            return;
        }
        ActivityCompat.requestPermissions(ResultActivity.this, new String[]{Manifest.permission.READ_SMS},
                SMS_PERMISSION_CODE);
    }
}
