package com.example.dmitry.a1c_client.android;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainWindowActivity extends BaseActivity {

    @BindView(R.id.tv_income) TextView tvIncome;
    @BindView(R.id.tv_shipment) TextView tvShipment;
    @BindView(R.id.tv_equip) TextView tvEquip;

    public static final String INCOME_TAG = "Приход";
    public static final String SHIPMENT_TAG = "Расход";
    public static final String EQUIPMENT_TAG = "Комплектация";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Fragment shipmentFragment = new IncomeListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, shipmentFragment, INCOME_TAG).commit();
            setTitle(INCOME_TAG);
        }
    }

    @OnClick(R.id.tv_income) void onIncomeClick(){
        showNewFragment(getFragmentByTag(INCOME_TAG), INCOME_TAG);
        setTitle(INCOME_TAG);
    }

    @OnClick(R.id.tv_shipment) void onShipmentClick(){
        showNewFragment(getFragmentByTag(SHIPMENT_TAG), SHIPMENT_TAG);
        setTitle(SHIPMENT_TAG);
    }

    @OnClick(R.id.tv_equip) void onEquipClick(){
        showNewFragment(getFragmentByTag(EQUIPMENT_TAG), EQUIPMENT_TAG);
        setTitle(EQUIPMENT_TAG);
    }

    private void showNewFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment, tag).commit();
        getSupportFragmentManager().executePendingTransactions();
        //currentTag = tag;
        //toolbar.setTitle(tag);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_main;
    }

    private Fragment getFragmentByTag(String tag) {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null) {
            for (Fragment fragment : fragmentList) {
                if ((fragment != null) && (fragment.getTag().equals(tag))) {
                    return fragment;
                }
            }
        }
        if (tag.equals(INCOME_TAG)) {
            return new IncomeListFragment();
        }
        if (tag.equals(SHIPMENT_TAG)) {
            return new ShipmentListFragment();
        }
        if (tag.equals(EQUIPMENT_TAG)) {
            return new EquipListFragment();
        }
        return null;
    }
}
