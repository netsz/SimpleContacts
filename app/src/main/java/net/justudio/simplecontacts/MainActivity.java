package net.justudio.simplecontacts;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import net.justudio.simplecontacts.adapter.ContactAdapter;
import net.justudio.simplecontacts.model.Contacts;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MainTask().execute();
        adapter = new ContactAdapter(this);
        init();

    }

    private void init(){


        ListView contactView=(ListView)findViewById(R.id.contact_lvs);
        contactView.setAdapter(adapter);
        contactView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Contacts contacts =(Contacts)adapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, SendMessageActivity.class);
                intent.putExtra("name", contacts.getName());
                intent.putExtra("number", contacts.getNumber());
                startActivity(intent);
            }
        });
    }

    private class MainTask extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void...params) {
            try {

                    List<Contacts> list = getContactsList();
                    if(list==null){
                        return false;
                    } else {
                        adapter.setList(list);
                        adapter.notifyDataSetChanged();
                        return true;
                    }
            } catch (Exception e) {
                return false;
            }


        }
        @Override
        protected void onPostExecute(Boolean result){

            if(result){

                Toast.makeText(MainActivity.this, "加载成功", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_LONG).show();
            }

            super.onPostExecute(result);
            }

        }


    public List<Contacts> getContactsList(){
        Cursor cursor = null;
        List<Contacts> list= new ArrayList<Contacts>();

        try {

            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Contacts contacts = new Contacts();
                contacts.setName(name);
                contacts.setNumber(number);
                list.add(contacts);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }
}
