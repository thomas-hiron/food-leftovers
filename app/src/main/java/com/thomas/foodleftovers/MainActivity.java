package com.thomas.foodleftovers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.thomas.foodleftovers.fragments.ListFragment;
import com.thomas.foodleftovers.fragments.SearchFragment;
import com.thomas.foodleftovers.interfaces.listeners.OnSearch;
import com.thomas.foodleftovers.view_pager.ViewPagerAdapter;

public class MainActivity extends FragmentActivity implements ViewTreeObserver.OnGlobalLayoutListener, OnSearch
{
    public static final String APP_TAG = "FOOD";
    private static final int CAMERA = 1;

    private boolean mKeyboardOpened;

    private ListFragment mListFragment;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Création du pager */
        mViewPager = findViewById(R.id.view_pager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);

        /* Ajout du fragment de base */
        mListFragment = new ListFragment();
        mViewPagerAdapter.add(mListFragment);
        mViewPagerAdapter.notifyDataSetChanged();

        /* Demande de permission */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.i("FOOD", "pas de permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA);
        }

        /* Gestion de l'ouverture du clavier */
        final View activityRootView = getWindow().getDecorView().findViewById(android.R.id.content);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case CAMERA:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Cannot run application because camera service permission have not been granted", Toast.LENGTH_SHORT).show();
                    mListFragment.onPause();
                    mListFragment.setBarcodeVisibility(View.GONE);
                }
                else {
                    mListFragment.onResume();
                }

                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        return mListFragment.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed()
    {
        int currentItem = mViewPager.getCurrentItem();

        /* Pas première page, retour */
        if (currentItem != 0) {
            /* Scroll à la dernière page */
            mViewPager.setCurrentItem(currentItem - 1, true);

            /* Redémarrage barcode scanner sur la première page */
            if (mViewPager.getCurrentItem() == 0) {
                mListFragment.onResume();
            }
        }
        /* Sinon fermeture */
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void onGlobalLayout()
    {
        final View activityRootView = getWindow().getDecorView().findViewById(android.R.id.content);
        int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
        /* 99% of the time the height diff will be due to a keyboard. */
        if (heightDiff > 100) {
            if (!mKeyboardOpened) {
                /* Suppression du scanner */
                mListFragment.onPause();
                mListFragment.setBarcodeVisibility(View.GONE);
            }
            mKeyboardOpened = true;
        }
        else if (mKeyboardOpened) {
            /* Ajout du scanner */
            mListFragment.onResume();
            mListFragment.setBarcodeVisibility(View.VISIBLE);
            mKeyboardOpened = false;
        }
    }

    @Override
    public void onSearch()
    {
        /* Récupération fragment de recherche */
        int searchFragmentIndex = mViewPagerAdapter.indexOfFragment(SearchFragment.class.getName());

        /* Ajout de la recherche */
        SearchFragment searchFragment;
        if (searchFragmentIndex == -1) {
            searchFragment = new SearchFragment();
            mViewPagerAdapter.add(searchFragment);
            mViewPagerAdapter.notifyDataSetChanged();

            /* Scroll à la dernière page */
            mViewPager.setCurrentItem(mViewPagerAdapter.getCount() - 1);
        }
        else {
            searchFragment = (SearchFragment) mViewPagerAdapter.getItem(searchFragmentIndex);

            /* Scroll à la bonne page et reset des données */
            searchFragment.reset();
            mViewPager.setCurrentItem(searchFragmentIndex);
        }

        /* Lancement de la recherche */
        searchFragment.search(mListFragment.getIngredients());

        /* Pause */
        mListFragment.onPause();
    }
}