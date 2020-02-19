package com.cwclaims.claimapp.fragment;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.R.string;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class FormRoofFragment extends Fragment implements OnItemSelectedListener {
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	// Roof
		CheckBox chbxRoof;
		EditText etRoofPitch, etRoofLayers;
		Spinner spnrRoofEdgeMetal, spnrRoofType;
		EditText etRoofEdgeMetalCustom,etRoofTypeCustom;
		EditText etRoofAge;
		// Damage
		Spinner spnrDmgRoof;
		EditText etDmgRoofCustom;
		
	public static FormRoofFragment newInstance(String param1, String param2) {
		FormRoofFragment fragment = new FormRoofFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public FormRoofFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_form_roof, container,
				false);
		initControls(rootView);
		return rootView;
	}
	
	void initControls(View view) {
		
		// Roof
				chbxRoof = (CheckBox) view.findViewById(R.id.chbx_roof);
				etRoofPitch = (EditText) view.findViewById(R.id.et_roof_Pitch);
				etRoofLayers = (EditText) view.findViewById(R.id.et_roof_Layers);
				spnrRoofEdgeMetal = (Spinner) view.findViewById(R.id.spnr_roof_edge_metal);
				spnrRoofType = (Spinner) view.findViewById(R.id.spnr_roof_type);
				etRoofEdgeMetalCustom = (EditText)view.findViewById(R.id.et_roof_edge_metal_custom);
				etRoofTypeCustom = (EditText)view.findViewById(R.id.et_roof_type_custom);
				etRoofAge = (EditText) view.findViewById(R.id.et_roof_age);
		
				spnrDmgRoof = (Spinner) view.findViewById(R.id.spnr_dmg_roof);
				etDmgRoofCustom= (EditText)view.findViewById(R.id.et_dmg_roof_custom);
				
		spnrRoofEdgeMetal.setOnItemSelectedListener(this);
		spnrRoofType.setOnItemSelectedListener(this);
		spnrDmgRoof.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Spinner spinner = (Spinner) parent;
		String[] tempArray = getResources().getStringArray(R.array.dmg_notes);
		String lastString = tempArray[tempArray.length-1];
		/*if (spinner.getId() == R.id.spnr_dmg_notes && position == 1) {
			etDmgNotes.setVisibility(View.VISIBLE);
		} else {
			etDmgNotes.setVisibility(View.GONE);
		}*/
		switch (spinner.getId()) {

		case R.id.spnr_roof_edge_metal:
			if (spinner.getSelectedItem().toString().trim().equalsIgnoreCase(lastString)) {
				etRoofEdgeMetalCustom.setVisibility(View.VISIBLE);
			}else{
				etRoofEdgeMetalCustom.setVisibility(View.GONE);
			}
			break;
		case R.id.spnr_roof_type:
			if (spinner.getSelectedItem().toString().trim().equalsIgnoreCase(lastString)) {
				etRoofTypeCustom.setVisibility(View.VISIBLE);
			}else{
				etRoofTypeCustom.setVisibility(View.GONE);
			}
			break;

		case R.id.spnr_dmg_roof:
			if (spinner.getSelectedItem().toString().trim().equalsIgnoreCase(lastString)) {
				etDmgRoofCustom.setVisibility(View.VISIBLE);
			}else{
				etDmgRoofCustom.setVisibility(View.GONE);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}
