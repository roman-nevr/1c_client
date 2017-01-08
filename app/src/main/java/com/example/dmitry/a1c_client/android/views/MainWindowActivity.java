package com.example.dmitry.a1c_client.android.views;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.dmitry.a1c_client.android.MyApplication.log;

public class MainWindowActivity extends BaseActivity {

    @BindView(R.id.tv_income) TextView tvIncome;
    @BindView(R.id.tv_shipment) TextView tvShipment;
    @BindView(R.id.tv_equip) TextView tvEquip;

    public static final String INCOME_TAG = "Приход";
    public static final String SHIPMENT_TAG = "Расход";
    public static final String EQUIPMENT_TAG = "Комплектация";

    private FragmentTransaction transaction;

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            String tag = EQUIPMENT_TAG;
            Fragment shipmentFragment = getFragmentByTag(tag);
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, shipmentFragment, tag).commit();
            setTitle(tag);
        }
    }

    @OnClick(R.id.tv_income) void onIncomeClick(){
        log("onIncomeClick");
        showNewFragment(getFragmentByTag(INCOME_TAG), INCOME_TAG);
        //TestActivity.start(this);
        setTitle(INCOME_TAG);

    }

    @OnClick(R.id.tv_shipment) void onShipmentClick(){
        log("onShipmentClick");
        showNewFragment(getFragmentByTag(SHIPMENT_TAG), SHIPMENT_TAG);
        setTitle(SHIPMENT_TAG);

    }

    @OnClick(R.id.tv_equip) void onEquipClick(){
        log("onEquipClick");
        showNewFragment(getFragmentByTag(EQUIPMENT_TAG), EQUIPMENT_TAG);
        setTitle(EQUIPMENT_TAG);

    }

    private void showNewFragment(Fragment fragment, String tag) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, fragment, tag).commit();
        getSupportFragmentManager().executePendingTransactions();
        //currentTag = tag;
        //toolbar.setTitle(tag);
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
            log("new income");
            return new IncomeListFragment();
        }
        if (tag.equals(SHIPMENT_TAG)) {
            return new ShipmentListFragment();
        }
        if (tag.equals(EQUIPMENT_TAG)) {
            log("new equip");
            return new EquipListFragment();
        }
        return null;
    }
}
