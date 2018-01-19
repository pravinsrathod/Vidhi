package com.android.sample.listviewwithbaseadapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sample.listviewwithbaseadapter.adapter.CustomListAdapter;
import com.android.sample.listviewwithbaseadapter.model.Item;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Item> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView itemsListView  = (ListView)findViewById(R.id.list_view_items);

        //create adapter object
        CustomListAdapter adapter = new CustomListAdapter(this, generateItemsList());

        //set custom adapter as adapter to our list view
        itemsListView.setAdapter(adapter);

        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

               // String item = ((TextView)view).getText().toString();

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("message", list.get(position).getItemDetails());
                startActivity(intent);

            //    Toast.makeText(getApplicationContext(), "Item " + (position + 1) + ": " + list.get(position).getItemDescription(), Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     * Util function to generate list of items
     *
     * @return ArrayList
     */
    private ArrayList<Item> generateItemsList() {
        String itemNames[] = getResources().getStringArray(R.array.items_name);
        String itemDescriptions[] = getResources().getStringArray(R.array.item_description);
        String itemDetails[] = getResources().getStringArray(R.array.item_Details);
       for (int i = 0; i < itemNames.length; i++) {
            list.add(new Item(itemNames[i], itemDescriptions[i],itemDetails[i]));
        }

        return list;
    }
}
