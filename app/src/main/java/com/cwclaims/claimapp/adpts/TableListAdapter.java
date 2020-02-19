package com.cwclaims.claimapp.adpts;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.activity.TablelistDataActivity;
import com.cwclaims.claimapp.db.ClaimSqlLiteDbHelper;
import com.cwclaims.claimapp.models.DBItem;

import java.util.ArrayList;

/**
 * Created by dharm on 10/30/17.
 */

public class TableListAdapter extends BaseAdapter
{

    ArrayList<DBItem> reportarr;
    Context ctx ;
    LayoutInflater L_inflater;


    String myPath ;
    ClaimSqlLiteDbHelper claimDbHelper;
    SQLiteDatabase DB;

    String SELECT_SQL;
    Cursor Cur;

    public TableListAdapter(Context context, ArrayList<DBItem> reportarr)
    {
        this.ctx = context;
        this.reportarr = reportarr;
        L_inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }


    @Override
    public int getCount()
    {
        return reportarr.size();
    }

    @Override
    public Object getItem(int position)
    {
        return reportarr.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View inView, ViewGroup parent)
    {
        View convertView = inView;
        final ViewHolder holder ;

        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = L_inflater.inflate(R.layout.reportrow, null);
//
//	   		 	holder.imgadItem = (ImageView)convertView.findViewById(R.id.imgAd);
//
            holder.lblname = convertView.findViewById(R.id.lblname);
            holder.lblstatus = convertView.findViewById(R.id.lblstatus);
            holder.btndelete =convertView.findViewById(R.id.btndelete);
            holder.btnedit = convertView.findViewById(R.id.btnedit);
            holder.chkselect = convertView.findViewById(R.id.chkselect);

            holder.chkselect.setVisibility(View.GONE);
            holder.btndelete.setVisibility(View.GONE);
            holder.btnedit.setVisibility(View.GONE);

//	   		 	holder.lblphone = (TextView)convertView.findViewById(R.id.lblphone);
//
//	   		 	holder.btnCall = (Button)convertView.findViewById(R.id.btnCall);
//	   		 	holder.imgLoderProgressbar = (ProgressBar)convertView.findViewById(R.id.imgLoderProgressbar);
            convertView.setTag(holder);

        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }


        holder.lblname.setText(reportarr.get(position).getName());
//		    holder.lblstatus.setText(reportarr.get(position).getstatus());

        /*
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(ctx);
                builder1.setMessage("Are you sure delete this?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {
//                                deleterow(addvalue.tblname, reportarr.get(position).getid(), position);
//                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });


        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
                alert.setTitle("Enter value"); //Set Alert dialog title here

                LinearLayout layout = new LinearLayout(ctx);
                layout.setOrientation(LinearLayout.VERTICAL);


                // Set an EditText view to get user input
                final EditText input = new EditText(ctx);
                input.setText(reportarr.get(position).getName());
                input.setSelection(input.getText().toString().length());

                layout.addView(input);

                alert.setView(layout);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //You will get as string input data in this variable.
                        // here we convert the input to a string and show in a toast.
//						String srt = input.getEditableText().toString();

                        if (input.getText().toString().trim().equals("")) {
                            Toast.makeText(ctx, "Enter value", Toast.LENGTH_LONG).show();

                        }else {
//                            updatesubcatgryrow(input.getText().toString().trim(), txtshortname.getText().toString().trim(), addvalue.tblname, reportarr.get(position).getid(), position);

                        }
                    } // End of onClick(DialogInterface dialog, int whichButton)
                }); //End of alert.setPositiveButton
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        dialog.cancel();
                    }
                }); //End of alert.setNegativeButton
                AlertDialog alertDialog = alert.create();
                alertDialog.show();

            }
        });*/

        convertView.setClickable(true);
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent tablelistdata = new Intent(ctx, TablelistDataActivity.class);
                tablelistdata.putExtra("tblname",""+reportarr.get(position).getName());
                ctx.startActivity(tablelistdata);

//                if (addvalue.tblname.equals("tbl_macros"))
//                {
//                    addmenuvalue("tbl_macrossubcat",reportarr.get(position).getname(),reportarr.get(position).getid());
//                }
            }
        });
        return convertView;
    }

    private void addmenuvalue(String tname, String strtitle,String strcid)
    {

//        Intent addnewvalue = new Intent(ctx,addsubcatvalue.class);
//        addnewvalue.putExtra("tablename", tname);
//        addnewvalue.putExtra("cid", strcid);
//        addnewvalue.putExtra("title", strtitle);
//        ctx.startActivity(addnewvalue);
    }


    private void deleterow(String tblname, String getid,int position)
    {

        opendatabase();
        SELECT_SQL = "delete from "+tblname+" where id = "+getid+"";
        DB.execSQL(SELECT_SQL);
        DB.close();

        reportarr.remove(position);
        notifyDataSetChanged();
    }

    private void updaterow(String txt,String tblname, String getid,int position)
    {

        opendatabase();

        if(tblname.equals("tbl_macros"))
        {
            SELECT_SQL = "UPDATE "+tblname+" set name = '"+txt+"' where id = "+getid+" ";

        }
        else
        {
            SELECT_SQL = "UPDATE "+tblname+" set value = '"+txt+"' where id = "+getid+" ";

        }


        DB.execSQL(SELECT_SQL);
        DB.close();

//        nameitem item = reportarr.get(position);
//        item.setname(txt);
//        reportarr.set(position, item);
//        notifyDataSetChanged();
//		reportarr.remove(position);
    }

    private void updatesubcatgryrow(String txt,String shortname,String tblname, String getid,int position)
    {

        opendatabase();
//        SELECT_SQL = "UPDATE "+tblname+" set value = '"+txt+"',shortname='"+shortname+"' where id = "+getid+" ";
//        DB.execSQL(SELECT_SQL);
//        DB.close();
//
//        nameitem item = reportarr.get(position);
//        item.setname(txt);
//        reportarr.set(position,item);
//        notifyDataSetChanged();
//		reportarr.remove(position);
    }

    private void opendatabase()
    {
        myPath = claimDbHelper.claimdb_PATH + claimDbHelper.claimdb_NAME;
        DB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }


    public static class ViewHolder
    {
        TextView  lblname , lblstatus ;
        ImageButton btndelete,btnedit;
        CheckBox chkselect;

    }



}

