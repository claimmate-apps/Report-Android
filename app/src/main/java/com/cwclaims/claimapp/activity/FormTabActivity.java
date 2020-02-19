package com.cwclaims.claimapp.activity;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.fragment.FormClaimInfoFragment;
import com.cwclaims.claimapp.fragment.FormCovBFragment;
import com.cwclaims.claimapp.fragment.FormCustomFragment;
import com.cwclaims.claimapp.fragment.FormDwellingInfoFragment;
import com.cwclaims.claimapp.fragment.FormElevationsFragment;
import com.cwclaims.claimapp.fragment.FormInteriorFragment;
import com.cwclaims.claimapp.fragment.FormRoofFragment;

import android.app.Activity;
import android.app.ActionBar;
import android.app.FragmentTransaction;
//import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import android.view.Menu;
import android.view.MenuItem;

public class FormTabActivity extends Activity implements ActionBar.TabListener {


//	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

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
		setContentView(R.layout.activity_form_tab);

		initFragments();
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
//		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
//		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
//		mViewPager
//				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//					@Override
//					public void onPageSelected(int position) {
//						actionBar.setSelectedNavigationItem(position);
//					}
//				});
		mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		// For each of the sections in the app, add a tab to the action bar.
		/*for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}*/
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
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}


	/*public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:	return fragmentClaimInfo;
			case 1:	return fragmentDwellingInfo;
			case 2:	return fragmentRoof;
			case 3: return fragmentElevations;
			case 4: return fragmentInterior;
			case 5: return fragmentCustom;
			case 6: return fragmentCovB;
			}
			return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 7;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:	return "Claim Info";
			case 1:	return "Dwelling Info";
			case 2:	return "Roof";
			case 3: return "Elevations";
			case 4: return "Interior";
			case 5: return "Custom";
			case 6: return "Cov B";
			}
			return null;
		}
	}*/

}
