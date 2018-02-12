package com.android.sample.listviewwithbaseadapter.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.android.sample.listviewwithbaseadapter.R;
import com.android.sample.listviewwithbaseadapter.model.Item;
import com.android.sample.listviewwithbaseadapter.model.ListPojo;

import java.util.ArrayList;

/**
 * Created by KIT912 on 2/11/2018.
 */

public class SecondAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ListPojo> items;
    private View view;

    public SecondAdapter(Context context, ArrayList<ListPojo> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size(); //returns total item in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns the item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.listlayout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ListPojo currentItem = (ListPojo) getItem(position);
        viewHolder.itemName.setText(currentItem.getItemName());

//        viewHolder.itemqty.setHint(currentItem.getQty()+"");
        viewHolder.itemqty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                items.get(position).setQty(Integer.parseInt(editable+""));
            }
        });
        return convertView;
    }



    //ViewHolder inner class
    private class ViewHolder {
        TextView itemName;
        EditText itemqty;

        public ViewHolder(View view) {
            itemName = (TextView)view.findViewById(R.id.text_view_item_name);
            itemqty = (EditText) view.findViewById(R.id.et_qty);
        }
    }
}

