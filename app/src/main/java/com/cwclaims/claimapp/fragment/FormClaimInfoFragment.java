package com.cwclaims.claimapp.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cwclaims.claimapp.R;

public class FormClaimInfoFragment extends Fragment implements OnClickListener, OnCheckedChangeListener {
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	// First
	EditText etFirstClimantName, /* etFirstTolCode, */etFirstTolDesc,
	/* etFirstDateLoss, */
	etFirstInsuredName, etFirstMortgagee/* , etFirstMortgagee1 */
	/* ,etFirstDateInspected */;
	Button btnFirstDateLoss, btnFirstDateInspected, btnFirstTimeInspected;
	CheckBox chbxFirstIsPresent, chbxInsuredNameDiff;
	TextView tvInsuredName;

	// Custom
	boolean isDatePickerForInspection = false;
	String btnInitInspectTime, btnInitInspectDate, btnInitLossDate;

	public static FormClaimInfoFragment newInstance(String param1, String param2) {
		FormClaimInfoFragment fragment = new FormClaimInfoFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public FormClaimInfoFragment() {

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
		View rootView = inflater.inflate(R.layout.fragment_form_claim_info,
				container, false);

		initControls(rootView);
		setTextWatcher();

		return rootView;
	}

	void initControls(View view) {
		// First
		etFirstClimantName = (EditText) view
				.findViewById(R.id.et_first_ClaimantName);
		chbxInsuredNameDiff = (CheckBox) view
				.findViewById(R.id.chbx_first_InsuredNameDiff);
		tvInsuredName = (TextView) view.findViewById(R.id.tv_first_InsuredName);
		etFirstInsuredName = (EditText) view
				.findViewById(R.id.et_first_InsuredName);
		// etFirstTolCode = (EditText) view.findViewById(R.id.et_first_TolCode);
		etFirstTolDesc = (EditText) view.findViewById(R.id.et_first_TolDesc);
		// etFirstDateLoss =
		// (EditText)view.findViewById(R.id.et_first_DateLoss);
		btnFirstDateLoss = (Button) view.findViewById(R.id.btn_first_date_loss);
		etFirstMortgagee = (EditText) view
				.findViewById(R.id.et_first_Mortgagee);
		// etFirstMortgagee1 = (EditText)
		// view.findViewById(R.id.et_first_Mortgagee1);
		// etFirstDateInspected =
		// (EditText)view.findViewById(R.id.et_first_DateInspected);
		btnFirstDateInspected = (Button) view
				.findViewById(R.id.btn_first_date_inspected);
		btnFirstTimeInspected = (Button) view
				.findViewById(R.id.btn_first_time_inspected);
		chbxFirstIsPresent = (CheckBox) view
				.findViewById(R.id.chbx_first_IsPresent);

		// Button click listner
		btnFirstDateInspected.setOnClickListener(this);
		btnFirstDateLoss.setOnClickListener(this);
		btnFirstTimeInspected.setOnClickListener(this);

		btnInitInspectTime = btnFirstTimeInspected.getText().toString();
		btnInitInspectDate = btnFirstDateInspected.getText().toString();
		btnInitLossDate = btnFirstDateLoss.getText().toString();

		Calendar c = Calendar.getInstance();

		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
		btnFirstTimeInspected.setText(dateFormat.format(c.getTime()));
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		btnFirstDateInspected.setText(dateFormat.format(c.getTime()));
		
		chbxInsuredNameDiff.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_first_date_loss:
			isDatePickerForInspection = false;
			DatePickerFragment newFragment = new DatePickerFragment();
			// newFragment.show(getFragmentManager(), "datePicker");
			newFragment.show(getFragmentManager(), "s");

			break;
		case R.id.btn_first_date_inspected:
			isDatePickerForInspection = true;
			DialogFragment datePicker = new DatePickerFragment();
			datePicker.show(getFragmentManager(), "datePicker");
			break;
		case R.id.btn_first_time_inspected:
			DialogFragment datePicker2 = new TimePickerFragment();
			datePicker2.show(getFragmentManager(), "timePicker");
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		
		case R.id.chbx_first_InsuredNameDiff:
			if (isChecked) {
				etFirstInsuredName.setVisibility(View.VISIBLE);
				tvInsuredName.setVisibility(View.VISIBLE);
			} else {
				etFirstInsuredName.setVisibility(View.GONE);
				tvInsuredName.setVisibility(View.GONE);
			}
		default:
			break;
		}
	}
	
	void setTextWatcher() {
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
				ArrayList<String> lst = new ArrayList<String>(Arrays
						.asList(interiorArray));
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(), android.R.layout.simple_spinner_item,
						lst);

				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// spnrDmgInterior.setAdapter(adapter);
				// TODO write event bus for above logic
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
				String[] interiorArray = getResources().getStringArray(
						R.array.misc_title);
				for (int i = 0; i < interiorArray.length; i++) {
					String tempValue = interiorArray[i];
					// tempValue = tempValue.replace("[XM8_TOL_CODE]",
					// etFirstTolCode.getEditableText().toString());
					tempValue = tempValue.replace("[XM8_TOL_DESC]",
							etFirstTolDesc.getEditableText().toString());
					interiorArray[i] = tempValue;
				}
				ArrayList<String> lst = new ArrayList<String>(Arrays
						.asList(interiorArray));
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(), android.R.layout.simple_spinner_item,
						lst);

				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// spnrMiscTitle.setAdapter(adapter);
				// TODO write event bus for above logic
			}
		});

		etFirstClimantName.addTextChangedListener(new TextWatcher() {

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
				if (chbxInsuredNameDiff.isChecked()) {

				} else {
					etFirstInsuredName.setText(s.toString());
				}
			}
		});
	}

	public class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
			/* DateFormat.is24HourFormat(getActivity()) */false);
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, hourOfDay);
			c.set(Calendar.MINUTE, minute);

			SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
			btnFirstTimeInspected.setText(dateFormat.format(c.getTime()));
		}
	}

	public class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			if (isDatePickerForInspection) {
				btnFirstDateInspected.setText(month + "/" + day + "/" + year);
			} else {
				btnFirstDateLoss.setText(month + "/" + day + "/" + year);
			}
		}
	}

}
