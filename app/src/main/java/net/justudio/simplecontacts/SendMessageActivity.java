package net.justudio.simplecontacts;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/28 0028.
 */
public class SendMessageActivity extends Activity {

    private EditText content;
    private Button send;
    private IntentFilter sendFilter;
    private SendStatusReceiver sendStatusReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_text);

        Bundle bundle=getIntent().getExtras();
        String name = bundle.getString("name", "");
        String number = bundle.getString("number", "");
        TextView sender = (TextView)findViewById(R.id.sender);
        final TextView numbe = (TextView)findViewById(R.id.number);
        TextView receiver = (TextView)findViewById(R.id.receiver);
        numbe.setText(number);
        receiver.setText(name);
        sender.setText("我");
        content=(EditText)findViewById(R.id.content);
        send=(Button)findViewById(R.id.send);

        sendFilter = new IntentFilter();
        sendFilter.addAction("SENT_SMS_ACTION");
        sendStatusReceiver = new SendStatusReceiver();
        registerReceiver(sendStatusReceiver, sendFilter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                Intent sendIntent=new Intent("SENT_SMS_ACTION");
                PendingIntent pi = PendingIntent.getBroadcast(SendMessageActivity.this, 0, sendIntent, 0);
                smsManager.sendTextMessage(numbe.getText().toString(),
                        null, content.getText().toString(), pi, null);
            }
        });


    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(sendStatusReceiver);
    }

    class SendStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            if(getResultCode()==RESULT_OK){
                Toast.makeText(context,"发送成功", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(context,"发送失败", Toast.LENGTH_LONG).show();
            }
        }
    }
}
