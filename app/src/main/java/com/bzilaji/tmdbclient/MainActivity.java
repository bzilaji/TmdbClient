package com.bzilaji.tmdbclient;

import android.support.design.widget.TabLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bzilaji.tmdbclient.fragment.FilterableFragmentBase;
import com.bzilaji.tmdbclient.fragment.MovieListFragment;
import com.bzilaji.tmdbclient.fragment.PeopleListFragment;
import com.bzilaji.tmdbclient.fragment.TvListFragment;
import com.bzilaji.tmdbclient.view.ToolBarSearchView;
import com.bzilaji.tmdbclient.view.ToolBarSearchViewListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ToolBarSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabbed_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final ViewPagerAdapter adapter = createPagerAdapter();
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        searchView = (ToolBarSearchView) findViewById(R.id.search_bar);
        searchView.setListener(new ToolBarSearchViewListener() {
            @Override
            public void onSearchCleared(CharSequence old) {
                adapter.setQuery("");
            }

            @Override
            public void onSearchBarHomePressed() {
                searchView.hide();
            }

            @Override
            public void onSearchTextChanged(CharSequence s) {
                adapter.setQuery(s);
            }
        });


    }

    private ViewPagerAdapter createPagerAdapter() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MovieListFragment(), getString(R.string.movie_tab));
        adapter.addFrag(new TvListFragment(), getString(R.string.tv_tab));
        adapter.addFrag(new PeopleListFragment(), getString(R.string.people_tab));
        return adapter;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        VectorDrawableCompat search = getCompactDrawable(R.drawable.ic_search_black_24dp);
        tintToWhite(search);
        menu.findItem(R.id.search).setIcon(search);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = false;
        if (item.getItemId() == R.id.search) {
            searchView.show();
            handled = true;
        }
        return handled || super.onOptionsItemSelected(item);
    }


    @Override

    public void onBackPressed() {
        if (searchView.getVisibility() == View.VISIBLE) {
            searchView.hide();
        } else {
            super.onBackPressed();
        }
    }

    private VectorDrawableCompat getCompactDrawable(int resId) {
        return VectorDrawableCompat.create(getResources(), resId, getTheme());
    }

    private void tintToWhite(VectorDrawableCompat drawable) {
        drawable.setTint(ResourcesCompat.getColor(getResources(), android.R.color.white, getTheme()));
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<FilterableFragmentBase> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public void setQuery(CharSequence query) {
            for (FilterableFragmentBase fragment : fragmentList) {
                fragment.setQuery(query);
            }
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFrag(FilterableFragmentBase fragment, String title) {
            fragment.setRetainInstance(true);
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
