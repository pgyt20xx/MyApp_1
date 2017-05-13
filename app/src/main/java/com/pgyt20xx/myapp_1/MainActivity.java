package com.pgyt20xx.myapp_1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pgyt20xx.myapp_1.model.CategoryBean;

import java.util.Locale;

public class MainActivity extends FragmentActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;

    ViewPager mViewPager;

    /**
     * デフォルトタブ
     */
    private static int PAGE_COUNT = 0;

    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBHelper dBhelper = null;
        try {
            dBhelper = new DBHelper(this.getApplicationContext());

        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = new SectionFragment();
            Bundle args = new Bundle();
            args.putInt(SectionFragment.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * 生成するページ数
         *
         * @return
         */
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }

    public static class SectionFragment extends Fragment {

        public static final String ARG_SECTION_NUMBER = "section_number";

        public SectionFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;

        }
    }
}
