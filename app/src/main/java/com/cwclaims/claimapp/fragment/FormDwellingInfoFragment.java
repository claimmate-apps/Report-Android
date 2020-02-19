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

public class FormDwellingInfoFragment extends Fragment implements
		OnItemSelectedListener {
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	// Dweling
	EditText etDwlStory;
	Spinner spnrDwlFirst, spnrDwlSecond, spnrDwlThird, spnrDwlFourth,
			spnrDwlFifth;
	EditText etDwlFirstCustom, etDwlSecondCustom, etDwlThirdCustom,
			etDwlFourthCustom, etDwlFifthCustom;

	public static FormDwellingInfoFragment newInstance(String param1,
			String param2) {
		FormDwellingInfoFragment fragment = new FormDwellingInfoFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public FormDwellingInfoFragment() {
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
		View rootView = inflater.inflate(R.layout.fragment_form_dwelling_info,
				container, false);
		initControls(rootView);
		return rootView;
	}

	void initControls(View view) {
		// Dweling
		etDwlStory = (EditText) view.findViewById(R.id.et_dwl_story);
		spnrDwlFirst = (Spinner) view.findViewById(R.id.spnr_dwl_first);
		spnrDwlSecond = (Spinner) view.findViewById(R.id.spnr_dwl_second);
		spnrDwlThird = (Spinner) view.findViewById(R.id.spnr_dwl_third);
		spnrDwlFourth = (Spinner) view.findViewById(R.id.spnr_dwl_fourth);
		spnrDwlFifth = (Spinner) view.findViewById(R.id.spnr_dwl_fifth);
		etDwlFirstCustom = (EditText) view
				.findViewById(R.id.et_dwl_first_custom);
		etDwlSecondCustom = (EditText) view
				.findViewById(R.id.et_dwl_second_custom);
		etDwlThirdCustom = (EditText) view
				.findViewById(R.id.et_dwl_third_custom);
		etDwlFourthCustom = (EditText) view
				.findViewById(R.id.et_dwl_fourth_custom);
		etDwlFifthCustom = (EditText) view
				.findViewById(R.id.et_dwl_fifth_custom);

		spnrDwlFifth.setOnItemSelectedListener(this);
		spnrDwlFirst.setOnItemSelectedListener(this);
		spnrDwlFourth.setOnItemSelectedListener(this);
		spnrDwlSecond.setOnItemSelectedListener(this);
		spnrDwlThird.setOnItemSelectedListener(this);

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Spinner spinner = (Spinner) parent;
		String[] tempArray = getResources().getStringArray(R.array.dmg_notes);
		String lastString = tempArray[tempArray.length - 1];
		/*
		 * if (spinner.getId() == R.id.spnr_dmg_notes && position == 1) {
		 * etDmgNotes.setVisibility(View.VISIBLE); } else {
		 * etDmgNotes.setVisibility(View.GONE); }
		 */
		switch (spinner.getId()) {

		case R.id.spnr_dwl_fifth:
			if (spinner.getSelectedItem().toString().trim()
					.equalsIgnoreCase(lastString)) {
				etDwlFifthCustom.setVisibility(View.VISIBLE);
			} else {
				etDwlFifthCustom.setVisibility(View.GONE);
			}
			break;
		case R.id.spnr_dwl_first:
			if (spinner.getSelectedItem().toString().trim()
					.equalsIgnoreCase(lastString)) {
				etDwlFirstCustom.setVisibility(View.VISIBLE);
			} else {
				etDwlFirstCustom.setVisibility(View.GONE);
			}
			break;
		case R.id.spnr_dwl_fourth:
			if (spinner.getSelectedItem().toString().trim()
					.equalsIgnoreCase(lastString)) {
				etDwlFourthCustom.setVisibility(View.VISIBLE);
			} else {
				etDwlFourthCustom.setVisibility(View.GONE);
			}
			break;
		case R.id.spnr_dwl_second:
			if (spinner.getSelectedItem().toString().trim()
					.equalsIgnoreCase(lastString)) {
				etDwlSecondCustom.setVisibility(View.VISIBLE);
			} else {
				etDwlSecondCustom.setVisibility(View.GONE);
			}
			break;
		case R.id.spnr_dwl_third:
			if (spinner.getSelectedItem().toString().trim()
					.equalsIgnoreCase(lastString)) {
				etDwlThirdCustom.setVisibility(View.VISIBLE);
			} else {
				etDwlThirdCustom.setVisibility(View.GONE);
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
