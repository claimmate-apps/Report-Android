package com.cwclaims.claimapp.activity;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.adpts.SpinnerAdpt;
import com.cwclaims.claimapp.db.ClaimSqlLiteDbHelper;
import com.cwclaims.claimapp.models.ReportModel;
import com.cwclaims.claimapp.other.PrefManager;
import com.cwclaims.claimapp.other.Utility;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class SettingActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private final String TAG = "SettingActivity";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();
        setOnTextChangeListner();
    }

    @Override
    protected void onStart() {
        super.onStart();

        arrayReportType = new ArrayList<>();
        arrayReportType.add("ClaimMate Report");
        for (HashMap<String, String> hm : claimDbHelper.getCustomReport()) {
            arrayReportType.add(hm.get("ReportName"));
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayReportType);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spReportType.setAdapter(spinnerArrayAdapter);

        setData();
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void setData() {

        edtEmail.setText(lastpathpf.getString("toemail", "claimzapp@gmail.com"));
        hideSoftKeyboard();

        ReportModel reportModel;

        if (PrefManager.getSetting().equals(""))
            return;
        else
            reportModel = new Gson().fromJson(PrefManager.getSetting(), ReportModel.class);

        for (int i = 0; i < arrayNames.size(); i++) {
            if (reportModel.getMr().equals(arrayNames.get(i))) {
                spName.setSelection(i);
                break;
            }
        }
        etFirstClimantName.setText(reportModel.getClaimant_name());
        chbxInsuredNameDiff.setChecked(reportModel.getInsuredNameDiffernt().equals("1") ? true : false);
        etFirstInsuredName.setText(reportModel.getInsuredName());
        etFirstTolDesc.setText(reportModel.getCausesOfLoss());
        btnFirstDateLoss.setText(reportModel.getDateLoss());
        chbxFirstIsPresent.setChecked(reportModel.getInsuredPersonPresent().equals("1") ? true : false);
        chkmortgagee.setChecked(reportModel.getIsMortgagee().equals("1") ? true : false);
        edtMortgagee.setText(reportModel.getMortgagee());
        chkNoMortgagee.setChecked(reportModel.getIsNoMortgagee().equals("1") ? true : false);
        chkContractor.setChecked(reportModel.getIsContractor().equals("1") ? true : false);
        edtContractorName.setText(reportModel.getContractorName());
        edtCompanyName.setText(reportModel.getCompanyName());
        btnFirstDateInspected.setText(reportModel.getDateInspected());
        btnFirstTimeInspected.setText(reportModel.getTimeInspected());
        chbxRoof.setChecked(reportModel.getIsRoof().equals("1") ? true : false);
        etRoofPitch.setText(reportModel.getPitch());
        etRoofLayers.setText(reportModel.getLayers());

        for (int i = 0; i < spnrRoofEdgeMetalaarr.size(); i++) {
            if (spnrRoofEdgeMetalaarr.get(i).equals(reportModel.getEdgeMetal())) {
                spnrRoofEdgeMetal.setSelection(i);
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

        etRoofAge.setText(reportModel.getAge());
        etDwlStory.setText(reportModel.getStory());

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

        for (int i = 0; i < spndwl_fourtharr.size(); i++) {
            if (spndwl_fourtharr.get(i).equals(reportModel.getDwl_fourth())) {
                spnrDwlFourth.setSelection(i);
                break;
            }
        }
        etDwlFourthCustom.setText(reportModel.getDwl_fourth_custom());

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

        spReportType.setSelection(Integer.parseInt(reportModel.getReport_type()));
    }

    SharedPreferences lastpathpf;
    SharedPreferences.Editor lastimageeditor;

    Calendar calendar;

    ImageView imgBack;

    LinearLayout llMain;

    // First
    Spinner spName;
    LinearLayout llMortgagee, llDateTime, llContractor;
    EditText edtEmail, etFirstClimantName, etFirstTolDesc, etFirstInsuredName, etFirstMortgagee, edtMortgagee, edtAdded, edtRemoved, edtContractorName, edtCompanyName;
    Button btnFirstDateLoss, btnFirstDateInspected, btnFirstTimeInspected;
    CheckBox chbxFirstIsPresent, chbxInsuredNameDiff, chkmortgagee, chkNoMortgagee, chkDateTime, chkContractor;
    TextView tvInsuredName;

    // Roof
    CheckBox chbxRoof;
    EditText etRoofPitch, etRoofLayers;
    Spinner spnrRoofEdgeMetal, spnrRoofType;
    EditText etRoofEdgeMetalCustom, etRoofTypeCustom;
    EditText etRoofAge;

    // Dweling
    EditText etDwlStory;
    Spinner spnrDwlFirst, spnrDwlSecond, spnrDwlThird, spnrDwlFourth, spnrDwlFifth;
    EditText etDwlFirstCustom, etDwlSecondCustom, etDwlThirdCustom, etDwlFourthCustom,
            etDwlFifthCustom;

    // Damage
    Spinner spnrDmgInterior, spnrDmgRoof, spnrDmgFrontElev, spnrDmgBackElev,
            spnrDmgLeftElev, spnrDmgRightElev, spnrDmgNotes;
    EditText etDmgInteriorCustom, etDmgRoofCustom, etDmgFrontElevCustom, etDmgBackElevCustom,
            etDmgLeftElevCustom, etDmgRightElevCustom, etDmgNotesCustom;

    // Misc
    Spinner spnrMiscTitle, spnr_all;
    EditText etMiscTitleCustom;
    LinearLayout llLaborMin;
    CheckBox chbxMiscOP, chbxMiscDepreciation, chbxMiscApsDamage,
            chbxMiscContents, chbxMiscSalvage, chkSubrogation, l_min;
    EditText etMiscDepreciationYear, etMiscApsDamage, etMiscSalvage, edtSubrogation,
            etMiscLaborMinIncl, etMiscLaborMinRemove, edtAllCustom;

    Spinner spReportType;
    Button btnCreateNewReport;

    Button btnDone;

    private ArrayList<String> arrayNames, arrayReportType;
    ArrayList<String> spnrRoofEdgeMetalaarr, spnroof_typearr, spndwl_firstarr, spndwl_secondarr, spndwl_thirdarr;
    ArrayList<String> spndwl_fourtharr, spndwl_fiftharr, spndmg_interior_damagearr, spndmg_roofarr, spndmg_elevationarr, spndmg_notesarr, spnmisc_titlearr;
    ArrayList<String> spnr_allarr;

    private void init() {
        mContext = SettingActivity.this;

        lastpathpf = getSharedPreferences("claimmateform", Context.MODE_PRIVATE);
        lastimageeditor = lastpathpf.edit();
        copydb();

        llMain = findViewById(R.id.llMain);
        imgBack = findViewById(R.id.imgBack);

        // First
        edtEmail = findViewById(R.id.edtEmail);
        etFirstClimantName = findViewById(R.id.et_first_ClaimantName);
        chbxInsuredNameDiff = findViewById(R.id.chbx_first_InsuredNameDiff);
        tvInsuredName = findViewById(R.id.tv_first_InsuredName);
        etFirstInsuredName = findViewById(R.id.et_first_InsuredName);
        spName = findViewById(R.id.spName);
        chkmortgagee = findViewById(R.id.chkmortgagee);
        chkNoMortgagee = findViewById(R.id.chkNoMortgagee);
        edtMortgagee = findViewById(R.id.edtMortgagee);
        llMortgagee = findViewById(R.id.llMortgagee);
        etFirstTolDesc = findViewById(R.id.et_first_TolDesc);
        btnFirstDateLoss = findViewById(R.id.btn_first_date_loss);
        chkContractor = findViewById(R.id.chkContractor);
        llContractor = findViewById(R.id.llContractor);
        edtContractorName = findViewById(R.id.edtContractorName);
        edtCompanyName = findViewById(R.id.edtCompanyName);

        String strdate = lastpathpf.getString("dateofloss", "11/21/2017");
        btnFirstDateLoss.setText(strdate);

        etFirstMortgagee = findViewById(R.id.et_first_Mortgagee);
        chkDateTime = findViewById(R.id.chkDateTime);
        llDateTime = findViewById(R.id.llDateTime);
        btnFirstDateInspected = findViewById(R.id.btn_first_date_inspected);
        btnFirstTimeInspected = findViewById(R.id.btn_first_time_inspected);
        chbxFirstIsPresent = findViewById(R.id.chbx_first_IsPresent);

        // Roof
        chbxRoof = findViewById(R.id.chbx_roof);
        etRoofPitch = findViewById(R.id.et_roof_Pitch);
        etRoofLayers = findViewById(R.id.et_roof_Layers);
        spnrRoofEdgeMetal = findViewById(R.id.spnr_roof_edge_metal);
        spnrRoofType = findViewById(R.id.spnr_roof_type);

        etRoofEdgeMetalCustom = findViewById(R.id.et_roof_edge_metal_custom);
        etRoofTypeCustom = findViewById(R.id.et_roof_type_custom);
        etRoofAge = findViewById(R.id.et_roof_age);

        // Dweling
        etDwlStory = findViewById(R.id.et_dwl_story);
        spnrDwlFirst = findViewById(R.id.spnr_dwl_first);
        spnrDwlSecond = findViewById(R.id.spnr_dwl_second);
        spnrDwlThird = findViewById(R.id.spnr_dwl_third);
        spnrDwlFourth = findViewById(R.id.spnr_dwl_fourth);
        spnrDwlFifth = findViewById(R.id.spnr_dwl_fifth);

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
        l_min = findViewById(R.id.l_min);
        edtAdded = findViewById(R.id.edtAdded);
        edtRemoved = findViewById(R.id.edtRemoved);
        llLaborMin = findViewById(R.id.llLaborMin);
        spnr_all = findViewById(R.id.spnr_all);
        edtAllCustom = findViewById(R.id.edtAllCustom);

        spReportType = findViewById(R.id.spReportType);
        btnCreateNewReport = findViewById(R.id.btnCreateNewReport);

        btnDone = findViewById(R.id.btnDone);

        arrayNames = new ArrayList();
        arrayNames.add("Mr.");
        arrayNames.add("Ms.");
        arrayNames.add("Mr. & Ms.");
        arrayNames.add("None");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spName.setAdapter(spinnerArrayAdapter);

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

        getroof_edge_metalvalue("roof_edge_metal", spnrRoofEdgeMetalaarr, spnrRoofEdgeMetal);
        getroof_edge_metalvalue("roof_type", spnroof_typearr, spnrRoofType);
        getroof_edge_metalvalue("dwl_first", spndwl_firstarr, spnrDwlFirst);
        getroof_edge_metalvalue("dwl_second", spndwl_secondarr, spnrDwlSecond);
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

        // Button click listner
        btnFirstDateInspected.setOnClickListener(this);
        btnFirstDateLoss.setOnClickListener(this);
        btnFirstTimeInspected.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        btnCreateNewReport.setOnClickListener(this);

        // Checkbox listner
        chkDateTime.setOnCheckedChangeListener(this);
        chkmortgagee.setOnCheckedChangeListener(this);
        chbxInsuredNameDiff.setOnCheckedChangeListener(this);
        chbxMiscApsDamage.setOnCheckedChangeListener(this);
        chbxMiscSalvage.setOnCheckedChangeListener(this);
        chkSubrogation.setOnCheckedChangeListener(this);
        chbxMiscDepreciation.setOnCheckedChangeListener(this);
        l_min.setOnCheckedChangeListener(this);
        chkContractor.setOnCheckedChangeListener(this);

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

        Calendar c = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        btnFirstTimeInspected.setText(dateFormat.format(c.getTime()));

        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        btnFirstDateInspected.setText(dateFormat.format(c.getTime()));

    }

    String myPath;
    ClaimSqlLiteDbHelper claimDbHelper;
    SQLiteDatabase DB;

    private void opendatabase() {

        myPath = claimDbHelper.claimdb_PATH + claimDbHelper.claimdb_NAME;
        DB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
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
//        spnrRoofEdgeMetal.setAdapter(spinnerArrayAdapter);
        spnrRoofEdgeMetal.setAdapter(new SpinnerAdpt(mContext, spnrRoofEdgeMetalalist));

    }

    private void getroof_edge_metalvalue(String tblname, ArrayList<String> listarr, Spinner sp) {

        if (listarr.size() > 0) {
            set_spnrRoofEdgeMetala(listarr, sp, 1);
            return;
        }

        opendatabase();

        int item = 0;
        int selectitem = 0;

        String SELECT_SQL = "SELECT * FROM " + tblname;
        Cursor Cur = DB.rawQuery(SELECT_SQL, null);
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
                        mContext,
                        android.R.layout.simple_spinner_item, lst);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrMiscTitle.setAdapter(adapter);
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

        etRoofAge.addTextChangedListener(new TextWatcher() {

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

    @Override
    public void onClick(View view) {
        if (view.getId() == imgBack.getId()) {
            onBackPressed();
        } else if (view.getId() == btnDone.getId()) {
            saveData();
        } else if (view.getId() == btnCreateNewReport.getId()) {
            startActivity(new Intent(mContext, CustomReportActivity.class));
        } else {
            switch (view.getId()) {
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
                default:
                    break;
            }
        }
    }

    private void saveData() {
        lastimageeditor.putString("toemail", edtEmail.getText().toString().trim());
        lastimageeditor.commit();

        ReportModel reportModel = new ReportModel();

        reportModel.setMr(spName.getSelectedItem().toString());
        reportModel.setClaimant_name(etFirstClimantName.getText().toString());
        reportModel.setInsuredNameDiffernt(chbxInsuredNameDiff.isChecked() ? "1" : "0");
        reportModel.setInsuredName(etFirstInsuredName.getText().toString());
        reportModel.setCausesOfLoss(etFirstTolDesc.getText().toString());
        reportModel.setDateLoss(btnFirstDateLoss.getText().toString());
        reportModel.setInsuredPersonPresent(chbxFirstIsPresent.isChecked() ? "1" : "0");
        reportModel.setIsMortgagee(chkmortgagee.isChecked() ? "1" : "0");
        reportModel.setMortgagee(edtMortgagee.getText().toString());
        reportModel.setIsNoMortgagee(chkNoMortgagee.isChecked() ? "1" : "0");

        reportModel.setIsContractor(chkContractor.isChecked() ? "1" : "0");
        reportModel.setContractorName(edtContractorName.getText().toString());
        reportModel.setCompanyName(edtCompanyName.getText().toString());

        if (chkDateTime.isChecked()) {
            reportModel.setDateInspected("");
            reportModel.setTimeInspected("");
        } else {
            reportModel.setDateInspected(btnFirstDateInspected.getText().toString());
            reportModel.setTimeInspected(btnFirstTimeInspected.getText().toString());
        }
        reportModel.setIsRoof(chbxRoof.isChecked() ? "1" : "0");
        reportModel.setPitch(etRoofPitch.getText().toString());
        reportModel.setLayers(etRoofLayers.getText().toString());
        reportModel.setEdgeMetal(spnrRoofEdgeMetalaarr.get(spnrRoofEdgeMetal.getSelectedItemPosition()));
        reportModel.setEdgeMetalCustom(etRoofEdgeMetalCustom.getText().toString());
        reportModel.setType(spnroof_typearr.get(spnrRoofType.getSelectedItemPosition()));
        reportModel.setTypeCustom(etRoofTypeCustom.getText().toString());
        reportModel.setAge(etRoofAge.getText().toString());
        reportModel.setStory(etDwlStory.getText().toString());
        reportModel.setDwl_first(spndwl_firstarr.get(spnrDwlFirst.getSelectedItemPosition()));
        reportModel.setDwl_first_custom(etDwlFirstCustom.getText().toString());
        reportModel.setDwl_second(spndwl_secondarr.get(spnrDwlSecond.getSelectedItemPosition()));
        reportModel.setDwl_second_custom(etDwlSecondCustom.getText().toString());
        reportModel.setDwl_third(spndwl_thirdarr.get(spnrDwlThird.getSelectedItemPosition()));
        reportModel.setDwl_third_custom(etDwlThirdCustom.getText().toString());
        reportModel.setDwl_fourth(spndwl_fourtharr.get(spnrDwlFourth.getSelectedItemPosition()));
        reportModel.setDwl_fourth_custom(etDwlFourthCustom.getText().toString());
        reportModel.setDwl_fifth(spndwl_fiftharr.get(spnrDwlFifth.getSelectedItemPosition()));
        reportModel.setDwl_fifth_custom(etDwlFifthCustom.getText().toString());

        reportModel.setDmg_interior(spndmg_interior_damagearr.get(spnrDmgInterior.getSelectedItemPosition()));
        reportModel.setDmg_interior_custom(etDmgInteriorCustom.getText().toString());
        reportModel.setDmg_roof(spndmg_roofarr.get(spnrDmgRoof.getSelectedItemPosition()));
        reportModel.setDmg_roof_custom(etDmgRoofCustom.getText().toString());
        reportModel.setDmg_right_eleva(spndmg_elevationarr.get(spnrDmgFrontElev.getSelectedItemPosition()));
        reportModel.setDmg_front_custom(etDmgFrontElevCustom.getText().toString());
        reportModel.setDmg_left_eleva(spndmg_elevationarr.get(spnrDmgLeftElev.getSelectedItemPosition()));
        reportModel.setDmg_left_custom(etDmgLeftElevCustom.getText().toString());
        reportModel.setDmg_back_eleva(spndmg_elevationarr.get(spnrDmgBackElev.getSelectedItemPosition()));
        reportModel.setDmg_back_custom(etDmgBackElevCustom.getText().toString());
        reportModel.setDmg_right_eleva(spndmg_elevationarr.get(spnrDmgRightElev.getSelectedItemPosition()));
        reportModel.setDmg_right_custom(etDmgRightElevCustom.getText().toString());
        reportModel.setDmg_notes(spndmg_notesarr.get(spnrDmgNotes.getSelectedItemPosition()));
        reportModel.setDmg_notes_custom(etDmgNotesCustom.getText().toString());
        reportModel.setMisc_title(spnmisc_titlearr.get(spnrMiscTitle.getSelectedItemPosition()));
        reportModel.setMisc_title_custom(etMiscTitleCustom.getText().toString());
        reportModel.setMisc_op(chbxMiscOP.isChecked() ? "1" : "0");
        reportModel.setMisc_depreciation(chbxMiscDepreciation.isChecked() ? "1" : "0");
        reportModel.setMisc_depreciation_year(etMiscDepreciationYear.getText().toString());
        reportModel.setMisc_aps_damage(chbxMiscApsDamage.isChecked() ? "1" : "0");
        reportModel.setMisc_aps_damage_custom(etMiscApsDamage.getText().toString());
        reportModel.setMisc_contents(chbxMiscContents.isChecked() ? "1" : "0");
        reportModel.setMisc_salvage(chbxMiscSalvage.isChecked() ? "1" : "0");
        reportModel.setMisc_salvage_custom(etMiscSalvage.getText().toString());
        reportModel.setSubrogation(chkSubrogation.isChecked() ? "1" : "0");
        reportModel.setSubrogation_custom(edtSubrogation.getText().toString());
        reportModel.setLaborMin(l_min.isChecked() ? "1" : "0");
        reportModel.setLaborMinAdded(edtAdded.getText().toString());
        reportModel.setLaborMinRemoved(edtRemoved.getText().toString());
        reportModel.setAll(spnr_allarr.get(spnr_all.getSelectedItemPosition()));
        reportModel.setAllCustom(edtAllCustom.getText().toString());

        reportModel.setReport_type(spReportType.getSelectedItemPosition() + "");

        PrefManager.setSetting(new Gson().toJson(reportModel));

        finish();
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

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void showaddalert(final ArrayList<String> listarr, final Spinner sp, final String tblname) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Enter Name");

        final EditText input = new EditText(mContext);
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
                    set_spnrRoofEdgeMetala(listarr, sp);
                }
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                spnrRoofEdgeMetal.setSelection(0);
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.chkDateTime:
                if (chkDateTime.isChecked()) {
                    llDateTime.setVisibility(View.GONE);
                } else {
                    llDateTime.setVisibility(View.VISIBLE);
                }
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
            case R.id.chkSubrogation:
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
                break;
            case R.id.chkContractor:
                if (chkContractor.isChecked())
                    llContractor.setVisibility(View.VISIBLE);
                else
                    llContractor.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}
