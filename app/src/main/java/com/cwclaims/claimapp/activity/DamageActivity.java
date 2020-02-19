package com.cwclaims.claimapp.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.adpts.ElevationFirstAdapter;
import com.cwclaims.claimapp.adpts.InteriorAdapter;
import com.cwclaims.claimapp.adpts.RoofFirstAdapter;
import com.cwclaims.claimapp.models.ElevationRes;
import com.cwclaims.claimapp.models.RoofRes;

import java.util.ArrayList;
import java.util.List;

public class DamageActivity extends Activity implements View.OnClickListener {

    private final String TAG = "DamageActivity";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damage);

        init();

    }

    Spinner spinnerRoom, spinnerMain, spinnerSub;
    RecyclerView recyclerInterior, recyclerRoof, recyclerElevation;
    LinearLayout llInrerior, llLWH;
    EditText l, w, h;
    Button btnAdd, btnAddInteriorSection, btnSave;

    boolean isElevation;

    String claimId;

    ArrayList<String> mainArray, interiorArray, roofArray, roomArray;
    ArrayList<String> roofMainArray, roofArray1, roofArray2, roofArray3, roofArray4,
            roofArray5, roofArray6, roofArray7, roofArray8,
            roofArray9, roofArray10, roofArray11;

    ArrayList<String> elevationMainArray, elevationArray1, elevationArray2, elevationArray3, elevationArray4,
            elevationArray5, elevationArray6, elevationArray7, elevationArray8,
            elevationArray9, elevationArray10, elevationArray11, elevationArray12,
            elevationArray13, elevationArray14, elevationArray15, elevationArray16,
            elevationArray17, elevationArray18;

    InteriorAdapter interiorAdapter;
    RoofFirstAdapter roofAdapter;
    ElevationFirstAdapter elevationAdapter;

    private void init() {
        mContext = DamageActivity.this;

        spinnerRoom = findViewById(R.id.spinnerRoom);
        spinnerMain = findViewById(R.id.spinnerMain);
        spinnerSub = findViewById(R.id.spinnerSub);

        recyclerInterior = findViewById(R.id.recyclerInterior);
        recyclerRoof = findViewById(R.id.recyclerRoof);
        recyclerElevation = findViewById(R.id.recyclerElevation);

        llInrerior = findViewById(R.id.llInrerior);
        llLWH = findViewById(R.id.llLWH);

        l = findViewById(R.id.l);
        w = findViewById(R.id.w);
        h = findViewById(R.id.h);

        btnAdd = findViewById(R.id.btnAdd);
        btnAddInteriorSection = findViewById(R.id.btnAddInteriorSection);
        btnSave = findViewById(R.id.btnSave);

        btnAdd.setOnClickListener(this);
        btnAddInteriorSection.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerInterior.setLayoutManager(mLayoutManager);

        interiorAdapter = new InteriorAdapter(mContext);
        recyclerInterior.setAdapter(interiorAdapter);

        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(mContext);
        recyclerRoof.setLayoutManager(mLayoutManager2);

        roofAdapter = new RoofFirstAdapter(mContext);
        recyclerRoof.setAdapter(roofAdapter);

        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(mContext);
        recyclerElevation.setLayoutManager(mLayoutManager3);

        elevationAdapter = new ElevationFirstAdapter(mContext);
        recyclerElevation.setAdapter(elevationAdapter);

        elevationAdapter.setEventListener(new ElevationFirstAdapter.EventListener() {
            @Override
            public void onItemViewClicked(int position) {

            }

            @Override
            public void onItemTitleClicked(int position) {
                ElevationRes data = elevationAdapter.getItem(position);
                showDialog(data.sub);
            }
        });

        roofAdapter.setEventListener(new RoofFirstAdapter.EventListener() {
            @Override
            public void onItemViewClicked(int position) {

            }

            @Override
            public void onItemTitleClicked(int position) {
                RoofRes data = roofAdapter.getItem(position);
                showDialog(data.sub);
            }
        });

        roomArray = new ArrayList<>();
        roomArray.add("Main building");
        roomArray.add("garage");
        roomArray.add("shed");
        roomArray.add("fence");
        roomArray.add("custom name");

        mainArray = new ArrayList<>();
        mainArray.add("Interior");
        mainArray.add("Roof");
        mainArray.add("Elevation");

        interiorArray = new ArrayList<>();
        interiorArray.add("Living room");
        interiorArray.add("Hallway");
        interiorArray.add("Kitchen");
        interiorArray.add("Stairs");
        interiorArray.add("Bedroom 1");
        interiorArray.add("Bedroom 2");
        interiorArray.add("Bedroom 3");
        interiorArray.add("Bathroom");
        interiorArray.add("Master Bedroom");
        interiorArray.add("Master Bathroom");
        interiorArray.add("Closet");
        interiorArray.add("Office");
        interiorArray.add("Attic");
        interiorArray.add("Garage");
        interiorArray.add("Custom Text");

        elevationMainArray = new ArrayList<>();
        elevationMainArray.add("Siding");
        elevationMainArray.add("Insulation");
        elevationMainArray.add("Soffit");
        elevationMainArray.add("Fascia");
        elevationMainArray.add("Gutters");
        elevationMainArray.add("DownSpouts");
        elevationMainArray.add("Gutterguards");
        elevationMainArray.add("Shutters");
        elevationMainArray.add("Vents");
        elevationMainArray.add("Windows");
        elevationMainArray.add("Doors");
        elevationMainArray.add("AC Units");
        elevationMainArray.add("Light Fixtures");
        elevationMainArray.add("Decks");
        elevationMainArray.add("Fence");
        elevationMainArray.add("Shed");
        elevationMainArray.add("Mailbox");
        elevationMainArray.add("Paint");
        elevationMainArray.add("Custom Data");

        elevationArray1 = new ArrayList<>();
        elevationArray1.add("Vinyl");
        elevationArray1.add("Vinyl High");
        elevationArray1.add("Vinyl Premium");
        elevationArray1.add("Aluminum .19");
        elevationArray1.add("Aluminum .24");
        elevationArray1.add("Steel");
        elevationArray1.add("Wood T1-11");
        elevationArray1.add("Hardboard 6");
        elevationArray1.add("Hardboard 8");
        elevationArray1.add("Hardboard 12");
        elevationArray1.add("Fiber Cement 6");
        elevationArray1.add("Fiber Cement 8");
        elevationArray1.add("Fiber Cement 12");

        elevationArray2 = new ArrayList<>();
        elevationArray2.add("Foam Board");
        elevationArray2.add("House Wrap");
        elevationArray2.add("Foam + House Wrap");

        elevationArray3 = new ArrayList<>();
        elevationArray3.add("Metal");
        elevationArray3.add("Vinyl");
        elevationArray3.add("Wood");

        elevationArray4 = new ArrayList<>();
        elevationArray4.add("Metal 4");
        elevationArray4.add("Metal 6");
        elevationArray4.add("Metal 8");
        elevationArray4.add("Wood 4");
        elevationArray4.add("Wood 6");
        elevationArray4.add("Wood 8");
        elevationArray4.add("Vinyl 4-6");
        elevationArray4.add("Vinyl 7-10");

        elevationArray5 = new ArrayList<>();
        elevationArray5.add("5");
        elevationArray5.add("6");
        elevationArray5.add("Plastic");

        elevationArray6 = new ArrayList<>();
        elevationArray6.add("2x3");
        elevationArray6.add("3x4");
        elevationArray6.add("Plastic");

        elevationArray7 = new ArrayList<>();
        elevationArray7.add("Average");
        elevationArray7.add("High");
        elevationArray7.add("Premium");

        elevationArray8 = new ArrayList<>();
        elevationArray8.add("12x24");
        elevationArray8.add("15x43");
        elevationArray8.add("15x60");

        elevationArray9 = new ArrayList<>();
        elevationArray9.add("Octagon");
        elevationArray9.add("Rectangle");

        elevationArray10 = new ArrayList<>();
        elevationArray10.add("Reglaze 1-9 sf");
        elevationArray10.add("Reglaze 10-16 sf");
        elevationArray10.add("Reglaze 17-24 sf");

        elevationArray11 = new ArrayList<>();
        elevationArray11.add("Xterior Door");
        elevationArray11.add("Interior door");
        elevationArray11.add("Storm door");
        elevationArray11.add("Lockset");
        elevationArray11.add("Lockset w/ Deadbolt");

        elevationArray12 = new ArrayList<>();
        elevationArray12.add("Comb");
        elevationArray12.add("Cage");
        elevationArray12.add("Inspection");

        elevationArray13 = new ArrayList<>();
        elevationArray13.add("Average");
        elevationArray13.add("High");
        elevationArray13.add("Premium");

        elevationArray14 = new ArrayList<>();
        elevationArray14.add("Pwash");
        elevationArray14.add("Stain");
        elevationArray14.add("Prime");
        elevationArray14.add("Paint");
        elevationArray14.add("Pwash Dk Rail");
        elevationArray14.add("Stain Dk Rail");
        elevationArray14.add("Prime Dk Rail");
        elevationArray14.add("Paint Dk Rail");

        elevationArray15 = new ArrayList<>();
        elevationArray15.add("Privacy");
        elevationArray15.add("Board On Board");
        elevationArray15.add("Chain Link");

        elevationArray16 = new ArrayList<>();
        elevationArray16.add("Metal Gable");
        elevationArray16.add("Metal Barn");
        elevationArray16.add("Vinyl");

        elevationArray17 = new ArrayList<>();
        elevationArray17.add("Average");
        elevationArray17.add("High");
        elevationArray17.add("Premium");

        elevationArray18 = new ArrayList<>();
        elevationArray18.add("Siding");
        elevationArray18.add("Soffit");
        elevationArray18.add("Fascia");
        elevationArray18.add("Gutters");
        elevationArray18.add("DownSpouts");
        elevationArray18.add("Shutters");
        elevationArray18.add("Vents");
        elevationArray18.add("Doors");
        elevationArray18.add("Decks");
        elevationArray18.add("Fence");


        roofArray = new ArrayList<>();
        roofArray.add("Water Damage");
        roofArray.add("Wind Damage");
        roofArray.add("Hail Damage");
        roofArray.add("Mechanical Damage");
        roofArray.add("Smoke Damage");
        roofArray.add("Fire Damage");
        roofArray.add("Collateral Damage");
        roofArray.add("No Damage");
        roofArray.add("No Damage But Included");

        roofMainArray = new ArrayList<>();
        roofMainArray.add("TEST SQ");
        roofMainArray.add("Shingles");
        roofMainArray.add("Cap");
        roofMainArray.add("layers");
        roofMainArray.add("Drip ");
        roofMainArray.add("Pitch");
        roofMainArray.add("IWS");
        roofMainArray.add("Valley");
        roofMainArray.add("Pipe Jacks");
        roofMainArray.add("Vents");
        roofMainArray.add("Flash");

        roofArray1 = new ArrayList<>();
        roofArray1.add("Blank");
        roofArray1.add("25 yr 3 tab");
        roofArray1.add("30 yr lam");
        roofArray1.add("40 yr lam");
        roofArray1.add("50 yr lam");
        roofArray1.add("T-Lock");
        roofArray1.add("Shake");
        roofArray1.add("Metal");

        roofArray2 = new ArrayList<>();
        roofArray2.add("Blank");
        roofArray2.add("25 yr 3 tab");
        roofArray2.add("30 yr lam");
        roofArray2.add("40 yr lam");
        roofArray2.add("50 yr lam");
        roofArray2.add("T-Lock");
        roofArray2.add("Shake");
        roofArray2.add("Metal");

        roofArray3 = new ArrayList<>();
        roofArray3.add("Blank");
        roofArray3.add("Composite");
        roofArray3.add("Comp. w/ Ridge Vent");
        roofArray3.add("High profile");
        roofArray3.add("High Profile W/ Vent");
        roofArray3.add("Alum Ridge vent");

        roofArray4 = new ArrayList<>();
        roofArray4.add("Blank");
        roofArray4.add("1");
        roofArray4.add("2");
        roofArray4.add("3");

        roofArray5 = new ArrayList<>();
        roofArray5.add("Blank");
        roofArray5.add("None");
        roofArray5.add("ALL");
        roofArray5.add("Eave Drip");
        roofArray5.add("Eave Apron");
        roofArray5.add("Eave Apron & Rake Drip");

        roofArray6 = new ArrayList<>();
        roofArray6.add("Blank");
        roofArray6.add("1");
        roofArray6.add("2");
        roofArray6.add("3");
        roofArray6.add("4");
        roofArray6.add("5");
        roofArray6.add("6");
        roofArray6.add("7");
        roofArray6.add("8");
        roofArray6.add("9");
        roofArray6.add("10");
        roofArray6.add("11");
        roofArray6.add("12");

        roofArray7 = new ArrayList<>();
        roofArray7.add("Blank");
        roofArray7.add("Eaves");
        roofArray7.add("Eaves-Code");

        roofArray8 = new ArrayList<>();
        roofArray8.add("Blank");
        roofArray8.add("IWS");
        roofArray8.add("Open Rolled");
        roofArray8.add("Closed");
        roofArray8.add("Closed with Metal");

        roofArray9 = new ArrayList<>();
        roofArray9.add("Blank");
        roofArray9.add("1-3");
        roofArray9.add("4 Lead");
        roofArray9.add("Split");
        roofArray9.add("Flu Cap");
        roofArray9.add("HVAC Cap 5");
        roofArray9.add("HVAC Cap 6");
        roofArray9.add("HVAC Cap 8");

        roofArray10 = new ArrayList<>();
        roofArray10.add("Blank");
        roofArray10.add("Turtle");
        roofArray10.add("Turbine");
        roofArray10.add("Power Vent");
        roofArray10.add("P-Vent Cap Metal");
        roofArray10.add("P-Vent Cap Plastic");

        roofArray11 = new ArrayList<>();
        roofArray11.add("Blank");
        roofArray11.add("Step");
        roofArray11.add("Skylight cladding");
        roofArray11.add("Skylight Flashing");
        roofArray11.add("Chimney Small");
        roofArray11.add("Chimney Med");
        roofArray11.add("Chimney Lrg");

        List<ElevationRes> elevationRes = new ArrayList<>();
        ElevationRes elevationRes1 = new ElevationRes();
        elevationRes1.sub = elevationArray1;
        elevationRes.add(elevationRes1);
        elevationAdapter.addAll(elevationRes);

        List<RoofRes> array = new ArrayList<>();
        RoofRes roofRes = new RoofRes();
        roofRes.title = "TEST SQ";
        roofRes.isCheckbox = false;
        roofRes.sub = roofArray1;
        array.add(roofRes);

        roofRes = new RoofRes();
        roofRes.title = "SHINGLES";
        roofRes.isCheckbox = true;
        roofRes.sub = roofArray2;
        array.add(roofRes);

        roofRes = new RoofRes();
        roofRes.title = "CAP";
        roofRes.isCheckbox = false;
        roofRes.sub = roofArray3;
        array.add(roofRes);

        roofRes = new RoofRes();
        roofRes.title = "LAYERS";
        roofRes.isCheckbox = true;
        roofRes.sub = roofArray4;
        array.add(roofRes);

        roofRes = new RoofRes();
        roofRes.title = "DRIP";
        roofRes.isCheckbox = false;
        roofRes.sub = roofArray5;
        array.add(roofRes);

        roofRes = new RoofRes();
        roofRes.title = "PITCH";
        roofRes.isCheckbox = true;
        roofRes.sub = roofArray6;
        array.add(roofRes);

        roofRes = new RoofRes();
        roofRes.title = "IWS";
        roofRes.isCheckbox = true;
        roofRes.sub = roofArray7;
        array.add(roofRes);

        roofRes = new RoofRes();
        roofRes.title = "VALLEY";
        roofRes.isCheckbox = false;
        roofRes.sub = roofArray8;
        array.add(roofRes);

        roofRes = new RoofRes();
        roofRes.title = "PIPE JACKS";
        roofRes.isCheckbox = false;
        roofRes.sub = roofArray9;
        array.add(roofRes);

        roofRes = new RoofRes();
        roofRes.title = "VENTS";
        roofRes.isCheckbox = false;
        roofRes.sub = roofArray10;
        array.add(roofRes);

        roofRes = new RoofRes();
        roofRes.title = "FLASH";
        roofRes.isCheckbox = false;
        roofRes.sub = roofArray11;
        array.add(roofRes);

        roofAdapter.addAll(array);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, mainArray);
        spinnerMain.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, interiorArray);
        spinnerSub.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, roomArray);
        spinnerRoom.setAdapter(adapter2);


        spinnerMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    isElevation = false;
                    recyclerInterior.setVisibility(View.VISIBLE);
                    recyclerRoof.setVisibility(View.GONE);
                    recyclerElevation.setVisibility(View.GONE);
                    llLWH.setVisibility(View.VISIBLE);
                    btnAdd.setVisibility(View.GONE);
//                    spinnerSub.setVisibility(View.GONE);

                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, interiorArray);
                    spinnerSub.setAdapter(adapter1);
                }
                if (position == 1) {
                    isElevation = false;
                    recyclerInterior.setVisibility(View.GONE);
                    recyclerRoof.setVisibility(View.VISIBLE);
                    recyclerElevation.setVisibility(View.GONE);
                    llLWH.setVisibility(View.GONE);
                    btnAdd.setVisibility(View.VISIBLE);
//                    spinnerSub.setVisibility(View.VISIBLE);

                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, roofArray);
                    spinnerSub.setAdapter(adapter1);
                }
                if (position == 2) {
                    isElevation = true;
                    recyclerElevation.setVisibility(View.VISIBLE);
                    recyclerRoof.setVisibility(View.GONE);
                    recyclerInterior.setVisibility(View.GONE);
                    llLWH.setVisibility(View.GONE);
                    btnAdd.setVisibility(View.VISIBLE);
//                    spinnerSub.setVisibility(View.VISIBLE);

                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, roofArray);
                    spinnerSub.setAdapter(adapter1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == btnAdd.getId()) {
            showDialog(isElevation);
        } else if (view.getId() == btnAddInteriorSection.getId()) {

        } else if (view.getId() == btnSave.getId()) {

        }
    }

    Dialog dialog;

    public void showDialog(boolean isElevations) {
        dialog = new Dialog(mContext);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_add_option);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final TextView tvDone = dialog.findViewById(R.id.tvDone);
        final Spinner spinnerMain = dialog.findViewById(R.id.spinnerMain);
        final Spinner spinnerSub = dialog.findViewById(R.id.spinnerSub);
        final TextView tvCancel = dialog.findViewById(R.id.tvCancel);

        if (isElevations) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationMainArray);
            spinnerMain.setAdapter(adapter);

            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray1);
            spinnerSub.setAdapter(adapter1);

            spinnerMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray1);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 1) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray2);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 2) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray3);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 3) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray4);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 4) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray5);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 5) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray6);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 6) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray7);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 7) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray8);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 8) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray9);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 9) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray10);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 10) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray11);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 11) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray12);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 12) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray13);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 13) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray14);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 14) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray15);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 15) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray16);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 16) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray17);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 17) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, elevationArray18);
                        spinnerSub.setAdapter(adapter);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } else {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, roofMainArray);
            spinnerMain.setAdapter(adapter);

            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, roofArray1);
            spinnerSub.setAdapter(adapter1);

            spinnerMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, roofArray1);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 1) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, roofArray2);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 2) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, roofArray3);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 3) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, roofArray4);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 4) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, roofArray5);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 5) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, roofArray6);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 6) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, roofArray7);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 7) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, roofArray8);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 8) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, roofArray9);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 9) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, roofArray10);
                        spinnerSub.setAdapter(adapter);
                    }
                    if (position == 10) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, roofArray11);
                        spinnerSub.setAdapter(adapter);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    Dialog dialog2;

    public void showDialog(List<String> array) {
        dialog2 = new Dialog(mContext);
        dialog2.setCanceledOnTouchOutside(true);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setCancelable(true);
        dialog2.setContentView(R.layout.dialog_edit_option);
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final TextView tvDone = dialog2.findViewById(R.id.tvDone);
        final TextView tvCancel = dialog2.findViewById(R.id.tvCancel);
        final Spinner spinner = dialog2.findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.support_simple_spinner_dropdown_item, array);
        spinner.setAdapter(adapter);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

        dialog2.show();
    }
}
