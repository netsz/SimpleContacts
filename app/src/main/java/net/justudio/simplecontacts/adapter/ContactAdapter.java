package net.justudio.simplecontacts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.justudio.simplecontacts.R;
import net.justudio.simplecontacts.model.Contacts;

import java.util.ArrayList;
import java.util.List;

/**
 * 通讯录适配器
 */
public class ContactAdapter extends BaseAdapter {

    private ViewHolder viewHolder;
    private LayoutInflater inflater;
    private Context context;
    private List<Contacts> list;

    public ContactAdapter(Context context){
        super();
        this.context=context;
        inflater=LayoutInflater.from(context);
        list=new ArrayList<Contacts>();
    }

    public void setList(List<Contacts> list){
        this.list=list;
    }

    public List<Contacts> getList(){
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       if (convertView==null){
           convertView=inflater.inflate(R.layout.list_item, null);
           viewHolder = new ViewHolder();
           viewHolder.name=(TextView)convertView.findViewById(R.id.name);
           viewHolder.number=(TextView)convertView.findViewById(R.id.number);
           convertView.setTag(viewHolder);
       } else {
           viewHolder=(ViewHolder)convertView.getTag();
       }
        Contacts contacts=list.get(position);
        if (contacts!=null){
            viewHolder.name.setText(contacts.getName());
            viewHolder.number.setText(contacts.getNumber());
        }

        return convertView;
    }

    private class ViewHolder{
        TextView id;
        TextView name;
        TextView number;
    }
}
