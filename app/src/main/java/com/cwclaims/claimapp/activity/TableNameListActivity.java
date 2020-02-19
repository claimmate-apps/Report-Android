package com.cwclaims.claimapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.adpts.TableListAdapter;
import com.cwclaims.claimapp.models.DBItem;

import java.util.ArrayList;


public class TableNameListActivity extends Activity {

    ArrayList<DBItem> dbitemarr;
    ListView lvtable;
    ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablenamelist);

        imgback = findViewById(R.id.imgback);

        lvtable = findViewById(R.id.lvtable);

        dbitemarr = new ArrayList<>();

        DBItem DataDetail = new DBItem();
        DataDetail.setId("1");
        DataDetail.setName("roof_edge_metal");
        dbitemarr.add(DataDetail);

        DataDetail = new DBItem();
        DataDetail.setId("2");
        DataDetail.setName("roof_type");
        dbitemarr.add(DataDetail);

        DataDetail = new DBItem();
        DataDetail.setId("3");
        DataDetail.setName("dwl_first");
        dbitemarr.add(DataDetail);

        DataDetail = new DBItem();
        DataDetail.setId("4");
        DataDetail.setName("dwl_second");
        dbitemarr.add(DataDetail);

        DataDetail = new DBItem();
        DataDetail.setId("5");
        DataDetail.setName("dwl_third");
        dbitemarr.add(DataDetail);


        DataDetail = new DBItem();
        DataDetail.setId("6");
        DataDetail.setName("dwl_fourth");
        dbitemarr.add(DataDetail);

        DataDetail = new DBItem();
        DataDetail.setId("7");
        DataDetail.setName("dwl_fifth");
        dbitemarr.add(DataDetail);

        DataDetail = new DBItem();
        DataDetail.setId("8");
        DataDetail.setName("dmg_interior_damage");
        dbitemarr.add(DataDetail);

        DataDetail = new DBItem();
        DataDetail.setId("9");
        DataDetail.setName("dmg_roof");
        dbitemarr.add(DataDetail);

        DataDetail = new DBItem();
        DataDetail.setId("10");
        DataDetail.setName("dmg_elevation");
        dbitemarr.add(DataDetail);


        DataDetail = new DBItem();
        DataDetail.setId("11");
        DataDetail.setName("dmg_notes");
        dbitemarr.add(DataDetail);


        DataDetail = new DBItem();
        DataDetail.setId("12");
        DataDetail.setName("misc_title");
        dbitemarr.add(DataDetail);

        DataDetail = new DBItem();
        DataDetail.setId("13");
        DataDetail.setName("company");
        dbitemarr.add(DataDetail);


        TableListAdapter tableListAdapter = new TableListAdapter(TableNameListActivity.this, dbitemarr);
        lvtable.setAdapter(tableListAdapter);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


    }
}
