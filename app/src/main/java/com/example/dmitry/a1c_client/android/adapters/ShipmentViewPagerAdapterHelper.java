package com.example.dmitry.a1c_client.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.dmitry.a1c_client.android.fragments.ShipmentAdapterFragment;
import com.example.dmitry.a1c_client.presentation.entity.ShipmentViewState;

/**
 * Created by Admin on 13.12.2016.
 */

public class ShipmentViewPagerAdapterHelper {

    private PagerAdapter adapter;
    private ViewPager mViewPager;
    private boolean swipeToRight;
    private ShipmentViewState viewState;
    private boolean isLastVisiblePage;

    public ShipmentViewPagerAdapterHelper(ViewPager viewPager, FragmentManager fragmentManager,
                                          ShipmentViewState viewState) {
        this.mViewPager = viewPager;
        this.viewState = viewState;
        this.adapter = new UpdatableAdapter(fragmentManager);
    }

    public void bindAdapterAndPageChangeListener(){
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new PageChangeListener());
    }

    public PagerAdapter provideAdapter(){
        return adapter;
    }


    public void removeVisiblePage(){
        int currentItem = mViewPager.getCurrentItem();
        if(currentItem != viewState.size() - 1){//has next page?
            mViewPager.setCurrentItem(currentItem + 1, true);
            swipeToRight = true;
        }else {
            mViewPager.setCurrentItem(currentItem - 1, true);
            swipeToRight = false;
        }
    }

    private class UpdatableAdapter extends FixedFragmentStatePagerAdapter{
        public UpdatableAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return viewState.size();
        }

        @Override
        public Fragment getItem(int position) {
            return ShipmentAdapterFragment.newInstance(position);
        }

        @Override
        public String getTag(int position) {
            return "shipment"+viewState.get(position).position().id();
        }

        @Override
        public int getItemPosition(Object object){
            return POSITION_NONE;
        }

    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE){
                if(viewState.hasMarkedItem()){
                    viewState.removeMarkedItem();
                    if(viewState.showOnlyActual){
                        int currentItem = mViewPager.getCurrentItem();
                        adapter.notifyDataSetChanged(); //количество страниц меняется
                        if(currentItem != viewState.size() && swipeToRight){
                            mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1, false);
                        }
                    }
                }
            }
        }
    }
}
