package edu.uw.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity implements MovieListFragment.OnMovieSelectedListener,SearchFragment.onSearchListener{

    private static final String TAG = "MainActivity";
    public static final String MOVIE_LIST_FRAGMENT_TAG = "MoviesListFragment";
    public static final String MOVIE_DETAIL_FRAGMENT_TAG = "DetailFragment";


    private ViewPager viewPager;
    private MoviePagerAdapter pagerAdapter;
    private SearchFragment searchFragment;
    private MovieListFragment movieListFragment;
    private DetailFragment detailFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchFragment = SearchFragment.newInstance();

        viewPager = (ViewPager)findViewById(R.id.viewPager);

        pagerAdapter = new MoviePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

    }

    //respond to search button clicking
//    public void handleSearchClick(View v){
//        EditText text = (EditText)findViewById(R.id.txt_search);
//        String searchTerm = text.getText().toString();
//
//        MovieListFragment fragment = MovieListFragment.newInstance(searchTerm);
//
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.container, fragment, MOVIE_LIST_FRAGMENT_TAG);
//        ft.addToBackStack(null);
//        ft.commit();
//    }


    public void onSearchSubmitted(String searchTerm){
        movieListFragment = MovieListFragment.newInstance(searchTerm);
        pagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onMovieSelected(Movie movie) {
        detailFragment = DetailFragment.newInstance(movie);
        pagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(2);

    }


    private class MoviePagerAdapter extends FragmentStatePagerAdapter{
        public MoviePagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position){
            if (position==0){return searchFragment;}
            if (position==1){return movieListFragment;}
            if (position==2){return detailFragment;}
            return null;
        }

        public int getItemPosition(Object o){
            return POSITION_NONE;
        }

        @Override
        public int getCount(){
            if (movieListFragment==null){
                return 1;
            } else if (detailFragment==null){
                return 2;
            } else {
                return 3;
            }
        }
    }

    @Override
    public void onBackPressed(){
        if (viewPager.getCurrentItem() == 0){
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
        }
    }

}
