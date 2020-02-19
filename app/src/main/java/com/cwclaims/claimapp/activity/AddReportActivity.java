package com.cwclaims.claimapp.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.R.id;
import com.cwclaims.claimapp.R.layout;
import com.cwclaims.claimapp.adpts.SpinnerAdpt;
import com.cwclaims.claimapp.db.ClaimSqlLiteDbHelper;
import com.cwclaims.claimapp.models.ReportModel;
import com.cwclaims.claimapp.other.PrefManager;
import com.cwclaims.claimapp.other.Utility;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AddReportActivity extends Activity implements View.OnClickListener, OnCheckedChangeListener, OnItemSelectedListener {

    private final String TAG = "AddReportActivity";
    private Context mContext;
    LinearLayout LinearLayout1, llMortgagee, llLaborMin;
    CheckBox l_min;

    // First
    EditText etFirstClimantName, etFirstTolDesc, etFirstInsuredName, edtMortgagee, edtAdded, edtRemoved, edtContractorName, edtCompanyName;
    Button btnFirstDateLoss, btnFirstDateInspected, btnFirstTimeInspected;
    CheckBox chbxFirstIsPresent, chbxInsuredNameDiff, chkmortgagee, chkContractor, chkNoMortgagee;
    TextView tvInsuredName;
    LinearLayout llContractor;

    // Roof
    CheckBox chbxRoof;
    EditText etRoofPitch, etRoofLayers;
    TextView txtRoofPitchDec, txtRoofPitch, txtRoofPitchInc, txtRoofLayersDec, txtRoofLayers, txtRoofLayersInc, txtAge;
    Spinner spnrRoofEdgeMetal, spnrRoofType;
    RecyclerView rcRoofEdgeMetal;
    EditText etRoofEdgeMetalCustom, etRoofTypeCustom;
    EditText etRoofAge;
    SeekBar sbAge;

    int roofEdgeMetalSelectPos = 0;

    // Dweling
    EditText etDwlStory;
    TextView txtStoryDec, txtStory, txtStoryInc;
    Spinner spnrDwlFirst, spnrDwlSecond, spnrDwlThird, spnrDwlFourth, spnrDwlFifth;
    RecyclerView rcDwlSecond;
    EditText etDwlFirstCustom, etDwlSecondCustom, etDwlThirdCustom, etDwlFourthCustom,
            etDwlFifthCustom;
    ListView listGarage;
    int dwlSecondSelectPos;
    ArrayList<HashMap<String, String>> arrayListGarage;
    SelectGarageAdpt selectGarageAdpt;

    // Damage
    Spinner spnrDmgInterior, spnrDmgRoof, spnrDmgFrontElev, spnrDmgBackElev,
            spnrDmgLeftElev, spnrDmgRightElev, spnrDmgNotes;
    EditText etDmgInteriorCustom, etDmgRoofCustom, etDmgFrontElevCustom, etDmgBackElevCustom,
            etDmgLeftElevCustom, etDmgRightElevCustom, etDmgNotesCustom;
    ImageButton imgBtnInteriorForward;

    // Misc
    Spinner spnrMiscTitle;
    EditText etMiscTitleCustom;
    CheckBox chbxMiscOP, chbxMiscDepreciation, chbxMiscApsDamage,
            chbxMiscContents, chbxMiscSalvage, chkSubrogation;
    EditText etMiscDepreciationYear, etMiscApsDamage, etMiscSalvage, edtSubrogation,
            etMiscLaborMinIncl, etMiscLaborMinRemove, edtAllCustom;

    Button btnHarford, btnAddCustom, btnRepotz;

    // Custom
    boolean isDatePickerForInspection = false;
    String btnInitInspectTime, btnInitInspectDate, btnInitLossDate;

    ArrayList<String> spnrRoofEdgeMetalaarr, spnroof_typearr, spndwl_firstarr, spndwl_secondarr, spndwl_thirdarr;
    ArrayList<String> spndwl_fourtharr, spndwl_fiftharr, spndmg_interior_damagearr, spndmg_roofarr, spndmg_elevationarr, spndmg_notesarr, spnmisc_titlearr;

    ArrayList<String> spnr_allarr, arrayListCompany;
    Spinner spnr_all;

    String myPath;
    ClaimSqlLiteDbHelper claimDbHelper;
    SQLiteDatabase DB;

    String SELECT_SQL;
    Cursor Cur;

    SharedPreferences lastpathpf;
    SharedPreferences.Editor lastimageeditor;

    Button btnsetting;

    ScrollView sv_main;

    Button btn_misc_title;

    Button btndefaultdateofloss, btnAddCustomD;
    Spinner spCompany;
    EditText edtAllCustomD;

    LinearLayout llReportType;
    Spinner spReportType;

    private Spinner spName;
    private ArrayList<String> arrayNames, arrayReportType;
    ReportModel reportModel;
    String claimname = "";
    String appname = "";
    String ClaimId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);


        appname = getIntent().getStringExtra("cameraapp");

        if(appname.equalsIgnoreCase("1"))
        {
            claimname = getIntent().getStringExtra("claimname");
            ClaimId = getIntent().getStringExtra("claimid");
        }


        // remove title
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//		hideSoftKeyboard();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        lastpathpf = getSharedPreferences("claimmateform", Context.MODE_PRIVATE);
        lastimageeditor = lastpathpf.edit();

        copydb();
        initContols();
        setOnTextChangeListner();

        LinearLayout1 = findViewById(R.id.LinearLayout1);

        spnrRoofEdgeMetalaarr = new ArrayList<>();
        spnroof_typearr = new ArrayList<>();
        spndwl_firstarr = new ArrayList<>();
        spndwl_secondarr = new ArrayList<>();
        spndwl_thirdarr = new ArrayList<>();
        spndwl_fourtharr = new ArrayList<>();
        spndwl_fiftharr = new ArrayList<>();
        spndmg_interior_damagearr = new ArrayList<>();
        spndmg_roofarr = new ArrayList<>();
        spndmg_elevationarr = new ArrayList<>();
        spndmg_notesarr = new ArrayList<>();
        spnmisc_titlearr = new ArrayList<>();
        spnr_allarr = new ArrayList<>();

//        spnr_allarr = claimDbHelper.getCustomData(false);
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spnr_allarr);
//        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
//        spnr_all.setAdapter(new SpinnerAdpt(mContext, spnr_allarr));
//        spnr_all.setSelection(PrefManager.getCompany());

        getroof_edge_metalvalue("roof_edge_metal", spnrRoofEdgeMetalaarr, spnrRoofEdgeMetal);
        getroof_edge_metalvalue("roof_type", spnroof_typearr, spnrRoofType);

        rcRoofEdgeMetal.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rcRoofEdgeMetal.setAdapter(new RoofEdgeMetalAdpt());

        getroof_edge_metalvalue("dwl_first", spndwl_firstarr, spnrDwlFirst);
        getroof_edge_metalvalue("dwl_second", spndwl_secondarr, spnrDwlSecond);

        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        rcDwlSecond.setLayoutManager(manager);
        rcDwlSecond.setAdapter(new DwlSecondAdpt());
//        rcDwlSecond.scrollToPosition(dwlSecondSelectPos);

        getroof_edge_metalvalue("dwl_third", spndwl_thirdarr, spnrDwlThird);
        getroof_edge_metalvalue("dwl_fourth", spndwl_fourtharr, spnrDwlFourth);
        getroof_edge_metalvalue("dwl_fifth", spndwl_fiftharr, spnrDwlFifth);
        getroof_edge_metalvalue("dmg_interior_damage", spndmg_interior_damagearr, spnrDmgInterior);
        getroof_edge_metalvalue("dmg_roof", spndmg_roofarr, spnrDmgRoof);

        getroof_edge_metalvalue("dmg_elevation", spndmg_elevationarr, spnrDmgFrontElev);
        getroof_edge_metalvalue("dmg_elevation", spndmg_elevationarr, spnrDmgBackElev);
        getroof_edge_metalvalue("dmg_elevation", spndmg_elevationarr, spnrDmgLeftElev);
        getroof_edge_metalvalue("dmg_elevation", spndmg_elevationarr, spnrDmgRightElev);

        getroof_edge_metalvalue("dmg_notes", spndmg_notesarr, spnrDmgNotes);
        getroof_edge_metalvalue("misc_title", spnmisc_titlearr, spnrMiscTitle);

        getroof_edge_metalvalue("company", spnr_allarr, spnr_all);

        setData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void onClickForOnBackPressed() {
        onClick(btnHarford);
    }

    int directExit = 0;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setMessage("Do you want to save changes or discard?");
        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                directExit = 1;
                onClickForOnBackPressed();
            }
        });
        alert.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AddReportActivity.this.finish();
            }
        });
        alert.show();
    }

    private void setData() {
        try {
            reportModel = (ReportModel) getIntent().getSerializableExtra("param");
            btnHarford.setTag("1");
            if (reportModel != null)
                llReportType.setVisibility(View.GONE);
        } catch (Exception e) {
            btnHarford.setTag("0");
            llReportType.setVisibility(View.VISIBLE);
        }
        btnRepotz.setVisibility(View.GONE);

        if (reportModel == null) {
            if (PrefManager.getSetting().equals(""))
                return;
            else
                reportModel = new Gson().fromJson(PrefManager.getSetting(), ReportModel.class);
        }

        for (int i = 0; i < arrayNames.size(); i++) {
            if (reportModel.getMr().equals(arrayNames.get(i))) {
                spName.setSelection(i);
                break;
            }
        }

        if(appname.equalsIgnoreCase("1"))
        {
            reportModel.setId(ClaimId);
        }
        else
        {
            etFirstClimantName.setText(reportModel.getClaimant_name());
        }


        chbxInsuredNameDiff.setChecked(reportModel.getInsuredNameDiffernt().equals("1") ? true : false);
        etFirstInsuredName.setText(reportModel.getInsuredName());
        etFirstTolDesc.setText(reportModel.getCausesOfLoss());
        btnFirstDateLoss.setText(reportModel.getDateLoss());
        chbxFirstIsPresent.setChecked(reportModel.getInsuredPersonPresent().equals("1") ? true : false);
        chkmortgagee.setChecked(reportModel.getIsMortgagee().equals("1") ? true : false);
        edtMortgagee.setText(reportModel.getMortgagee());
        chkNoMortgagee.setChecked(reportModel.getIsNoMortgagee().equals("1") ? true : false);

        if (reportModel.getIsContractor() != null) {
            if (reportModel.getIsContractor().equals("1")) {
                chkContractor.setChecked(true);
                edtContractorName.setText(reportModel.getContractorName());
                edtCompanyName.setText(reportModel.getCompanyName());
            }
        }

        chbxRoof.setChecked(reportModel.getIsRoof().equals("1") ? true : false);
        txtRoofPitch.setText(reportModel.getPitch());
        txtRoofLayers.setText(reportModel.getLayers());

        if (!reportModel.getDateInspected().equals("")) {
            btnFirstDateInspected.setText(reportModel.getDateInspected());
            btnFirstTimeInspected.setText(reportModel.getTimeInspected());
        }
        for (int i = 0; i < spnrRoofEdgeMetalaarr.size(); i++) {
            if (spnrRoofEdgeMetalaarr.get(i).equals(reportModel.getEdgeMetal())) {
                spnrRoofEdgeMetal.setSelection(i);
                roofEdgeMetalSelectPos = i;
                rcRoofEdgeMetal.scrollToPosition(roofEdgeMetalSelectPos);
                break;
            }
        }
        etRoofEdgeMetalCustom.setText(reportModel.getEdgeMetalCustom());

        for (int i = 0; i < spnroof_typearr.size(); i++) {
            if (spnroof_typearr.get(i).equals(reportModel.getType())) {
                spnrRoofType.setSelection(i);
                break;
            }
        }
        etRoofTypeCustom.setText(reportModel.getTypeCustom());

        txtAge.setText(reportModel.getAge());
        try {
            sbAge.setProgress(Integer.parseInt(reportModel.getAge()));
        } catch (Exception e) {
        }

        txtStory.setText(reportModel.getStory());

        for (int i = 0; i < spndwl_firstarr.size(); i++) {
            if (spndwl_firstarr.get(i).equals(reportModel.getDwl_first())) {
                spnrDwlFirst.setSelection(i);
                break;
            }
        }
        etDwlFirstCustom.setText(reportModel.getDwl_first_custom());

        for (int i = 0; i < spndwl_secondarr.size(); i++) {
            if (spndwl_secondarr.get(i).equals(reportModel.getDwl_second())) {
                spnrDwlSecond.setSelection(i);
                dwlSecondSelectPos = i;
                rcDwlSecond.scrollToPosition(dwlSecondSelectPos);
                break;
            }
        }
        etDwlSecondCustom.setText(reportModel.getDwl_second_custom());

        for (int i = 0; i < spndwl_thirdarr.size(); i++) {
            if (spndwl_thirdarr.get(i).equals(reportModel.getDwl_third())) {
                spnrDwlThird.setSelection(i);
                break;
            }
        }
        etDwlThirdCustom.setText(reportModel.getDwl_third_custom());

        Log.i(TAG, "garage res = " + reportModel.getDwl_fourth());
        try {
            JSONArray jsonArray = new JSONArray(reportModel.getDwl_fourth());
            arrayListGarage = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("1", jsonObject.getString("1"));
                hashMap.put("2", jsonObject.getString("2"));
                arrayListGarage.add(hashMap);
                listGarage.setAdapter(selectGarageAdpt);
                Utility.setListViewHeightBasedOnChildren(listGarage);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*for (int i = 0; i < spndwl_fourtharr.size(); i++) {
            if (spndwl_fourtharr.get(i).equals(reportModel.getDwl_fourth())) {
                spnrDwlFourth.setSelection(i);
                break;
            }
        }
        etDwlFourthCustom.setText(reportModel.getDwl_fourth_custom());*/

        for (int i = 0; i < spndwl_fiftharr.size(); i++) {
            if (spndwl_fiftharr.get(i).equals(reportModel.getDwl_fifth())) {
                spnrDwlFifth.setSelection(i);
                break;
            }
        }
        etDwlFifthCustom.setText(reportModel.getDwl_fifth_custom());

        for (int i = 0; i < spndmg_interior_damagearr.size(); i++) {
            if (spndmg_interior_damagearr.get(i).equals(reportModel.getDmg_interior())) {
                spnrDmgInterior.setSelection(i);
                break;
            }
        }
        etDmgInteriorCustom.setText(reportModel.getDmg_interior_custom());

        for (int i = 0; i < spndmg_roofarr.size(); i++) {
            if (spndmg_roofarr.get(i).equals(reportModel.getDmg_roof())) {
                spnrDmgRoof.setSelection(i);
                break;
            }
        }
        etDmgRoofCustom.setText(reportModel.getDmg_roof_custom());

        for (int i = 0; i < spndmg_elevationarr.size(); i++) {
            if (spndmg_elevationarr.get(i).equals(reportModel.getDmg_front_eleva())) {
                spnrDmgFrontElev.setSelection(i);
                break;
            }
        }
        etDmgFrontElevCustom.setText(reportModel.getDmg_front_custom());

        for (int i = 0; i < spndmg_elevationarr.size(); i++) {
            if (spndmg_elevationarr.get(i).equals(reportModel.getDmg_left_eleva())) {
                spnrDmgLeftElev.setSelection(i);
                break;
            }
        }
        etDmgLeftElevCustom.setText(reportModel.getDmg_left_custom());

        for (int i = 0; i < spndmg_elevationarr.size(); i++) {
            if (spndmg_elevationarr.get(i).equals(reportModel.getDmg_back_eleva())) {
                spnrDmgBackElev.setSelection(i);
                break;
            }
        }
        etDmgBackElevCustom.setText(reportModel.getDmg_back_eleva());

        for (int i = 0; i < spndmg_elevationarr.size(); i++) {
            if (spndmg_elevationarr.get(i).equals(reportModel.getDmg_right_eleva())) {
                spnrDmgRightElev.setSelection(i);
                break;
            }
        }
        etDmgRightElevCustom.setText(reportModel.getDmg_right_eleva());

        for (int i = 0; i < spndmg_notesarr.size(); i++) {
            if (spndmg_notesarr.get(i).equals(reportModel.getDmg_notes())) {
                spnrDmgNotes.setSelection(i);
                break;
            }
        }
        etDmgNotesCustom.setText(reportModel.getDmg_notes_custom());

        for (int i = 0; i < spnmisc_titlearr.size(); i++) {
            if (spnmisc_titlearr.get(i).equals(reportModel.getMisc_title())) {
                spnrMiscTitle.setSelection(i);
                break;
            }
        }

        etMiscTitleCustom.setText(reportModel.getMisc_title_custom());

        chbxMiscOP.setChecked(reportModel.getMisc_op().equals("1") ? true : false);
        chbxMiscDepreciation.setChecked(reportModel.getMisc_depreciation().equals("1") ? true : false);
        etMiscDepreciationYear.setText(reportModel.getMisc_depreciation_year());
        chbxMiscApsDamage.setChecked(reportModel.getMisc_aps_damage().equals("1") ? true : false);
        etMiscApsDamage.setText(reportModel.getMisc_aps_damage_custom());
        chbxMiscContents.setChecked(reportModel.getMisc_contents().equals("1") ? true : false);
        chbxMiscSalvage.setChecked(reportModel.getMisc_salvage().equals("1") ? true : false);
        etMiscSalvage.setText(reportModel.getMisc_salvage_custom());
        chkSubrogation.setChecked(reportModel.getSubrogation().equals("1") ? true : false);
        edtSubrogation.setText(reportModel.getSubrogation_custom());
        l_min.setChecked(reportModel.getLaborMin().equals("1") ? true : false);
        edtAdded.setText(reportModel.getLaborMinAdded());
        edtRemoved.setText(reportModel.getLaborMinRemoved());

        for (int i = 0; i < spnr_allarr.size(); i++) {
            if (spnr_allarr.get(i).equals(reportModel.getAll())) {
                spnr_all.setSelection(i);
                break;
            }
        }
        edtAllCustom.setText(reportModel.getAllCustom());

        try {
            spReportType.setSelection(Integer.parseInt(reportModel.getReport_type()));
        } catch (Exception e) {
        }
    }

    private void getroof_edge_metalvalue(String tblname, ArrayList<String> listarr, Spinner sp) {

        if (listarr.size() > 0) {
            set_spnrRoofEdgeMetala(listarr, sp, 1);
            return;
        }

        opendatabase();

        int item = 0;
        int selectitem = 0;

        SELECT_SQL = "SELECT * FROM " + tblname;
        Cur = DB.rawQuery(SELECT_SQL, null);
        if (Cur != null && Cur.getCount() > 0) {
            Cur.moveToFirst();
            do {
                String strvalue = Cur.getString(Cur.getColumnIndex("name"));
                listarr.add(strvalue);

                if (Cur.getString(Cur.getColumnIndex("selectname")).equals("1")) {
                    selectitem = item;
                }

                item++;
            }
            while (Cur.moveToNext());
        }
        Cur.close();
        DB.close();

        listarr.add("Insert custom data");
        listarr.add("Add New Option");

        set_spnrRoofEdgeMetala(listarr, sp, selectitem);
    }

    private void copydb() {

        claimDbHelper = new ClaimSqlLiteDbHelper(this);
        String iscopydp = lastpathpf.getString("iscopy", "no");

        if (iscopydp.equals("no")) {
            try {

                claimDbHelper.CopyDataBaseFromAsset();
                lastimageeditor.putString("iscopy", "yes");
                lastimageeditor.commit();
                Log.e("iscopy", "no");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("iscopy", "yes");

        }

    }

    private void opendatabase() {

        myPath = claimDbHelper.claimdb_PATH + claimDbHelper.claimdb_NAME;
        DB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void initContols() {
        mContext = AddReportActivity.this;

        btn_misc_title = findViewById(R.id.btn_misc_title);

        chkmortgagee = findViewById(R.id.chkmortgagee);
        l_min = findViewById(R.id.l_min);
        chkNoMortgagee = findViewById(id.chkNoMortgagee);

        edtMortgagee = findViewById(R.id.edtMortgagee);
        edtAdded = findViewById(R.id.edtAdded);
        edtRemoved = findViewById(R.id.edtRemoved);

        llMortgagee = findViewById(id.llMortgagee);
        llLaborMin = findViewById(R.id.llLaborMin);

        edtAllCustom = findViewById(R.id.edtAllCustom);
        btnAddCustom = findViewById(R.id.btnAddCustom);

        // First
        etFirstClimantName = findViewById(R.id.et_first_ClaimantName);
        etFirstClimantName.setText(claimname);
        chbxInsuredNameDiff = findViewById(R.id.chbx_first_InsuredNameDiff);
        tvInsuredName = findViewById(R.id.tv_first_InsuredName);
        etFirstInsuredName = findViewById(R.id.et_first_InsuredName);
        spName = findViewById(id.spName);
        etFirstTolDesc = findViewById(R.id.et_first_TolDesc);
        btnFirstDateLoss = findViewById(R.id.btn_first_date_loss);
        String strdate = lastpathpf.getString("dateofloss", "11/21/2017");
        btnFirstDateLoss.setText(strdate);

        btnFirstDateInspected = findViewById(R.id.btn_first_date_inspected);
        btnFirstTimeInspected = findViewById(R.id.btn_first_time_inspected);
        chbxFirstIsPresent = findViewById(R.id.chbx_first_IsPresent);
        chkContractor = findViewById(id.chkContractor);
        llContractor = findViewById(id.llContractor);
        edtContractorName = findViewById(id.edtContractorName);
        edtCompanyName = findViewById(id.edtCompanyName);

        // Roof
        chbxRoof = findViewById(R.id.chbx_roof);
        etRoofPitch = findViewById(R.id.et_roof_Pitch);
        etRoofLayers = findViewById(R.id.et_roof_Layers);
        txtRoofPitchDec = findViewById(id.txtRoofPitchDec);
        txtRoofPitch = findViewById(id.txtRoofPitch);
        txtRoofPitchInc = findViewById(id.txtRoofPitchInc);
        txtRoofLayersDec = findViewById(id.txtRoofLayersDec);
        txtRoofLayers = findViewById(id.txtRoofLayers);
        txtRoofLayersInc = findViewById(id.txtRoofLayersInc);
        spnrRoofEdgeMetal = findViewById(R.id.spnr_roof_edge_metal);

        rcRoofEdgeMetal = findViewById(id.rcRoofEdgeMetal);

        spnr_all = findViewById(id.spnr_all);
        spnrRoofType = findViewById(R.id.spnr_roof_type);

        etRoofEdgeMetalCustom = findViewById(R.id.et_roof_edge_metal_custom);
        etRoofTypeCustom = findViewById(R.id.et_roof_type_custom);
        etRoofAge = findViewById(R.id.et_roof_age);
        txtAge = findViewById(R.id.txtAge);
        sbAge = findViewById(R.id.sbAge);

        // Dweling
        etDwlStory = findViewById(R.id.et_dwl_story);
        txtStoryDec = findViewById(id.txtStoryDec);
        txtStory = findViewById(id.txtStory);
        txtStoryInc = findViewById(id.txtStoryInc);
        spnrDwlFirst = findViewById(R.id.spnr_dwl_first);
        spnrDwlSecond = findViewById(R.id.spnr_dwl_second);
        rcDwlSecond = findViewById(id.rcDwlSecond);
        spnrDwlThird = findViewById(R.id.spnr_dwl_third);
        spnrDwlFourth = findViewById(R.id.spnr_dwl_fourth);
        spnrDwlFifth = findViewById(R.id.spnr_dwl_fifth);

        listGarage = findViewById(id.listGarage);
        arrayListGarage = new ArrayList<>();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("1", "0");
        hm.put("2", "detached");
        arrayListGarage.add(hm);
        selectGarageAdpt = new SelectGarageAdpt();
        listGarage.setAdapter(selectGarageAdpt);
        Utility.setListViewHeightBasedOnChildren(listGarage);

        etDwlFirstCustom = findViewById(R.id.et_dwl_first_custom);
        etDwlSecondCustom = findViewById(R.id.et_dwl_second_custom);
        etDwlThirdCustom = findViewById(R.id.et_dwl_third_custom);
        etDwlFourthCustom = findViewById(R.id.et_dwl_fourth_custom);
        etDwlFifthCustom = findViewById(R.id.et_dwl_fifth_custom);

        // Damage
        spnrDmgInterior = findViewById(R.id.spnr_dmg_interior);
        spnrDmgRoof = findViewById(R.id.spnr_dmg_roof);
        spnrDmgFrontElev = findViewById(R.id.spnr_dmg_front_eleva);
        spnrDmgBackElev = findViewById(R.id.spnr_dmg_back_eleva);
        spnrDmgLeftElev = findViewById(R.id.spnr_dmg_left_eleva);
        spnrDmgRightElev = findViewById(R.id.spnr_dmg_right_eleva);
        spnrDmgNotes = findViewById(R.id.spnr_dmg_notes);
        etDmgInteriorCustom = findViewById(R.id.et_dmg_interior_custom);
        etDmgRoofCustom = findViewById(R.id.et_dmg_roof_custom);
        etDmgFrontElevCustom = findViewById(R.id.et_dmg_front_custom);
        etDmgBackElevCustom = findViewById(R.id.et_dmg_back_custom);
        etDmgLeftElevCustom = findViewById(R.id.et_dmg_left_custom);
        etDmgRightElevCustom = findViewById(R.id.et_dmg_right_custom);
        etDmgNotesCustom = findViewById(R.id.et_dmg_notes_custom);

        imgBtnInteriorForward = findViewById(R.id.imgBtnInteriorForward);

        // Misc
        spnrMiscTitle = findViewById(R.id.spnr_misc_title);
        chbxMiscOP = findViewById(R.id.chbx_misc_op);
        chbxMiscDepreciation = findViewById(R.id.chbx_misc_depreciation);
        chbxMiscApsDamage = findViewById(R.id.chbx_misc_aps_damage);
        chbxMiscContents = findViewById(R.id.chbx_misc_contents);
        chbxMiscSalvage = findViewById(R.id.chbx_misc_salvage);
        chkSubrogation = findViewById(R.id.chkSubrogation);
        etMiscApsDamage = findViewById(R.id.et_misc_aps_damage);
        etMiscSalvage = findViewById(R.id.et_misc_salvage);
        edtSubrogation = findViewById(R.id.edtSubrogation);
        etMiscLaborMinIncl = findViewById(R.id.et_misc_labor_incl);
        etMiscLaborMinRemove = findViewById(R.id.et_misc_labor_remove);
        etMiscDepreciationYear = findViewById(R.id.et_misc_depreciation_year);
        etMiscTitleCustom = findViewById(R.id.et_misc_title_custom);
        btnHarford = findViewById(R.id.btn_hartford);
        btnRepotz = findViewById(R.id.btnRepotz);
        btnsetting = findViewById(R.id.btnsetting);

        llReportType = findViewById(R.id.llReportType);
        spReportType = findViewById(id.spReportType);

        arrayReportType = new ArrayList<>();
        arrayReportType.add("ClaimMate Report");
        for (HashMap<String, String> hm2 : claimDbHelper.getCustomReport()) {
            arrayReportType.add(hm2.get("ReportName"));
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayReportType);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spReportType.setAdapter(spinnerArrayAdapter);

        arrayNames = new ArrayList();
        arrayNames.add("Mr.");
        arrayNames.add("Ms.");
        arrayNames.add("Mr. & Ms.");
        arrayNames.add("None");
        spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spName.setAdapter(spinnerArrayAdapter);

        btnsetting.setOnClickListener(this);
        // Button click listner
        btnFirstDateInspected.setOnClickListener(this);
        btnFirstDateLoss.setOnClickListener(this);
        btnFirstTimeInspected.setOnClickListener(this);
        btnHarford.setOnClickListener(this);
        btnRepotz.setOnClickListener(this);
        imgBtnInteriorForward.setOnClickListener(this);

        // Checkbox listner
        chkContractor.setOnCheckedChangeListener(this);
        chkmortgagee.setOnCheckedChangeListener(this);
        chbxInsuredNameDiff.setOnCheckedChangeListener(this);
        chbxMiscApsDamage.setOnCheckedChangeListener(this);
        chbxMiscSalvage.setOnCheckedChangeListener(this);
        chkSubrogation.setOnCheckedChangeListener(this);
        chbxMiscDepreciation.setOnCheckedChangeListener(this);
        l_min.setOnCheckedChangeListener(this);

        // Spinner item selected listner
        spnrDmgNotes.setOnItemSelectedListener(this);
        spnrDmgBackElev.setOnItemSelectedListener(this);
        spnrDmgFrontElev.setOnItemSelectedListener(this);
        spnrDmgInterior.setOnItemSelectedListener(this);
        spnrDmgLeftElev.setOnItemSelectedListener(this);
        spnrDmgRightElev.setOnItemSelectedListener(this);
        spnrDmgRoof.setOnItemSelectedListener(this);

        spnrDwlFifth.setOnItemSelectedListener(this);
        spnrDwlFirst.setOnItemSelectedListener(this);
        spnrDwlFourth.setOnItemSelectedListener(this);
        spnrDwlSecond.setOnItemSelectedListener(this);
        spnrDwlThird.setOnItemSelectedListener(this);

        spnrMiscTitle.setOnItemSelectedListener(this);

        spnrRoofEdgeMetal.setOnItemSelectedListener(this);
        spnr_all.setOnItemSelectedListener(this);

        spnrRoofType.setOnItemSelectedListener(this);

        btnInitInspectTime = btnFirstTimeInspected.getText().toString();
        btnInitInspectDate = btnFirstDateInspected.getText().toString();
        btnInitLossDate = btnFirstDateLoss.getText().toString();

        Calendar c = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        btnFirstTimeInspected.setText(dateFormat.format(c.getTime()));

        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        btnFirstDateInspected.setText(dateFormat.format(c.getTime()));


        sv_main = findViewById(id.sv_main);
        sv_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event != null && event.getAction() == MotionEvent.ACTION_MOVE) {
                    InputMethodManager imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
                    boolean isKeyboardUp = imm.isAcceptingText();

                    if (isKeyboardUp) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
                return false;
            }
        });

        txtRoofPitchDec.setOnTouchListener(onTouch());
        txtRoofPitchInc.setOnTouchListener(onTouch());
        txtRoofLayersDec.setOnTouchListener(onTouch());
        txtRoofLayersInc.setOnTouchListener(onTouch());
        txtStoryDec.setOnTouchListener(onTouch());
        txtStoryInc.setOnTouchListener(onTouch());

        sbAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtAge.setText(sbAge.getProgress() + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private View.OnTouchListener onTouch() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if (view.getId() == txtRoofPitchDec.getId()) {
                            try {
                                int cnt = Integer.parseInt(txtRoofPitch.getText().toString());
                                if (cnt != 0)
                                    txtRoofPitch.setText(--cnt + "");
                            } catch (Exception e) {
                                txtRoofPitch.setText("0");
                            }
                        } else if (view.getId() == txtRoofPitchInc.getId()) {
                            try {
                                int cnt = Integer.parseInt(txtRoofPitch.getText().toString());
                                txtRoofPitch.setText(++cnt + "");
                            } catch (Exception e) {
                                txtRoofPitch.setText("0");
                            }
                        } else if (view.getId() == txtRoofLayersDec.getId()) {
                            try {
                                int cnt = Integer.parseInt(txtRoofLayers.getText().toString());
                                if (cnt != 0)
                                    txtRoofLayers.setText(--cnt + "");
                            } catch (Exception e) {
                                txtRoofLayers.setText("0");
                            }
                        } else if (view.getId() == txtRoofLayersInc.getId()) {
                            try {
                                int cnt = Integer.parseInt(txtRoofLayers.getText().toString());
                                txtRoofLayers.setText(++cnt + "");
                            } catch (Exception e) {
                                txtRoofLayers.setText("0");
                            }
                        } else if (view.getId() == txtStoryDec.getId()) {
                            try {
                                int cnt = Integer.parseInt(txtStory.getText().toString());
                                if (cnt != 0)
                                    txtStory.setText(--cnt + "");
                            } catch (Exception e) {
                                txtStory.setText("0");
                            }
                        } else if (view.getId() == txtStoryInc.getId()) {
                            try {
                                int cnt = Integer.parseInt(txtStory.getText().toString());
                                txtStory.setText(++cnt + "");
                            } catch (Exception e) {
                                txtStory.setText("0");
                            }
                        }
                        return true; // if you want to handle the touch event
                    default:
                }
                return false;
            }
        };
    }

    private void set_spnrRoofEdgeMetala(ArrayList<String> listarr, Spinner spnrRoofEdgeMetal, int pos) {

        ArrayList<String> spnrRoofEdgeMetalalist = new ArrayList<>();

        for (int i = 0; i < listarr.size(); i++) {
            spnrRoofEdgeMetalalist.add(listarr.get(i));
        }

        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spnrRoofEdgeMetalalist);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
//        spnrRoofEdgeMetal.setAdapter(spinnerArrayAdapter);
        spnrRoofEdgeMetal.setAdapter(new SpinnerAdpt(mContext, spnrRoofEdgeMetalalist));

        spnrRoofEdgeMetal.setSelection(pos);
    }

    private void set_spnrRoofEdgeMetala(ArrayList<String> listarr, Spinner spnrRoofEdgeMetal) {

        ArrayList<String> spnrRoofEdgeMetalalist = new ArrayList<>();

        for (int i = 0; i < listarr.size(); i++) {
            spnrRoofEdgeMetalalist.add(listarr.get(i));
        }

        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spnrRoofEdgeMetalalist);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spnrRoofEdgeMetal.setAdapter(new SpinnerAdpt(mContext, spnrRoofEdgeMetalalist));

    }

    public void setOnTextChangeListner() {
        etFirstInsuredName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String[] interiorArray = getResources().getStringArray(
                        R.array.dmg_interior_damage);
                for (int i = 0; i < interiorArray.length; i++) {
                    String tempValue = interiorArray[i];
                    tempValue = tempValue.replace("[INSURED NAME]",
                            etFirstInsuredName.getEditableText().toString());
                    interiorArray[i] = tempValue;
                }
                ArrayList<String> lst = new ArrayList<>(Arrays
                        .asList(interiorArray));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        AddReportActivity.this,
                        android.R.layout.simple_spinner_item, lst);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrDmgInterior.setAdapter(new SpinnerAdpt(mContext, lst));
            }
        });

        etFirstTolDesc.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                String[] interiorArray = getResources().getStringArray(R.array.misc_title);
                for (int i = 0; i < interiorArray.length; i++) {
                    String tempValue = interiorArray[i];
                    tempValue = tempValue.replace("[XM8_TOL_DESC]",
                            etFirstTolDesc.getEditableText().toString());
                    interiorArray[i] = tempValue;
                }
                ArrayList<String> lst = new ArrayList<String>(Arrays
                        .asList(interiorArray));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        AddReportActivity.this,
                        android.R.layout.simple_spinner_item, lst);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrMiscTitle.setAdapter(adapter);

                btn_misc_title.setText(s.toString());
            }
        });

        etFirstClimantName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (chbxInsuredNameDiff.isChecked()) {

                } else {
                    etFirstInsuredName.setText(s.toString());
                }
            }
        });

        txtAge.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                etMiscDepreciationYear.setText(s.toString());
            }
        });
    }

    Calendar calendar;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_first_date_loss:
                calendar = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, AlertDialog.THEME_DEVICE_DEFAULT_DARK, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        btnFirstDateLoss.setText((i1 + 1) + "/" + i2 + "/" + i);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            case R.id.btn_first_date_inspected:
                calendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(mContext, AlertDialog.THEME_DEVICE_DEFAULT_DARK, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        btnFirstDateInspected.setText((i1 + 1) + "/" + i2 + "/" + i);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            case R.id.btn_first_time_inspected:
                showTimePickerDialog();
                break;
            case R.id.btn_hartford:
                if (isAllReqFieldsFilled()) {
                    if (spReportType.getSelectedItemPosition() != 0 && llReportType.getVisibility() == View.VISIBLE) {
                        prepareCustomReport();
                    } else {
                        prepareReport();
                    }
                }
                break;
            case id.btnRepotz:
                PopupMenu popupMenu = new PopupMenu(mContext, btnRepotz);
                popupMenu.getMenu().add("ClaimMate Report");
                popupMenu.getMenu().add("Other Report");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getTitle().equals("ClaimMate Report")) {
                            onClick(btnHarford);
                        } else {
                            Intent intent = new Intent(mContext, ReportzActivity.class);
                            intent.putExtra("isUpdate", "0");
                            startActivity(intent);
                        }
                        return false;
                    }
                });
                popupMenu.show();
                break;
            case R.id.btnsetting:
                showChangeLangDialog();
                break;
            case id.imgBtnInteriorForward:
//                startActivity(new Intent(mContext, DamageActivity.class));
                break;
            default:
                break;
        }
    }

    private void showTimePickerDialog() {
        final Calendar calendar = Calendar.getInstance();

        TimePickerDialog tpd = new TimePickerDialog(mContext,
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                btnFirstTimeInspected.setText(dateFormat.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        tpd.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.spnr_dmg_back_eleva:
                if (spndmg_elevationarr.get(spnrDmgBackElev.getSelectedItemPosition()).equalsIgnoreCase("Insert custom data")) {
                    etDmgBackElevCustom.setVisibility(View.VISIBLE);
                } else if (spndmg_elevationarr.get(spnrDmgBackElev.getSelectedItemPosition()).equalsIgnoreCase("Add New Option")) {
                    showaddalert(spndmg_elevationarr, spnrDmgBackElev, "dmg_elevation");
                    etDmgBackElevCustom.setVisibility(View.GONE);
                } else {
                    etDmgBackElevCustom.setVisibility(View.GONE);
                }
                break;
            case R.id.spnr_dmg_front_eleva:
                if (spndmg_elevationarr.get(spnrDmgFrontElev.getSelectedItemPosition()).equalsIgnoreCase("Insert custom data")) {
                    etDmgFrontElevCustom.setVisibility(View.VISIBLE);
                } else if (spndmg_elevationarr.get(spnrDmgFrontElev.getSelectedItemPosition()).equalsIgnoreCase("Add New Option")) {
                    showaddalert(spndmg_elevationarr, spnrDmgFrontElev, "dmg_elevation");
                    etDmgFrontElevCustom.setVisibility(View.GONE);
                } else {
                    etDmgFrontElevCustom.setVisibility(View.GONE);
                }
                break;
            case R.id.spnr_dmg_interior:
                if (spndmg_interior_damagearr.get(spnrDmgInterior.getSelectedItemPosition()).equalsIgnoreCase("Insert custom data")) {
                    etDmgInteriorCustom.setVisibility(View.VISIBLE);
                } else if (spndmg_interior_damagearr.get(spnrDmgInterior.getSelectedItemPosition()).equalsIgnoreCase("Add New Option")) {
                    showaddalert(spndmg_interior_damagearr, spnrDmgInterior, "dmg_interior_damage");
                    etDmgInteriorCustom.setVisibility(View.GONE);
                } else {
                    etDmgInteriorCustom.setVisibility(View.GONE);
                }
                break;
            case R.id.spnr_dmg_left_eleva:
                if (spndmg_elevationarr.get(spnrDmgLeftElev.getSelectedItemPosition()).equalsIgnoreCase("Insert custom data")) {
                    etDmgLeftElevCustom.setVisibility(View.VISIBLE);
                } else if (spndmg_elevationarr.get(spnrDmgLeftElev.getSelectedItemPosition()).equalsIgnoreCase("Add New Option")) {
                    showaddalert(spndmg_elevationarr, spnrDmgLeftElev, "dmg_elevation");
                    etDmgLeftElevCustom.setVisibility(View.GONE);
                } else {
                    etDmgLeftElevCustom.setVisibility(View.GONE);
                }
                break;
            case R.id.spnr_dmg_notes:
                if (spndmg_notesarr.get(spnrDmgNotes.getSelectedItemPosition()).equalsIgnoreCase("Insert custom data")) {
                    etDmgNotesCustom.setVisibility(View.VISIBLE);
                } else if (spndmg_notesarr.get(spnrDmgNotes.getSelectedItemPosition()).equalsIgnoreCase("Add New Option")) {
                    showaddalert(spndmg_notesarr, spnrDmgNotes, "dmg_notes");
                    etDmgNotesCustom.setVisibility(View.GONE);
                } else {
                    etDmgNotesCustom.setVisibility(View.GONE);
                }
                break;
            case R.id.spnr_dmg_right_eleva:
                if (spndmg_elevationarr.get(spnrDmgRightElev.getSelectedItemPosition()).equalsIgnoreCase("Insert custom data")) {
                    etDmgRightElevCustom.setVisibility(View.VISIBLE);
                } else if (spndmg_elevationarr.get(spnrDmgRightElev.getSelectedItemPosition()).equalsIgnoreCase("Add New Option")) {
                    showaddalert(spndmg_elevationarr, spnrDmgRightElev, "dmg_elevation");
                    etDmgRightElevCustom.setVisibility(View.GONE);
                } else {
                    etDmgRightElevCustom.setVisibility(View.GONE);
                }
                break;
            case R.id.spnr_dmg_roof:
                if (spndmg_roofarr.get(spnrDmgRoof.getSelectedItemPosition()).equalsIgnoreCase("Insert custom data")) {
                    etDmgRoofCustom.setVisibility(View.VISIBLE);
                } else if (spndmg_roofarr.get(spnrDmgRoof.getSelectedItemPosition()).equalsIgnoreCase("Add New Option")) {
                    showaddalert(spndmg_roofarr, spnrDmgRoof, "dmg_roof");
                    etDmgRoofCustom.setVisibility(View.GONE);
                } else {
                    etDmgRoofCustom.setVisibility(View.GONE);
                }
                break;
            case R.id.spnr_dwl_fifth:
                if (spndwl_fiftharr.get(spnrDwlFifth.getSelectedItemPosition()).equalsIgnoreCase("Insert custom data")) {
                    etDwlFifthCustom.setVisibility(View.VISIBLE);
                } else if (spndwl_fiftharr.get(spnrDwlFifth.getSelectedItemPosition()).equalsIgnoreCase("Add New Option")) {
                    showaddalert(spndwl_fiftharr, spnrDwlFifth, "dwl_fifth");
                    etDwlFifthCustom.setVisibility(View.GONE);
                } else {
                    etDwlFifthCustom.setVisibility(View.GONE);
                }
                break;
            case R.id.spnr_dwl_first:
                if (spndwl_firstarr.get(spnrDwlFirst.getSelectedItemPosition()).equalsIgnoreCase("Insert custom data")) {
                    etDwlFirstCustom.setVisibility(View.VISIBLE);
                } else if (spndwl_firstarr.get(spnrDwlFirst.getSelectedItemPosition()).equalsIgnoreCase("Add New Option")) {
                    showaddalert(spndwl_firstarr, spnrDwlFirst, "dwl_first");
                    etDwlFirstCustom.setVisibility(View.GONE);
                } else {
                    etDwlFirstCustom.setVisibility(View.GONE);
                }
                break;
            case R.id.spnr_dwl_fourth:
                if (spndwl_fourtharr.get(spnrDwlFourth.getSelectedItemPosition()).equalsIgnoreCase("Insert custom data")) {
                    etDwlFourthCustom.setVisibility(View.VISIBLE);
                } else if (spndwl_fourtharr.get(spnrDwlFourth.getSelectedItemPosition()).equalsIgnoreCase("Add New Option")) {
                    showaddalert(spndwl_fourtharr, spnrDwlFourth, "dwl_fourth");
                    etDwlFourthCustom.setVisibility(View.GONE);
                } else {
                    etDwlFourthCustom.setVisibility(View.GONE);
                }
                break;
            case R.id.spnr_dwl_second:
                if (spndwl_secondarr.get(spnrDwlSecond.getSelectedItemPosition()).equalsIgnoreCase("Insert custom data")) {
                    etDwlSecondCustom.setVisibility(View.VISIBLE);
                } else if (spndwl_secondarr.get(spnrDwlSecond.getSelectedItemPosition()).equalsIgnoreCase("Add New Option")) {
                    showaddalert(spndwl_secondarr, spnrDwlSecond, "dwl_second");
                    etDwlSecondCustom.setVisibility(View.GONE);
                } else {
                    etDwlSecondCustom.setVisibility(View.GONE);
                }
                break;
            case R.id.spnr_dwl_third:
                if (spndwl_thirdarr.get(spnrDwlThird.getSelectedItemPosition()).equalsIgnoreCase("Insert custom data")) {
                    etDwlThirdCustom.setVisibility(View.VISIBLE);
                } else if (spndwl_thirdarr.get(spnrDwlThird.getSelectedItemPosition()).equalsIgnoreCase("Add New Option")) {
                    showaddalert(spndwl_thirdarr, spnrDwlThird, "dwl_third");
                    etDwlThirdCustom.setVisibility(View.GONE);
                } else {
                    etDwlThirdCustom.setVisibility(View.GONE);
                }
                break;
            case R.id.spnr_misc_title:
                if (spnmisc_titlearr.get(spnrMiscTitle.getSelectedItemPosition()).equalsIgnoreCase("Insert custom data")) {
                    etMiscTitleCustom.setVisibility(View.VISIBLE);
                } else if (spnmisc_titlearr.get(spnrMiscTitle.getSelectedItemPosition()).equalsIgnoreCase("Add New Option")) {
                    showaddalert(spnmisc_titlearr, spnrMiscTitle, "misc_title");
                    etMiscTitleCustom.setVisibility(View.GONE);
                } else {
                    etMiscTitleCustom.setVisibility(View.GONE);
                }
                break;
            case R.id.spnr_roof_edge_metal:
                String metalSelection = spnrRoofEdgeMetalaarr.get(spnrRoofEdgeMetal.getSelectedItemPosition());
                if (metalSelection.equalsIgnoreCase("Insert custom data")) {
                    etRoofEdgeMetalCustom.setVisibility(View.VISIBLE);
                } else if (metalSelection.equalsIgnoreCase("Add New Option")) {
                    showaddalert(spnrRoofEdgeMetalaarr, spnrRoofEdgeMetal, "roof_edge_metal");
                    etRoofEdgeMetalCustom.setVisibility(View.GONE);
                } else {
                    etRoofEdgeMetalCustom.setVisibility(View.GONE);
                }
                break;
            case R.id.spnr_roof_type:
                if (spnroof_typearr.get(spnrRoofType.getSelectedItemPosition()).equalsIgnoreCase("Insert custom data")) {
                    etRoofTypeCustom.setVisibility(View.VISIBLE);
                } else if (spnroof_typearr.get(spnrRoofType.getSelectedItemPosition()).equalsIgnoreCase("Add New Option")) {
                    showaddalert(spnroof_typearr, spnrRoofType, "roof_type");
                    etRoofTypeCustom.setVisibility(View.GONE);
                } else {
                    etRoofTypeCustom.setVisibility(View.GONE);
                }
                break;
            /*case R.id.spnr_all:
                String selectedItem = spnr_allarr.get(spnr_all.getSelectedItemPosition());
                if (selectedItem.equalsIgnoreCase("Insert custom data")) {
//                    edtAllCustom.setText("");
                    edtAllCustom.setVisibility(View.VISIBLE);
                    btnAddCustom.setVisibility(View.GONE);
                } else {
                    edtAllCustom.setText("");
                    edtAllCustom.setVisibility(View.GONE);
                    btnAddCustom.setVisibility(View.GONE);
                }
                break;*/
            case R.id.spnr_all:
                if (spnr_allarr.get(spnr_all.getSelectedItemPosition()).equalsIgnoreCase("Insert custom data")) {
                    edtAllCustom.setText("");
                    edtAllCustom.setVisibility(View.VISIBLE);
                } else if (spnr_allarr.get(spnr_all.getSelectedItemPosition()).equalsIgnoreCase("Add New Option")) {
                    showaddalert(spnr_allarr, spnr_all, "company");
                    edtAllCustom.setVisibility(View.GONE);
                } else {
                    edtAllCustom.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    private void showaddalert(final ArrayList<String> listarr, final Spinner sp, final String tblname) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddReportActivity.this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Enter Name");

        final EditText input = new EditText(AddReportActivity.this);
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

                    listarr.add(listarr.size() - 2, input.getText().toString().trim());
//							spnrRoofEdgeMetalaarr.add("Insert custom data");
                    set_spnrRoofEdgeMetala(listarr, sp);
                    if (sp == spnrDwlSecond) {
                        dwlSecondSelectPos = listarr.size() - 3;
                        rcDwlSecond.setAdapter(new DwlSecondAdpt());
                        rcDwlSecond.scrollToPosition(dwlSecondSelectPos);
                    } else if (sp == spnrRoofEdgeMetal) {
                        roofEdgeMetalSelectPos = listarr.size() - 3;
                        rcRoofEdgeMetal.setAdapter(new RoofEdgeMetalAdpt());
                        rcRoofEdgeMetal.scrollToPosition(roofEdgeMetalSelectPos);
                    }
                }
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
//                spnrRoofEdgeMetal.setSelection(0);
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case id.chkContractor:
                if (chkContractor.isChecked())
                    llContractor.setVisibility(View.VISIBLE);
                else
                    llContractor.setVisibility(View.GONE);
                break;
            case R.id.chkmortgagee:
                if (chkmortgagee.isChecked())
                    llMortgagee.setVisibility(View.VISIBLE);
                else
                    llMortgagee.setVisibility(View.GONE);
                break;
            case R.id.chbx_misc_aps_damage:
                if (isChecked) {
                    etMiscApsDamage.setVisibility(View.VISIBLE);
                } else {
                    etMiscApsDamage.setVisibility(View.GONE);
                }
                break;
            case R.id.chbx_misc_salvage:
                if (isChecked) {
                    etMiscSalvage.setVisibility(View.VISIBLE);
                } else {
                    etMiscSalvage.setVisibility(View.GONE);
                }
                break;
            case id.chkSubrogation:
                if (isChecked) {
                    edtSubrogation.setVisibility(View.VISIBLE);
                } else {
                    edtSubrogation.setVisibility(View.GONE);
                }
                break;
            case R.id.chbx_misc_depreciation:
                if (isChecked) {
                    etMiscDepreciationYear.setVisibility(View.VISIBLE);
                } else {
                    etMiscDepreciationYear.setVisibility(View.GONE);
                }
                break;
            case R.id.chbx_first_InsuredNameDiff:
                if (isChecked) {
                    etFirstInsuredName.setVisibility(View.VISIBLE);
                    tvInsuredName.setVisibility(View.VISIBLE);
                } else {
                    etFirstInsuredName.setVisibility(View.GONE);
                    tvInsuredName.setVisibility(View.GONE);
                }
                break;
            case R.id.l_min:
                if (l_min.isChecked())
                    llLaborMin.setVisibility(View.VISIBLE);
                else
                    llLaborMin.setVisibility(View.GONE);
            default:
                break;
        }
    }

    public boolean isAllReqFieldsFilled() {
        boolean result = true;

        if (TextUtils.isEmpty(etFirstClimantName.getText())) {
            etFirstClimantName.setError(getString(R.string.error_field_required));
            etFirstClimantName.requestFocus();
            result = false;
        } else if (btnFirstDateLoss.getText().toString().equalsIgnoreCase(btnInitLossDate)) {
            Toast.makeText(AddReportActivity.this, "Please select date of loss.",
                    Toast.LENGTH_SHORT).show();
//            result = false;
        } else if (btnFirstDateInspected.getText().toString().equalsIgnoreCase(btnInitInspectDate)) {
            Toast.makeText(AddReportActivity.this,
                    "Please select date of inspection.", Toast.LENGTH_SHORT)
                    .show();
//            result = false;
        } else if (btnFirstTimeInspected.getText().toString().equalsIgnoreCase(btnInitInspectTime)) {
            Toast.makeText(AddReportActivity.this,
                    "Please select time of inspection.", Toast.LENGTH_SHORT)
                    .show();
//            result = false;
        }

        return result;
    }

    public void prepareReport() {
        String[] tempArray = getResources().getStringArray(R.array.dmg_notes);
        String lastStringOfSpinner = tempArray[tempArray.length - 1];

        // First

//		String finalString = "";
        String B6, G6, C7, G7, I7, C8, C9;
        B6 = "Arrived for scheduled appointment at ";
        G6 = btnFirstTimeInspected.getText().toString();
        C7 = "on";
        G7 = btnFirstDateInspected.getText().toString();
        I7 = (spName.getSelectedItem().toString().equals("None") ? "" : spName.getSelectedItem().toString()) + " " + etFirstInsuredName.getEditableText().toString();

        if (chkContractor.isChecked() && !chbxFirstIsPresent.isChecked()) {
            C8 = " and their contractor " + edtContractorName.getText().toString() + " of " + edtCompanyName.getText().toString() + " were present during inspection.";
        } else if (chkContractor.isChecked() && chbxFirstIsPresent.isChecked()) {
            C8 = " were not present during inspection and asked for me to inspect the loss with their contractor " + edtContractorName.getText().toString() + " of " + edtCompanyName.getText().toString() + ".";
        } else if (chbxFirstIsPresent.isChecked()) {
            C8 = " was not present during inspection and asked for me to inspect the loss alone.";
        } else {
            C8 = " was present during inspection.";
        }
        C9 = " An onsite inspection of the loss was performed and the following was found: ";

        String N6 = B6 + G6 + " " + C7 + " " + G7 + ".  " + I7 + C8 + C9;


        // Dweling
        String B11, C12, D12, F12, G12, C13, C14, H15, H16, B46;
        B11 = "DWELLING INSPECTION: ";
        C12 = txtStory.getText().toString() + " Story ";

        D12 = spndwl_firstarr.get(spnrDwlFirst.getSelectedItemPosition());
        if (D12.equalsIgnoreCase(lastStringOfSpinner)) {
            D12 = etDwlFirstCustom.getEditableText().toString() + "  ";
        } else {
            D12 = D12 + " ";
        }

        F12 = spndwl_secondarr.get(dwlSecondSelectPos);
        if (F12.equalsIgnoreCase(lastStringOfSpinner)) {
            F12 = etDwlSecondCustom.getEditableText().toString() + " ";
        } else {
            F12 = F12 + " ";
        }

        G12 = spndwl_thirdarr.get(spnrDwlThird.getSelectedItemPosition());
        if (G12.equalsIgnoreCase(lastStringOfSpinner)) {
            G12 = etDwlThirdCustom.getEditableText().toString();
        }

        /*C13 = spndwl_fourtharr.get(spnrDwlFourth.getSelectedItemPosition());
        if (C13.equalsIgnoreCase(lastStringOfSpinner)) {
            C13 = etDwlFourthCustom.getEditableText().toString();
        }*/

        C13 = "";
        for (int i = 0; i < arrayListGarage.size(); i++) {
            if (i != 0)
                C13 += "\n";
            C13 += arrayListGarage.get(i).get("1") + " car garage " + arrayListGarage.get(i).get("2");
        }

        for (HashMap<String, String> hm : arrayListGarage)
            Log.i(TAG, "garage = " + hm.get("1") + ", " + hm.get("2"));

        C14 = "-The dwelling appears to be owner occupied on a year round basis. ";

        H15 = "- The exterior of the dwelling is ";
        if (spndwl_fiftharr.get(spnrDwlFifth.getSelectedItemPosition()).equalsIgnoreCase(lastStringOfSpinner)) {
            H15 += etDwlFifthCustom.getEditableText().toString();
        } else {
            H15 += spndwl_fiftharr.get(spnrDwlFifth.getSelectedItemPosition());
        }

        H16 = "- The dwelling roof is ";
        if (spnroof_typearr.get(spnrRoofType.getSelectedItemPosition()).trim().equalsIgnoreCase(lastStringOfSpinner)) {
            H16 += etRoofTypeCustom.getEditableText().toString() + " ";
        } else {
            H16 += spnroof_typearr.get(spnrRoofType.getSelectedItemPosition()) + " ";
        }

        if (spnrRoofEdgeMetalaarr.get(roofEdgeMetalSelectPos).trim().trim().equalsIgnoreCase(lastStringOfSpinner)) {
            H16 += etRoofEdgeMetalCustom.getEditableText().toString();
        } else {
            H16 += spnrRoofEdgeMetalaarr.get(roofEdgeMetalSelectPos);
        }

        if (chbxMiscDepreciation.isChecked()) {
            B46 = "-Depreciation documentation: Based on the age and condition of the materials, depreciation was set at "
                    + etMiscDepreciationYear.getEditableText().toString()
                    + " years.";
        } else {
            B46 = "-Depreciation was not covered at inspection because depreciation is not applied.";
        }

        String N11 = B11 + "\n" + C12 + D12 + F12 + G12 + "\n" + C13 + "\n\n" + C14 + "\n" + H15 + "\n" + H16 + "\n" + B46;
        Log.e("N11==>", "--->" + N11);

        String B18, C19, C20, D20, E20, F20, I20, C21, H21, I21, C22;
        B18 = "ROOFING:";
        C19 = C12 + " Roof with a " + txtRoofPitch.getText().toString() + "/12" + " Roof Pitch";
        C20 = "There is ";
        D20 = txtRoofLayers.getText().toString();
        E20 = " Layer ";
        if (spnroof_typearr.get(spnrRoofType.getSelectedItemPosition()).equalsIgnoreCase(lastStringOfSpinner)) {
            F20 = etRoofTypeCustom.getEditableText().toString();
        } else {
            F20 = spnroof_typearr.get(spnrRoofType.getSelectedItemPosition());
        }
        I20 = " on the roof of the dwelling. ";
        C21 = "Approximate age of the roof in years:";
        H21 = txtAge.getText().toString();
        I21 = "yrs";
        C22 = "ROOF: ";
        if (spndmg_roofarr.get(spnrDmgRoof.getSelectedItemPosition()).trim().length() == 0) {
            C22 += "No roof damage.";
        } else {
            if (spndmg_roofarr.get(spnrDmgRoof.getSelectedItemPosition()).equalsIgnoreCase(lastStringOfSpinner)) {
                C22 += etDmgRoofCustom.getEditableText().toString();
            } else {
                C22 += spndmg_roofarr.get(spnrDmgRoof.getSelectedItemPosition());
            }
        }

        String N16 = "\n" + B18 + "\n" + C19 + " " + "\n" + C20 + D20 + E20 + F20;
        if (spnrRoofEdgeMetalaarr.get(roofEdgeMetalSelectPos).equalsIgnoreCase(lastStringOfSpinner)) {
            N16 += etRoofEdgeMetalCustom.getEditableText().toString();
        } else {
            N16 += spnrRoofEdgeMetalaarr.get(roofEdgeMetalSelectPos);
        }
        N16 += I20 + "\n" + C21 + H21 + I21 + "\n" + C22 + "\n";

        String M16 = etMiscApsDamage.getEditableText().toString().trim();

        M16 += "\n" + C22;

        String B25, E25, B26, E26, B27, E27, B28, E28, B30, E30, F30, G30, B32,
                C32, B33, C33, B36, C36, B38, C38, G38, B40, G40, I40, B42, D42, B44;
        String E24 = "NO STORM RELATED DAMAGE FOUND TO THIS ELEVATION. NO REPAIRS RECOMMENDED.";
        B25 = "Front elevation: ";

        E25 = spndmg_elevationarr.get(spnrDmgFrontElev.getSelectedItemPosition());
        if (E25.trim().length() == 0) {
            E25 = E24;
        } else if (E25.equalsIgnoreCase(lastStringOfSpinner)) {
            E25 = etDmgFrontElevCustom.getEditableText().toString();
        }

        B26 = "Left elevation: ";
        E26 = spndmg_elevationarr.get(spnrDmgLeftElev.getSelectedItemPosition());
        if (E26.trim().length() == 0) {
            E26 = E24;
        } else if (E26.equalsIgnoreCase(lastStringOfSpinner)) {
            E26 = etDmgLeftElevCustom.getEditableText().toString();
        }

        B27 = "Rear elevation: ";
        E27 = spndmg_elevationarr.get(spnrDmgBackElev.getSelectedItemPosition());
        if (E27.trim().length() == 0) {
            E27 = E24;
        } else if (E27.equalsIgnoreCase(lastStringOfSpinner)) {
            E27 = etDmgBackElevCustom.getEditableText().toString();
        }

        B28 = "Right elevation: ";
        E28 = spndmg_elevationarr.get(spnrDmgRightElev.getSelectedItemPosition());
        if (E28.trim().length() == 0) {
            E28 = E24;
        } else if (E28.equalsIgnoreCase(lastStringOfSpinner)) {
            E28 = etDmgRightElevCustom.getEditableText().toString();
        }

        B30 = "Interior of Dwelling: ";
        E30 = spndmg_interior_damagearr.get(spnrDmgInterior.getSelectedItemPosition());
        if (E30.trim().length() == 0) {
            E30 = "No Interior damage reported - Inspection of interior declined. ";
        } else if (E30.equalsIgnoreCase(lastStringOfSpinner)) {
            E30 = etDmgInteriorCustom.getEditableText().toString().trim();
        }

        F30 = "";
        G30 = "";

        B32 = "APS: ";
        if (chbxMiscApsDamage.isChecked()) {
            C32 = etMiscApsDamage.getEditableText().toString().trim();
        } else {
            C32 = "There was no visible damage to the APS Structures or any fencing";
        }
        B33 = "CONTENTS: ";
        if (chbxMiscContents.isChecked()) {
            C33 = " Content damage being claimed on loss for Inside Rep to review- Overview photos of some "
                    + "contents included for Inside rep";
        } else {
            C33 = " No content damage reported by  " + (spName.getSelectedItem().toString().equals("None") ? "" : spName.getSelectedItem().toString()) + " " + etFirstInsuredName.getEditableText().toString().trim();
        }

        B36 = "\nADDITIONAL: ";
        C36 = spndmg_notesarr.get(spnrDmgNotes.getSelectedItemPosition());
        if (C36.trim().length() == 0) {
            C36 = "No other visible damage found.";
        } else if (C36.equalsIgnoreCase(lastStringOfSpinner)) {
            C36 = etDmgNotesCustom.getEditableText().toString().trim();
        }

        B38 = "FIELD REVIEW: ";
        C38 = "The following was reviewed with ";
        if (chkContractor.isChecked() && chbxFirstIsPresent.isChecked()) {
            G38 = edtContractorName.getText().toString() + " was " + edtCompanyName.getText().toString();
        } else {
            G38 = (spName.getSelectedItem().toString().equals("None") ? "" : spName.getSelectedItem().toString()) + " " + etFirstInsuredName.getEditableText().toString().trim();
        }
        B40 = "-The scope of the inspection was reviewed with ";
        G40 = G38;
        I40 = " in person. ";
        B42 = G38;
        D42 = " acknowledged understanding and is in agreement with the scope of repairs.";
        B44 = "-The claim process and file being subject to carriers review were addressed. ";

        String N25 = B25 + E25 + "\n" + B26 + E26 + "\n" + B27 + E27 + "\n"
                + B28 + E28 + "\n\n" + B30 + E30 + F30 + G30 + "\n\n" + B32
                + C32 + "\n" + B33 + C33 + "\n" + B36 + C36 + "\n\n\n" + B38
                + C38 + G38 + "\n" + B40 + G40 + I40 + "\n" + B42 + D42 + "\n"
                + B44;

        String N29, M46;

        String O29 = N6 + "\n\n" + N11 + "\n\n" + N16 + "\n" + N25;
        String M29 = N6 + "\n\n" + N11 + "\n\n" + M16 + "\n" + N25;
        if (chbxRoof.isChecked()) {
            N29 = O29;
        } else {
            N29 = M29;
        }
        String M51, M48, B58, C58, B62, B63, B64, B68, D68, B70, D70;

        M51 = "Discussed with " + (spName.getSelectedItem().toString().equals("None") ? "" : spName.getSelectedItem().toString()) + " " + etFirstInsuredName.getEditableText().toString().trim() + " that I completed my inspection of damage"
                + " to their property. I reviewed the scope of damages with them as noted in the above descriptions. I advised"
                + " that I cannot comment on the payment of coverage but their Hartford claim representative will do so upon"
                + " receipt of my report. I let them know I would be submitting my report and that they should be hearing"
                + " from their Hartford Rep within 6 to 10 business days.  ";

        if (chkmortgagee.isChecked()) {
            if (chkNoMortgagee.isChecked())
                M51 += "Confirmed No mortgage for this property.";
            else
                M51 += "Confirmed that " + edtMortgagee.getText().toString() + " as the mortgage company.";
        } else {

            M51 += "";

        }

        if (l_min.isChecked()) {
            M48 = "LABOR MINIMUMS:  " + edtRemoved.getText().toString() + " Labor Minimums were removed and " + edtAdded.getText().toString() + " labor minimums were added to this estimate.";
        } else {
            M48 = "";
        }

        B58 = "CAUSE OF LOSS: ";
        C58 = etFirstTolDesc.getEditableText().toString() + " - " + btnFirstDateLoss.getText().toString();

        B62 = "SALVAGE: ";

        if (chbxMiscSalvage.isChecked()) {
            B62 += etMiscSalvage.getEditableText().toString();
        } else {
            B62 += "The discarded Materials have no value ";
        }

        B64 = "SUBROGATION: ";
        if (chkSubrogation.isChecked())
            B64 += edtSubrogation.getText().toString();
        else
            B64 += "No subrogation available, this is a weather related claim. ";

        B68 = "OVERHEAD & PROFIT: ";

        D68 = "";

        if (chbxMiscOP.isChecked()) {
            D68 += "- Based on the complexity of work & number of trades Overhead & Profit are necessary & therefore included in this estimate.";
        } else {
            D68 += "- Based on the complexity of work & number of trades Overhead & Profit are not necessary & there for NOT included in this estimate.";
        }

        B70 = "CLAIM COMPLETION: ";

        D70 = " All documents and estimates are being forwarded via electronic upload to " + (spnr_allarr.get(spnr_all.getSelectedItemPosition()).equalsIgnoreCase("Insert custom data") ? edtAllCustom.getText().toString() : spnr_allarr.get(spnr_all.getSelectedItemPosition())) + ".";

        M46 = M51 + "\n\n" + M48 + "\n" + B58 + C58 + "\n" + B62 + "\n" + B64 + "\n" + B68 + D68 + "\n\n" + B70 + D70;

        String N74 = N29 + "\n" + M46;

        String strreport = N74.replace("Hartford", "Inside");

        HashMap<String, String> hm = new HashMap<>();
        hm.put("mr", spName.getSelectedItem().toString());
        hm.put("report_type", "1");
        hm.put("claimant_name", etFirstClimantName.getText().toString());
        hm.put("insuredNameDiffernt", chbxInsuredNameDiff.isChecked() ? "1" : "0");
        hm.put("insuredName", etFirstInsuredName.getText().toString());
        hm.put("causesOfLoss", etFirstTolDesc.getText().toString());
        hm.put("dateLoss", btnFirstDateLoss.getText().toString());
        hm.put("insuredPersonPresent", chbxFirstIsPresent.isChecked() ? "1" : "0");
        hm.put("isMortgagee", chkmortgagee.isChecked() ? "1" : "0");
        hm.put("mortgagee", edtMortgagee.getText().toString());
        hm.put("isNoMortgagee", chkNoMortgagee.isChecked() ? "1" : "0");
        hm.put("dateInspected", btnFirstDateInspected.getText().toString());
        hm.put("timeInspected", btnFirstTimeInspected.getText().toString());
        hm.put("isRoof", chbxRoof.isChecked() ? "1" : "0");
        hm.put("pitch", txtRoofPitch.getText().toString());
        hm.put("layers", txtRoofLayers.getText().toString());
        hm.put("edgeMetal", spnrRoofEdgeMetalaarr.get(roofEdgeMetalSelectPos));
        hm.put("edgeMetalCustom", etRoofEdgeMetalCustom.getText().toString());
        hm.put("type", spnroof_typearr.get(spnrRoofType.getSelectedItemPosition()));
        hm.put("typeCustom", etRoofTypeCustom.getText().toString());
        hm.put("age", txtAge.getText().toString());
        hm.put("story", txtStory.getText().toString());
        hm.put("dwl_first", spndwl_firstarr.get(spnrDwlFirst.getSelectedItemPosition()));
        hm.put("dwl_first_custom", etDwlFirstCustom.getText().toString());
        hm.put("dwl_second", spndwl_secondarr.get(dwlSecondSelectPos));
        hm.put("dwl_second_custom", etDwlSecondCustom.getText().toString());
        hm.put("dwl_third", spndwl_thirdarr.get(spnrDwlThird.getSelectedItemPosition()));
        hm.put("dwl_third_custom", etDwlThirdCustom.getText().toString());

        JSONArray jsonArray = new JSONArray();
        for (HashMap<String, String> hm2 : arrayListGarage) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("1", hm2.get("1"));
                jsonObject.put("2", hm2.get("2"));
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        hm.put("dwl_fourth", jsonArray.toString());
//        hm.put("dwl_fourth", spndwl_fourtharr.get(spnrDwlFourth.getSelectedItemPosition()));
        hm.put("dwl_fourth_custom", etDwlFourthCustom.getText().toString());
        hm.put("dwl_fifth", spndwl_fiftharr.get(spnrDwlFifth.getSelectedItemPosition()));
        hm.put("dwl_fifth_custom", etDwlFifthCustom.getText().toString());
        if (reportModel != null && reportModel.getFrom() != null)
            hm.put("from", reportModel.getFrom());

        HashMap<String, String> hm2 = new HashMap<>();
        hm2.put("dmg_interior", spndmg_interior_damagearr.get(spnrDmgInterior.getSelectedItemPosition()));
        hm2.put("dmg_interior_custom", etDmgInteriorCustom.getText().toString());
        hm2.put("dmg_roof", spndmg_roofarr.get(spnrDmgRoof.getSelectedItemPosition()));
        hm2.put("dmg_roof_custom", etDmgRoofCustom.getText().toString());
        hm2.put("dmg_front_eleva", spndmg_elevationarr.get(spnrDmgFrontElev.getSelectedItemPosition()));
        hm2.put("dmg_front_custom", etDmgFrontElevCustom.getText().toString());
        hm2.put("dmg_left_eleva", spndmg_elevationarr.get(spnrDmgLeftElev.getSelectedItemPosition()));
        hm2.put("dmg_left_custom", etDmgLeftElevCustom.getText().toString());
        hm2.put("dmg_back_eleva", spndmg_elevationarr.get(spnrDmgBackElev.getSelectedItemPosition()));
        hm2.put("dmg_back_custom", etDmgBackElevCustom.getText().toString());
        hm2.put("dmg_right_eleva", spndmg_elevationarr.get(spnrDmgRightElev.getSelectedItemPosition()));
        hm2.put("dmg_right_custom", etDmgRightElevCustom.getText().toString());
        hm2.put("dmg_notes", spndmg_notesarr.get(spnrDmgNotes.getSelectedItemPosition()));
        hm2.put("dmg_notes_custom", etDmgNotesCustom.getText().toString());
        hm2.put("misc_title", spnmisc_titlearr.get(spnrMiscTitle.getSelectedItemPosition()));
        hm2.put("misc_title_custom", etMiscTitleCustom.getText().toString());
        hm2.put("misc_op", chbxMiscOP.isChecked() ? "1" : "0");
        hm2.put("misc_depreciation", chbxMiscDepreciation.isChecked() ? "1" : "0");
        hm2.put("misc_depreciation_year", etMiscDepreciationYear.getText().toString());
        hm2.put("misc_aps_damage", chbxMiscApsDamage.isChecked() ? "1" : "0");
        hm2.put("misc_aps_damage_custome", etMiscApsDamage.getText().toString());
        hm2.put("misc_contents", chbxMiscContents.isChecked() ? "1" : "0");
        hm2.put("misc_salvage", chbxMiscSalvage.isChecked() ? "1" : "0");
        hm2.put("misc_salvage_custom", etMiscSalvage.getText().toString());
        hm2.put("subrogation", chkSubrogation.isChecked() ? "1" : "0");
        hm2.put("subrogation_custom", edtSubrogation.getText().toString());
        hm2.put("LaborMin", l_min.isChecked() ? "1" : "0");
        hm2.put("LaborMinAdded", edtAdded.getText().toString());
        hm2.put("LaborMinRemoved", edtRemoved.getText().toString());
        hm2.put("all", spnr_allarr.get(spnr_all.getSelectedItemPosition()));
        hm2.put("AllCustom", edtAllCustom.getText().toString());
        hm2.put("isContractor", chkContractor.isChecked() ? "1" : "0");
        hm2.put("contractorName", edtContractorName.getText().toString());
        hm2.put("companyName", edtCompanyName.getText().toString());

        Intent verifyActivityIntent = new Intent(this, VerifyReportActivity.class);
        if (reportModel != null)
        {
            Log.e("reportid","=>"+reportModel.getId());
            verifyActivityIntent.putExtra("reportId", reportModel.getId());
        }
        else
        {
            verifyActivityIntent.putExtra("reportId", "");

        }

        verifyActivityIntent.putExtra("exit", directExit);
        verifyActivityIntent.putExtra("report", strreport);
        verifyActivityIntent.putExtra("username", (spName.getSelectedItem().toString().equals("None") ? "" : spName.getSelectedItem().toString()) + " " + etFirstClimantName.getText().toString().trim());
        verifyActivityIntent.putExtra("hm", hm);
        verifyActivityIntent.putExtra("hm2", hm2);
        startActivity(verifyActivityIntent);
    }

    public void prepareCustomReport() {
        String report = claimDbHelper.getCustomReport().get(spReportType.getSelectedItemPosition() - 1).get("Report");

        String insertCustomData = "Insert custom data";

        report = report.replace("#INSPECTION_TIME_AND_DATE#", btnFirstTimeInspected.getText().toString() + " " + btnFirstDateInspected.getText().toString());
        report = report.replace("#INSURED_NAME#", (spName.getSelectedItem().toString().equals("None") ? "" : spName.getSelectedItem().toString()) + " " + etFirstInsuredName.getEditableText().toString());
        report = report.replace("#NUMBER_OF_STORIES#", txtStory.getText().toString());

        if (spndwl_firstarr.get(spnrDwlFirst.getSelectedItemPosition()).equalsIgnoreCase(insertCustomData)) {
            report = report.replace("#TYPE_OF_CONSTRUCTION#", etDwlFirstCustom.getEditableText().toString());
        } else {
            report = report.replace("#TYPE_OF_CONSTRUCTION#", spndwl_firstarr.get(spnrDwlFirst.getSelectedItemPosition()));
        }

        if (spndwl_secondarr.get(dwlSecondSelectPos).equalsIgnoreCase(insertCustomData)) {
            report = report.replace("#TYPE_OF_USE#", etDwlSecondCustom.getEditableText().toString());
        } else {
            report = report.replace("#TYPE_OF_USE#", spndwl_secondarr.get(dwlSecondSelectPos));
        }

        if (spndwl_thirdarr.get(spnrDwlThird.getSelectedItemPosition()).equalsIgnoreCase(insertCustomData)) {
            report = report.replace("#TYPE_OF_USE_2#", etDwlThirdCustom.getEditableText().toString());
        } else {
            report = report.replace("#TYPE_OF_USE_2#", spndwl_thirdarr.get(spnrDwlThird.getSelectedItemPosition()));
        }

        String C13 = "";
        for (int i = 0; i < arrayListGarage.size(); i++) {
            if (i != 0)
                C13 += "\n";
            C13 += arrayListGarage.get(i).get("1") + " car garage " + arrayListGarage.get(i).get("2");
        }
        report = report.replace("#GARAGE#", C13);

        /*if (spndwl_fourtharr.get(spnrDwlFourth.getSelectedItemPosition()).equalsIgnoreCase(insertCustomData)) {
            report = report.replace("#GARAGE#", etDwlFourthCustom.getEditableText().toString());
        } else {
            report = report.replace("#GARAGE#", spndwl_fourtharr.get(spnrDwlFourth.getSelectedItemPosition()));
        }*/

        if (spndwl_fiftharr.get(spnrDwlFifth.getSelectedItemPosition()).equalsIgnoreCase(insertCustomData)) {
            report = report.replace("#EXTERIOR#", etDwlFifthCustom.getEditableText().toString());
        } else {
            report = report.replace("#EXTERIOR#", spndwl_fiftharr.get(spnrDwlFifth.getSelectedItemPosition()));
        }

        if (spnroof_typearr.get(spnrRoofType.getSelectedItemPosition()).trim().equalsIgnoreCase(insertCustomData)) {
            report = report.replace("#ROOFING#", etRoofTypeCustom.getText().toString());
        } else {
            report = report.replace("#ROOFING#", spnroof_typearr.get(spnrRoofType.getSelectedItemPosition()));
        }

        report = report.replace("#PITCH#", txtRoofPitch.getText().toString());
        report = report.replace("#NUMBER_OF_LAYERS#", txtRoofLayers.getText().toString());

        if (spnroof_typearr.get(spnrRoofType.getSelectedItemPosition()).equalsIgnoreCase(insertCustomData)) {
            report = report.replace("#DRIP_EDGE#", etRoofTypeCustom.getText().toString());
        } else {
            report = report.replace("#DRIP_EDGE#", spnroof_typearr.get(spnrRoofType.getSelectedItemPosition()));
        }

        report = report.replace("#AGE#", txtAge.getEditableText().toString());

        if (spndmg_roofarr.get(spnrDmgRoof.getSelectedItemPosition()).trim().length() == 0) {
            report = report.replace("#ROOF_DAMAGE#", "No roof damage.");
        } else {
            if (spndmg_roofarr.get(spnrDmgRoof.getSelectedItemPosition()).equalsIgnoreCase(insertCustomData)) {
                report = report.replace("#ROOF_DAMAGE#", etDmgRoofCustom.getEditableText().toString());
            } else {
                report = report.replace("#ROOF_DAMAGE#", spndmg_roofarr.get(spnrDmgRoof.getSelectedItemPosition()));
            }
        }

        if (spndmg_elevationarr.get(spnrDmgFrontElev.getSelectedItemPosition()).trim().length() == 0) {
            report = report.replace("#FRONT_ELEVATION_DAMAGE#", "NO STORM RELATED DAMAGE FOUND TO THIS ELEVATION. NO REPAIRS RECOMMENDED.");
        } else if (spndmg_elevationarr.get(spnrDmgFrontElev.getSelectedItemPosition()).equalsIgnoreCase(insertCustomData)) {
            report = report.replace("#FRONT_ELEVATION_DAMAGE#", etDmgFrontElevCustom.getEditableText().toString());
        } else {
            report = report.replace("#FRONT_ELEVATION_DAMAGE#", spndmg_elevationarr.get(spnrDmgFrontElev.getSelectedItemPosition()));
        }

        if (spndmg_elevationarr.get(spnrDmgLeftElev.getSelectedItemPosition()).trim().length() == 0) {
            report = report.replace("#LEFT_ELEVATION_DAMAGE#", "NO STORM RELATED DAMAGE FOUND TO THIS ELEVATION. NO REPAIRS RECOMMENDED.");
        } else if (spndmg_elevationarr.get(spnrDmgLeftElev.getSelectedItemPosition()).equalsIgnoreCase(insertCustomData)) {
            report = report.replace("#LEFT_ELEVATION_DAMAGE#", etDmgLeftElevCustom.getEditableText().toString());
        } else {
            report = report.replace("#LEFT_ELEVATION_DAMAGE#", spndmg_elevationarr.get(spnrDmgLeftElev.getSelectedItemPosition()));
        }

        if (spndmg_elevationarr.get(spnrDmgBackElev.getSelectedItemPosition()).trim().length() == 0) {
            report = report.replace("#BACK_ELEVATION_DAMAGE#", "NO STORM RELATED DAMAGE FOUND TO THIS ELEVATION. NO REPAIRS RECOMMENDED.");
        } else if (spndmg_elevationarr.get(spnrDmgBackElev.getSelectedItemPosition()).equalsIgnoreCase(insertCustomData)) {
            report = report.replace("#BACK_ELEVATION_DAMAGE#", etDmgBackElevCustom.getEditableText().toString());
        } else {
            report = report.replace("#BACK_ELEVATION_DAMAGE#", spndmg_elevationarr.get(spnrDmgBackElev.getSelectedItemPosition()));
        }

        if (spndmg_elevationarr.get(spnrDmgRightElev.getSelectedItemPosition()).trim().length() == 0) {
            report = report.replace("#RIGHT_ELEVATION_DAMAGE#", "NO STORM RELATED DAMAGE FOUND TO THIS ELEVATION. NO REPAIRS RECOMMENDED.");
        } else if (spndmg_elevationarr.get(spnrDmgRightElev.getSelectedItemPosition()).equalsIgnoreCase(insertCustomData)) {
            report = report.replace("#RIGHT_ELEVATION_DAMAGE#", etDmgRightElevCustom.getEditableText().toString());
        } else {
            report = report.replace("#RIGHT_ELEVATION_DAMAGE#", spndmg_elevationarr.get(spnrDmgRightElev.getSelectedItemPosition()));
        }

        if (spndmg_interior_damagearr.get(spnrDmgInterior.getSelectedItemPosition()).trim().length() == 0) {
            report = report.replace("#INTERIOR_DAMAGE#", "No Interior damage reported - Inspection of interior declined. ");
        } else if (spndmg_interior_damagearr.get(spnrDmgInterior.getSelectedItemPosition()).equalsIgnoreCase(insertCustomData)) {
            report = report.replace("#INTERIOR_DAMAGE#", etDmgInteriorCustom.getEditableText().toString().trim());
        } else {
            report = report.replace("#INTERIOR_DAMAGE#", spndmg_interior_damagearr.get(spnrDmgInterior.getSelectedItemPosition()));
        }

        if (chbxMiscApsDamage.isChecked()) {
            report = report.replace("#OTHER_STRUCTURES_DAMAGE#", etMiscApsDamage.getText().toString().trim());
        } else {
            report = report.replace("#OTHER_STRUCTURES_DAMAGE#", "There was no visible damage to the APS Structures or any fencing");
        }

        if (chbxMiscContents.isChecked()) {
            report = report.replace("#CONTENTS_DAMAGE#", "Content damage being claimed on loss for Inside Rep to review- Overview photos of some "
                    + "contents included for Inside rep");
        } else {
            report = report.replace("#CONTENTS_DAMAGE#", " No content damage reported by  " + (spName.getSelectedItem().toString().equals("None") ? "" : spName.getSelectedItem().toString()) + " " + etFirstInsuredName.getEditableText().toString().trim());
        }

        if (spndmg_notesarr.get(spnrDmgNotes.getSelectedItemPosition()).trim().length() == 0) {
            report = report.replace("#NOTES#", "No other visible damage found.");
        } else if (spndmg_notesarr.get(spnrDmgNotes.getSelectedItemPosition()).equalsIgnoreCase(insertCustomData)) {
            report = report.replace("#NOTES#", etDmgNotesCustom.getEditableText().toString().trim());
        } else {
            report = report.replace("#NOTES#", spndmg_notesarr.get(spnrDmgNotes.getSelectedItemPosition()));
        }

        if (chkmortgagee.isChecked()) {
            if (chkNoMortgagee.isChecked())
                report = report.replace("#MORTGAGE#", "Confirmed No mortgage for this property.");
            else
                report = report.replace("#MORTGAGE#", "Confirmed that " + edtMortgagee.getText().toString() + " as the mortgage company.");
        } else {
            report = report.replace("#MORTGAGE#", "");
        }

        if (l_min.isChecked()) {
            report = report.replace("#LABOR_MIN_REMOVED#", edtRemoved.getText().toString());
            report = report.replace("#LABOR_MIN_ADDED#", edtAdded.getText().toString());
        } else {
            report = report.replace("#LABOR_MIN_REMOVED#", "");
            report = report.replace("#LABOR_MIN_ADDED#", "");
        }

        report = report.replace("#CAUSE_OF_LOSS#", etFirstTolDesc.getText().toString() + " - " + btnFirstDateLoss.getText().toString());

        if (chbxMiscSalvage.isChecked()) {
            report = report.replace("#SALVAGE#", etMiscSalvage.getText().toString());
        } else {
            report = report.replace("#SALVAGE#", "The discarded Materials have no value");
        }

        if (chkSubrogation.isChecked())
            report = report.replace("#SUBROGATION#", edtSubrogation.getText().toString());
        else
            report = report.replace("#SUBROGATION#", "No subrogation available, this is a weather related claim.");

        if (chbxMiscOP.isChecked()) {
            report = report.replace("#OVERHEAD_AND_PROFIT#", "- Based on the complexity of work & number of trades Overhead & Profit are necessary & therefore included in this estimate.");
        } else {
            report = report.replace("#OVERHEAD_AND_PROFIT#", "- Based on the complexity of work & number of trades Overhead & Profit are not necessary & there for NOT included in this estimate.");
        }

        report = report.replace("#CONTRACTOR_NAME#", edtContractorName.getText().toString());
        report = report.replace("#COMPANY_NAME#", (spnr_allarr.get(spnr_all.getSelectedItemPosition()).equalsIgnoreCase("Insert custom data") ? edtAllCustom.getText().toString() : spnr_allarr.get(spnr_all.getSelectedItemPosition())));

        Intent intent = new Intent(mContext, ReportzActivity.class);
        intent.putExtra("isUpdate", "0");
        intent.putExtra("report", report);
        startActivity(intent);
    }

    public void showChangeLangDialog() {

        String stremail = lastpathpf.getString("toemail", "claimzapp@gmail.com");
        String strdate = lastpathpf.getString("dateofloss", "11/21/2017");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(layout.settingalert, null);
        dialogBuilder.setView(dialogView);

        Button btnaddvalue = dialogView.findViewById(R.id.btnaddvalue);
        btndefaultdateofloss = dialogView.findViewById(R.id.btndefaultdateofloss);
        btndefaultdateofloss.setText(strdate);

        edtAllCustomD = dialogView.findViewById(R.id.edtAllCustomD);
        btnAddCustomD = dialogView.findViewById(R.id.btnAddCustomD);
        spCompany = dialogView.findViewById(R.id.spCompany);

        arrayListCompany = claimDbHelper.getCustomData(true);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListCompany);
        SpinnerAdpt spinnerAdpt = new SpinnerAdpt(mContext, arrayListCompany);
//        spinnerAdpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spCompany.setAdapter(spinnerAdpt);
        spCompany.setOnItemSelectedListener(this);
        spCompany.setSelection(PrefManager.getCompany());

        final EditText txtemail = dialogView.findViewById(R.id.txtemail);
        txtemail.setText(stremail);

        dialogBuilder.setTitle("Setting");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                lastimageeditor.putString("toemail", txtemail.getText().toString().trim());
                lastimageeditor.commit();
                //do something with edt.getText().toString();
                dialog.dismiss();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                //pass
            }
        });
        final AlertDialog b = dialogBuilder.create();
        b.show();


        btnaddvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tablelist = new Intent(getApplicationContext(), TableNameListActivity.class);
                startActivity(tablelist);
                b.dismiss();
            }
        });

        btndefaultdateofloss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                DatePickerDialogTheme2 dialogfragment = new DatePickerDialogTheme2();
//                dialogfragment.show(getFragmentManager(), "Theme 2");

                defaultDateDateOfLoss();
            }
        });
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void defaultDateDateOfLoss() {
        calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, AlertDialog.THEME_DEVICE_DEFAULT_DARK, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                btndefaultdateofloss.setText((month + 1) + "/" + day + "/" + year);

                btnFirstDateLoss.setText(btndefaultdateofloss.getText().toString().trim());

                lastimageeditor.putString("dateofloss", "" + (month + 1) + "/" + day + "/" + year);
                lastimageeditor.commit();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private class SelectGarageAdpt extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayListGarage.size();
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

            view = LayoutInflater.from(mContext).inflate(layout.item_garage, null);

            final TextView txtCarDec = view.findViewById(R.id.txtCarDec);
            final TextView txtCar = view.findViewById(R.id.txtCar);
            final TextView txtCarInc = view.findViewById(R.id.txtCarInc);
            final TextView txtAddGarage = view.findViewById(R.id.txtAddGarage);
            CheckBox chkAttached = view.findViewById(R.id.chkAttached);

            if (i != 0)
                txtAddGarage.setText("-");

            final HashMap<String, String> hmData = arrayListGarage.get(i);
            txtCar.setText(hmData.get("1"));
            chkAttached.setChecked(hmData.get("2").equals("attached"));

            chkAttached.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b)
                        hmData.put("2", "attached");
                    else
                        hmData.put("2", "detached");
                    arrayListGarage.set(i, hmData);
                }
            });

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getId() == txtCarDec.getId()) {
                        try {
                            int cnt = Integer.parseInt(txtCar.getText().toString());
                            if (cnt != 0)
                                txtCar.setText(--cnt + "");
                        } catch (Exception e) {
                            txtCar.setText("0");
                        }
                        hmData.put("1", txtCar.getText().toString());
                        arrayListGarage.set(i, hmData);
                    } else if (view.getId() == txtCarInc.getId()) {
                        try {
                            int cnt = Integer.parseInt(txtCar.getText().toString());
                            txtCar.setText(++cnt + "");
                        } catch (Exception e) {
                            txtCar.setText("0");
                        }
                        hmData.put("1", txtCar.getText().toString());
                        arrayListGarage.set(i, hmData);
                    } else if (view.getId() == txtAddGarage.getId()) {
                        if (txtAddGarage.getText().equals("+")) {
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("1", "0");
                            hm.put("2", "detached");
                            arrayListGarage.add(hm);
                        } else {
                            arrayListGarage.remove(i);
                        }
                        selectGarageAdpt.notifyDataSetChanged();
                        Utility.setListViewHeightBasedOnChildren(listGarage);
                    }
                }
            };

            txtCarDec.setOnClickListener(onClickListener);
            txtCarInc.setOnClickListener(onClickListener);
            txtAddGarage.setOnClickListener(onClickListener);
            return view;
        }
    }

    private class RoofEdgeMetalAdpt extends RecyclerView.Adapter<RoofEdgeMetalAdpt.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(layout.item_date_list, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.txtItem.setText(spnrRoofEdgeMetalaarr.get(position));

            if (position == roofEdgeMetalSelectPos) {
                holder.txtItem.setBackgroundResource(R.drawable.fill_round_shape);
                holder.txtItem.setTextColor(getResources().getColor(R.color.white));
            } else {
                holder.txtItem.setBackgroundResource(R.drawable.rect_black_border);
                holder.txtItem.setTextColor(getResources().getColor(R.color.black));
            }

            holder.txtItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (roofEdgeMetalSelectPos == position)
                        return;
                    if (!spnrRoofEdgeMetalaarr.get(position).equalsIgnoreCase("Add New Option"))
                        roofEdgeMetalSelectPos = position;

                    if (spnrRoofEdgeMetalaarr.get(position).equalsIgnoreCase("Insert custom data")) {
                        etRoofEdgeMetalCustom.setVisibility(View.VISIBLE);
                    } else if (spnrRoofEdgeMetalaarr.get(position).equalsIgnoreCase("Add New Option")) {
                        showaddalert(spnrRoofEdgeMetalaarr, spnrRoofEdgeMetal, "roof_edge_metal");
                        etRoofEdgeMetalCustom.setVisibility(View.GONE);
                    } else {
                        etRoofEdgeMetalCustom.setVisibility(View.GONE);
                    }
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return spnrRoofEdgeMetalaarr.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView txtItem;

            public ViewHolder(View itemView) {
                super(itemView);
                txtItem = itemView.findViewById(R.id.txtItem);
            }
        }
    }

    private class DwlSecondAdpt extends RecyclerView.Adapter<DwlSecondAdpt.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(layout.item_date_list, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.txtItem.setText(spndwl_secondarr.get(position));

            if (position == dwlSecondSelectPos) {
                holder.txtItem.setBackgroundResource(R.drawable.fill_round_shape);
                holder.txtItem.setTextColor(getResources().getColor(R.color.white));
            } else {
                holder.txtItem.setBackgroundResource(R.drawable.rect_black_border);
                holder.txtItem.setTextColor(getResources().getColor(R.color.black));
            }

            holder.txtItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dwlSecondSelectPos == position)
                        return;
                    if (!spndwl_secondarr.get(position).equalsIgnoreCase("Add New Option"))
                        dwlSecondSelectPos = position;

                    if (spndwl_secondarr.get(position).equalsIgnoreCase("Insert custom data")) {
                        etDwlSecondCustom.setVisibility(View.VISIBLE);
                    } else if (spndwl_secondarr.get(position).equalsIgnoreCase("Add New Option")) {
                        showaddalert(spndwl_secondarr, spnrDwlSecond, "dwl_second");
                        etDwlSecondCustom.setVisibility(View.GONE);
                    } else {
                        etDwlSecondCustom.setVisibility(View.GONE);
                    }
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return spndwl_secondarr.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView txtItem;

            public ViewHolder(View itemView) {
                super(itemView);
                txtItem = itemView.findViewById(R.id.txtItem);
            }
        }
    }
}
