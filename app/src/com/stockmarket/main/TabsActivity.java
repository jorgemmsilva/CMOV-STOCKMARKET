package com.stockmarket.main;

import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Application;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stockmarket.R;
import com.stockmarket.basics.CallServiceTask;
import com.stockmarket.basics.RestClient;
import com.stockmarket.basics.Stock;
import com.stockmarket.basics.StockAdapter;
import com.stockmarket.basics.StockManager;
import com.stockmarket.basics.SubtitlesAdapter;

public class TabsActivity extends FragmentActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		
		setContentView(R.layout.activity_tabs);

		
		// Set up the action bar.
		
	
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tabs, menu);
		return true;
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

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		
		Fragment[] fragments =  new Fragment[3];
		
		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a SectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			
			if(fragments[position] == null)
			{
				fragments[position] = new SectionFragment(position);

			}
			else
				((SectionFragment) fragments[position]).refresh();
			return fragments[position];
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
		
		
		
		
	}


	@SuppressLint("ValidFragment")
	public static class SectionFragment extends Fragment implements RestClient {

		public static final String ARG_SECTION_NUMBER = "section_number";
		
		
		private String stocks ="AA+HPQ+AIG+BA+DIS+CAT+T+INTC+AXP+MSFT+HON+GE+UTX+MMM+MRK+IBM+WMT+HD+MO+VZ+XOM+PG+JPM+DD+MCD+PFE+JNJ+C+KO+GM";
		private String mystocks = "HON+AIG+BA";
		private String url = "http://finance.yahoo.com/d/quotes?f=sghw1&s";
		
		ArrayList<Stock> stockarray;

		
		int fragnumber;
		View rootView;
		
		public SectionFragment(int position) {
			fragnumber = position;
			stockarray= new ArrayList<Stock>();

		}

		public void refresh()
		{
			ListView l = (ListView)rootView.findViewById(R.id.list);
			l.setAdapter(new StockAdapter(rootView.getContext(),R.id.empty, stockarray,fragnumber, getActivity().getApplication()));
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			
			CallServiceTask cst = new CallServiceTask(this);
			
			switch(fragnumber)
			{
				case 0:
					cst.execute(url + "=" + mystocks);
					rootView = inflater.inflate(R.layout.activity_main,container, false);
					break;
				case 1:
					cst.execute(url + "=" + stocks);
					rootView = inflater.inflate(R.layout.activity_main,container, false);
					break;
				case 2:
					//rootView = new CanvasView(container.getContext());
					rootView = inflater.inflate(R.layout.activity_chart,container, false);
					drawchart(container);
					break;

			}
			
			
			//dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			return rootView;
		}

		
		private void drawchart(ViewGroup container) {
			//CanvasView canvasview = (CanvasView) rootView.findViewById(R.id.canvas);
			
			
			ListView subtitles = (ListView) rootView.findViewById(R.id.subtitlelist);
			subtitles.setAdapter(new SubtitlesAdapter(rootView.getContext(),fragnumber,getActivity().getApplication(),((StockManager)getActivity().getApplication()).mystocks));
			
			CanvasView cv = new CanvasView(getActivity(),rootView);
			
		}

		@Override
		public void onBackgroundTaskCompleted(String result) {
			if(fragnumber == 1 || fragnumber == 0)
			{
				Application app =  getActivity().getApplication();
				
				switch(fragnumber){
					case 0:
						stockarray = ((StockManager) app).mystocks;
						break;
					case 1:
						stockarray = ((StockManager) app).explorestocks;
						break;
					default: 
						break;
						
				}
				if(result!= "")
				{
					String[] s = result.split("\n");
					for (String entry : s) {
						String[] stockrow = entry.split(",");
						stockrow[0] = stockrow[0].replaceAll("\"","");
						//stockrow[0] = stockrow[0]aux.replaceAll("\\s+","");
						stockrow[3] = stockrow[3].substring(5,stockrow[3].length()-3);
						
						for( int i=0 ; i< stockrow.length; ++i)
						{
							if (stockrow[i].equals("N/A"))
								stockrow[i] = "0";
						}

						
						Stock stk = new Stock(stockrow[0], stockrow[0], Float.parseFloat(stockrow[2]), Float.parseFloat(stockrow[1]), 0, Float.parseFloat(stockrow[3]), 1);
						
						if(!((StockManager) app).containsStock(stockarray, stk))
							stockarray.add(stk);
					}
				}
					ListView l = (ListView)rootView.findViewById(R.id.list);
					
					l.setAdapter(new StockAdapter(rootView.getContext(), R.id.empty, stockarray,fragnumber, app));
					
					final ArrayList<Stock> stockarraycopy = stockarray;
					
					l.setOnItemClickListener( new AdapterView.OnItemClickListener() {
	
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							Intent i = new Intent(getActivity(), DetailsComp.class);
							i.putExtra("name", stockarraycopy.get((int) arg3).name);
							startActivity(i);
							
						}
					});
					if(fragnumber == 0){
						l.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
		
							@Override
							public boolean onItemLongClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								
									final Dialog dialog = new Dialog(rootView.getContext());
									dialog.setContentView(R.layout.changestocknumberdialog);
									final Stock item = stockarraycopy.get((int) arg3);
									dialog.setTitle(item.name);
									TextView stockslbl = (TextView) dialog.findViewById(R.id.mystocksnumber);
									stockslbl.setText(((Integer)item.owned).toString());
									
									
									TextView plusbtn = (TextView) dialog.findViewById(R.id.plusbtn);
									TextView minusbtn = (TextView) dialog.findViewById(R.id.minusbtn);
									
									plusbtn.setOnClickListener( new View.OnClickListener() {
										
										@Override
										public void onClick(View v) {
											TextView stockstext = (TextView) dialog.findViewById(R.id.mystocksnumber);
											Integer i = Integer.parseInt((String) stockstext.getText());
											i++;
											stockstext.setText(i.toString());
										}
									});
									
									minusbtn.setOnClickListener( new View.OnClickListener() {
										
										@Override
										public void onClick(View v) {
											TextView stockstext = (TextView) dialog.findViewById(R.id.mystocksnumber);
											Integer i = Integer.parseInt((String) stockstext.getText());
											if(i>0)
												i--;
											stockstext.setText(i.toString());
										}
									});
									dialog.setCanceledOnTouchOutside(true);
									dialog.setOnCancelListener( new DialogInterface.OnCancelListener() {
										
										@Override
										public void onCancel(DialogInterface dialog) {
											TextView stockstext = (TextView)((Dialog) dialog).findViewById(R.id.mystocksnumber);
											Integer i = Integer.parseInt((String) stockstext.getText());
											((StockManager) getActivity().getApplication()).setOwned(item.name,i);
										}
									});
									
									dialog.show();
								return false;
							}
						});
					}


				TextView v = (TextView)rootView.findViewById(R.id.empty);
				
				if(stockarray.isEmpty())
				{
					v.setVisibility(View.VISIBLE);
				}
				else
				{
					v.setVisibility(View.GONE);
				}
				return;
			}
		}
		
		
	}
	
	
	
	

}
