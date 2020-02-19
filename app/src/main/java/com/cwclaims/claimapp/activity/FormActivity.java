package com.cwclaims.claimapp.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.fragment.FormClaimInfoFragment;
import com.cwclaims.claimapp.fragment.FormCovBFragment;
import com.cwclaims.claimapp.fragment.FormCustomFragment;
import com.cwclaims.claimapp.fragment.FormDwellingInfoFragment;
import com.cwclaims.claimapp.fragment.FormElevationsFragment;
import com.cwclaims.claimapp.fragment.FormInteriorFragment;
import com.cwclaims.claimapp.fragment.FormRoofFragment;

public class FormActivity extends Activity implements OnClickListener {

	Button btnMenuClaimInfo, btnMenuDwellingInfo, btnMenuRoof,
			btnMenuElevations, btnMenuInterior, btnMenuCustom, btnMenuCovB;

	HorizontalScrollView hScrollView;
//	LinearLayout llMenuHolder;
	int currentSelectedScreen = -1;
	
	FormClaimInfoFragment fragmentClaimInfo;
	FormCovBFragment fragmentCovB;
	FormCustomFragment fragmentCustom;
	FormDwellingInfoFragment fragmentDwellingInfo;
	FormElevationsFragment fragmentElevations;
	FormInteriorFragment fragmentInterior;
	FormRoofFragment fragmentRoof;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form);

		initControls();
		initFragments();
		onClick(btnMenuClaimInfo);
	}

	void initControls(){
		btnMenuClaimInfo = (Button) findViewById(R.id.btn_Main_Menu_ClaimInfo);
		btnMenuCovB = (Button) findViewById(R.id.btn_Main_Menu_CovB);
		btnMenuCustom = (Button) findViewById(R.id.btn_Main_Menu_Custom);
		btnMenuRoof = (Button) findViewById(R.id.btn_Main_Menu_Roof);
		btnMenuInterior = (Button) findViewById(R.id.btn_Main_Menu_Interior);
		btnMenuElevations = (Button) findViewById(R.id.btn_Main_Menu_Elevations);
		btnMenuDwellingInfo = (Button) findViewById(R.id.btn_Main_Menu_DwellingInfo);

		btnMenuClaimInfo.setOnClickListener(this);
		btnMenuCovB.setOnClickListener(this);
		btnMenuCustom.setOnClickListener(this);
		btnMenuDwellingInfo.setOnClickListener(this);
		btnMenuInterior.setOnClickListener(this);
		btnMenuElevations.setOnClickListener(this);
		btnMenuRoof.setOnClickListener(this);
		
		hScrollView = (HorizontalScrollView)findViewById(R.id.hScr_Main);
//		llMenuHolder = (LinearLayout)findViewById(R.id.ll_Main_Menu_Holder);
	}
	
	void initFragments(){
		fragmentClaimInfo = new FormClaimInfoFragment();
		fragmentCovB = new FormCovBFragment();
		fragmentCustom = new FormCustomFragment();
		fragmentDwellingInfo = new FormDwellingInfoFragment();
		fragmentElevations = new FormElevationsFragment();
		fragmentInterior = new FormInteriorFragment();
		fragmentRoof = new FormRoofFragment();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.menu_generate_report) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_Main_Menu_ClaimInfo:
			if (currentSelectedScreen!=R.id.btn_Main_Menu_ClaimInfo) {
				handleMenuTabClick(R.id.btn_Main_Menu_ClaimInfo , fragmentClaimInfo);
			}
			break;
		case R.id.btn_Main_Menu_CovB:
			if (currentSelectedScreen!=R.id.btn_Main_Menu_CovB) {
				handleMenuTabClick(R.id.btn_Main_Menu_CovB , fragmentCovB);
			}
			break;
		case R.id.btn_Main_Menu_Custom:
			if (currentSelectedScreen!=R.id.btn_Main_Menu_Custom) {
				handleMenuTabClick(R.id.btn_Main_Menu_Custom , fragmentCustom);
			}
			break;
		case R.id.btn_Main_Menu_DwellingInfo:
			if (currentSelectedScreen!=R.id.btn_Main_Menu_DwellingInfo) {
				handleMenuTabClick(R.id.btn_Main_Menu_DwellingInfo , fragmentDwellingInfo);
			}
			break;
		case R.id.btn_Main_Menu_Elevations:
			if (currentSelectedScreen!=R.id.btn_Main_Menu_Elevations) {
				handleMenuTabClick(R.id.btn_Main_Menu_Elevations , fragmentElevations);
			}
			break;
		case R.id.btn_Main_Menu_Interior:
			if (currentSelectedScreen!=R.id.btn_Main_Menu_Interior) {
				handleMenuTabClick(R.id.btn_Main_Menu_Interior , fragmentInterior);
			}
			break;
		case R.id.btn_Main_Menu_Roof:
			if (currentSelectedScreen!=R.id.btn_Main_Menu_Roof) {
				handleMenuTabClick(R.id.btn_Main_Menu_Roof , fragmentRoof);
			}
			break;

		default:
			break;
		}
	}
	
	void handleMenuTabClick(int btnId, Fragment fragment){
		
		resetBackgroundToButton();
		
		findViewById(btnId).setBackgroundResource(R.drawable.form_menu_tab_selected);
		
		getFragmentManager().beginTransaction()
		.replace(R.id.fl_Main_Container, fragment)
		.commit();
		currentSelectedScreen = btnId;	
		
		scrollHorizontalToClickedButton(btnId);
	}
	
	void resetBackgroundToButton(){
		btnMenuClaimInfo.setBackgroundResource(R.drawable.form_menu_tab);
		btnMenuCovB.setBackgroundResource(R.drawable.form_menu_tab);
		btnMenuCustom.setBackgroundResource(R.drawable.form_menu_tab);
		btnMenuDwellingInfo.setBackgroundResource(R.drawable.form_menu_tab);
		btnMenuElevations.setBackgroundResource(R.drawable.form_menu_tab);
		btnMenuRoof.setBackgroundResource(R.drawable.form_menu_tab);
		btnMenuInterior.setBackgroundResource(R.drawable.form_menu_tab);
	}
	
	void scrollHorizontalToClickedButton(int btnId){
		Button button3 = (Button) findViewById(btnId);
        int scrollTo = 0;
        final int count = ((LinearLayout) hScrollView.getChildAt(0))
                .getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = ((LinearLayout) hScrollView.getChildAt(0))
                    .getChildAt(i);                 
            if (child != button3) {
                scrollTo += child.getWidth();
            } else {
            	scrollTo+=child.getWidth()/2;
                break;
            }
        }
        hScrollView.scrollTo(scrollTo-(hScrollView.getWidth()/2), 0);
	}
}
