package com.cwclaims.claimapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;

import com.cwclaims.claimapp.R;

public class FormElevationsFragment extends Fragment implements OnItemSelectedListener {
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	// Damage
		Spinner spnrDmgFrontElev, spnrDmgBackElev,
				spnrDmgLeftElev, spnrDmgRightElev, spnrDmgNotes;
		EditText etDmgFrontElevCustom, etDmgBackElevCustom,
		etDmgLeftElevCustom, etDmgRightElevCustom, etDmgNotesCustom;
	
	public static FormElevationsFragment newInstance(String param1, String param2) {
		FormElevationsFragment fragment = new FormElevationsFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public FormElevationsFragment() {
		// Required empty public constructor
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
		View rootView = inflater.inflate(R.layout.fragment_form_elevations, container,
				false);
		initControls(rootView);
		return rootView;
	}

	void initControls(View view) {
		// Damage
				spnrDmgFrontElev = (Spinner) view.findViewById(R.id.spnr_dmg_front_eleva);
				spnrDmgBackElev = (Spinner) view.findViewById(R.id.spnr_dmg_back_eleva);
				spnrDmgLeftElev = (Spinner) view.findViewById(R.id.spnr_dmg_left_eleva);
				spnrDmgRightElev = (Spinner) view.findViewById(R.id.spnr_dmg_right_eleva);
				spnrDmgNotes = (Spinner) view.findViewById(R.id.spnr_dmg_notes);
//				etDmgNotes = (EditText) view.findViewById(R.id.et_dmg_notes);
				etDmgFrontElevCustom= (EditText)view.findViewById(R.id.et_dmg_front_custom);
				etDmgBackElevCustom= (EditText)view.findViewById(R.id.et_dmg_back_custom);
				etDmgLeftElevCustom= (EditText)view.findViewById(R.id.et_dmg_left_custom);
				etDmgRightElevCustom= (EditText)view.findViewById(R.id.et_dmg_right_custom);
				etDmgNotesCustom= (EditText)view.findViewById(R.id.et_dmg_notes_custom);
	


				// Spinner item selected listner
				spnrDmgNotes.setOnItemSelectedListener(this);
				spnrDmgBackElev.setOnItemSelectedListener(this);
				spnrDmgFrontElev.setOnItemSelectedListener(this);
				spnrDmgLeftElev.setOnItemSelectedListener(this);
				spnrDmgRightElev.setOnItemSelectedListener(this);
				
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
		case R.id.spnr_dmg_back_eleva:
			if (spinner.getSelectedItem().toString().trim().equalsIgnoreCase(lastString)) {
				etDmgBackElevCustom.setVisibility(View.VISIBLE);
			}else{
				etDmgBackElevCustom.setVisibility(View.GONE);
			}
			break;
		case R.id.spnr_dmg_front_eleva:
			if (spinner.getSelectedItem().toString().trim().equalsIgnoreCase(lastString)) {
				etDmgFrontElevCustom.setVisibility(View.VISIBLE);
			}else{
				etDmgFrontElevCustom.setVisibility(View.GONE);
			}
			break;
		case R.id.spnr_dmg_left_eleva:
			if (spinner.getSelectedItem().toString().trim().equalsIgnoreCase(lastString)) {
				etDmgLeftElevCustom.setVisibility(View.VISIBLE);
			}else{
				etDmgLeftElevCustom.setVisibility(View.GONE);
			}
			break;
		case R.id.spnr_dmg_notes:
			if (spinner.getSelectedItem().toString().trim().equalsIgnoreCase(lastString)) {
				etDmgNotesCustom.setVisibility(View.VISIBLE);
			}else{
				etDmgNotesCustom.setVisibility(View.GONE);
			}
			break;
		case R.id.spnr_dmg_right_eleva:
			if (spinner.getSelectedItem().toString().trim().equalsIgnoreCase(lastString)) {
				etDmgRightElevCustom.setVisibility(View.VISIBLE);
			}else{
				etDmgRightElevCustom.setVisibility(View.GONE);
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
