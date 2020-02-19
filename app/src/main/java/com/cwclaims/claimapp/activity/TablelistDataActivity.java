package com.cwclaims.claimapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.adpts.TableListDataAdapter;
import com.cwclaims.claimapp.db.ClaimSqlLiteDbHelper;
import com.cwclaims.claimapp.models.DBItem;

import java.util.ArrayList;


public class TablelistDataActivity extends Activity implements View.OnClickListener {

    private final String TAG = "TablelistDataActivity";
    private Context mContext;

    ArrayList<DBItem> dbitemarr;
    ListView lvtable;
    public static String tblname;

    String myPath;
    ClaimSqlLiteDbHelper claimDbHelper;
    SQLiteDatabase DB;

    String SELECT_SQL;
    Cursor Cur;

    ImageView imgback, imgAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablenamelist);

        init();
    }

    private void init() {
        mContext = this;

        tblname = getIntent().getStringExtra("tblname");

        imgback = findViewById(R.id.imgback);
        imgAdd = findViewById(R.id.imgAdd);
        imgAdd.setVisibility(View.VISIBLE);

        lvtable = findViewById(R.id.lvtable);

        getmacrovalue(tblname);

        imgback.setOnClickListener(this);
        imgAdd.setOnClickListener(this);
    }

    private void getmacrovalue(String t_name) {

        opendatabase();
        dbitemarr = new ArrayList<>();

        SELECT_SQL = "SELECT * from " + t_name;
        Cur = DB.rawQuery(SELECT_SQL, null);
        if (Cur != null && Cur.getCount() > 0) {
            Cur.moveToFirst();
            do {
                DBItem dbItem = new DBItem();
                dbItem.setId("" + Cur.getInt(Cur.getColumnIndex("id")));
                dbItem.setName(Cur.getString(Cur.getColumnIndex("name")));
                dbItem.setIsselect(Cur.getString(Cur.getColumnIndex("selectname")));
                dbitemarr.add(dbItem);
            }
            while (Cur.moveToNext());
        }
        Cur.close();
        DB.close();

        TableListDataAdapter tableListAdapter = new TableListDataAdapter(TablelistDataActivity.this, dbitemarr);
        lvtable.setAdapter(tableListAdapter);
    }

    private void opendatabase() {
        myPath = claimDbHelper.claimdb_PATH + claimDbHelper.claimdb_NAME;
        DB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == imgback.getId()) {
            finish();
        } else if (view.getId() == imgAdd.getId()) {
            showAddAlert();
        }
    }

    private void showAddAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Enter Name");

        final EditText input = new EditText(mContext);
        input.setSingleLine(true);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                if (input.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_LONG).show();
                } else {

                    opendatabase();
                    DB.execSQL("insert into " + tblname + " (name)" + "values('" + input.getText().toString().trim() + "') ;");
                    DB.close();
                    getmacrovalue(tblname);
                }
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
}
