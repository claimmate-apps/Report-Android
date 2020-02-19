package com.cwclaims.claimapp.adpts;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.activity.AddReportActivity;
import com.cwclaims.claimapp.activity.ReportListActivity;
import com.cwclaims.claimapp.activity.ReportzActivity;
import com.cwclaims.claimapp.activity.VerifyReportActivity;
import com.cwclaims.claimapp.models.ReportModel;

import java.util.ArrayList;

public class ReportAdpt extends BaseAdapter {

    private Context context;
    private ArrayList<ReportModel> arrayList;
    private ReportListActivity reportListActivity;

    public ReportAdpt(Context context, ArrayList<ReportModel> arrayList, ReportListActivity reportListActivity) {
        this.context = context;
        this.arrayList = arrayList;
        this.reportListActivity = reportListActivity;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(R.layout.item_report_list, null);

        TextView txtName = view.findViewById(R.id.txtName);

        txtName.setText(arrayList.get(i).getUser_name());

        view.findViewById(R.id.imgUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;


              /*  if(arrayList.get(i).getCreated_type().equalsIgnoreCase("Created by Camera App"))
                {

                    intent = new Intent(context, AddReportActivity.class);
                    intent.putExtra("cameraapp","1");
                    intent.putExtra("claimname",arrayList.get(i).getClaimant_name());
                    intent.putExtra("claimid",arrayList.get(i).getId());

//                    intent.putExtra("param", arrayList.get(i));
                    context.startActivity(intent);


                }
                else
                {
                    if (arrayList.get(i).getReport_type().equals("2"))
                    {
                        intent = new Intent(context, ReportzActivity.class);
                        intent.putExtra("isUpdate", "1");
                        intent.putExtra("cameraapp","0");
                        intent.putExtra("report", arrayList.get(i));
                    }
                    else
                    {
                        intent = new Intent(context, AddReportActivity.class);
                        intent.putExtra("cameraapp","0");
                        intent.putExtra("param", arrayList.get(i));


                    }

                    context.startActivity(intent);

                }*/


                if (arrayList.get(i).getReport_type().equals("2"))
                {
                    intent = new Intent(context, ReportzActivity.class);
                    intent.putExtra("isUpdate", "1");
                    intent.putExtra("cameraapp","0");
                    intent.putExtra("report", arrayList.get(i));
                }
                else
                {
                    intent = new Intent(context, AddReportActivity.class);
                    intent.putExtra("cameraapp","0");
                    intent.putExtra("param", arrayList.get(i));


                }

                context.startActivity(intent);


            }
        });

        view.findViewById(R.id.imgDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportListActivity.deleteReport(arrayList.get(i).getId(), arrayList.get(i).getFrom());
            }
        });

        return view;
    }
}
