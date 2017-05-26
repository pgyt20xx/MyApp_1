package com.pgyt20xx.myapp_1;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pgyt20xx.myapp_1.model.CategoryBean;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;

    ViewPager mViewPager;

    DBHelper dBhelper = null;

    /**
     * デフォルトタブ
     */
    private static int PAGE_COUNT = 0;

    private static String BLANK_STRING = "";

    private static ArrayList<String> TITLE_NAME = new ArrayList<>();


    /**
     * タグ:MainActivity
     */
    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cursor cursor = null;
        try {
            dBhelper = new DBHelper(this.getApplicationContext());
            cursor = dBhelper.selectCategory(BLANK_STRING);

        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        // 取得したレコードの件数がページ数
        PAGE_COUNT = cursor.getCount();

        // 登録されているカテゴリー名を保持する
        boolean isEof = cursor.moveToFirst();
        while(isEof){
            TITLE_NAME.add(cursor.getString(cursor.getColumnIndex("category_name")));
            isEof = cursor.moveToNext();
        }
        cursor.close();

        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    /**
     * タイトルバーにメニューボタンを表示
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manu_main, menu);
        return true;
    }

    /**
     * メニューボタン押下時
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // 押下されたメニューで分岐
        switch (item.getItemId()) {
            case R.id.item1:
                createDialogEvent();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // TODO 取得したコンテンツテーブルのpositionのcontents_nameをタイトルに設定する。
        @Override
        public Fragment getItem(int position) {
            setTitle(TITLE_NAME.get(position));
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

        DBHelper dBhelper = null;

        public SectionFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
//            try {
//                dBhelper = new DBHelper(getActivity());
//
//            } catch (Exception e) {
//                Log.d(TAG, e.getMessage());
//            }
//
//            Cursor cursor = dBhelper.selectCategory(null);//
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;

        }
    }

    /**
     * ダイアログイベント
     */
    private void createDialogEvent() {
        final EditText editView = new EditText(MainActivity.this);

        final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this, R.style.MyAlertDialogStyle);
        dialog.setTitle(R.string.menu_item1);
        dialog.setView(editView);

        // OKボタン押下時
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 値が入力された場合はDBに登録
                if (!TextUtils.isEmpty(editView.getText())) {
                    CategoryBean param = new CategoryBean();
                    param.setCategory_name(editView.getText().toString());
                    dBhelper.insertCategory(param);
                }
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            // Cancelボタン押下時
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        dialog.show();
    }
}
