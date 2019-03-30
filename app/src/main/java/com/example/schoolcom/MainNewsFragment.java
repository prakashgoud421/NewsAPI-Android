package com.example.schoolcom;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.schoolcom.news.Business;
import com.example.schoolcom.news.Cricket;
import com.example.schoolcom.news.Financial;
import com.example.schoolcom.news.Football;
import com.example.schoolcom.news.Music;
import com.example.schoolcom.news.Science;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Auther: prakash
 */

public class MainNewsFragment extends Fragment  {
    static String tabClicked;
    public static boolean flag = false;
    private boolean isLoggedIn;
    public static String pageTitle = "xxx";
    boolean connected;
    SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_news, container, false);
        setHasOptionsMenu(true);

        Check chk = new Check();
        chk.execute();


      /*View pager creation for Tabs and set up*/
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.getAdapter().notifyDataSetChanged();

        /*Tab layout for different news Catogeries*/


        TabLayout tabs = (TabLayout) v.findViewById(R.id.tabs);

        tabs.setupWithViewPager(viewPager);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pageTitle = tab.getText().toString();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return v;
    }//onCreate

   /*Set up View Pager with Fragments*/
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        try {
            adapter.addFragment(new Science(), "General");
            adapter.addFragment(new Business(), "Business");
            adapter.addFragment(new Music(), "Entertainment");

            adapter.addFragment(new Financial(), "Tech/Science");
            adapter.addFragment(new Cricket(), "Cricket");
            adapter.addFragment(new Football(), "Football");
        } catch (IllegalStateException e) {

        }


        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

    }



    class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {

            try {
                mFragmentList.add(fragment);
                mFragmentTitleList.add(title);
            } catch (IllegalStateException e) {

            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }

//--------To Check Internet Connection

    public class Check extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected Void doInBackground(Void... params) {
            if (isOnline())
                connected = true;
            else
                connected = false;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            if (!connected) {
                Toast.makeText(getActivity(), "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
            super.onPostExecute(result);
        }


    }//inner class

    private boolean isOnline() {
        if (isConnected()) {
            try {
                URL url = new URL("http://www.google.com");  // or any valid link.
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setConnectTimeout(3000);
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    return true;
                } else
                    return false;
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}