package com.example.dmitry.a1c_client.android;

import android.app.Application;
import android.util.Log;

import com.example.dmitry.a1c_client.di.DaggerMainComponent;
import com.example.dmitry.a1c_client.di.equipment.CollectionTaskViewComponent;
import com.example.dmitry.a1c_client.di.equipment.DaggerEquipmentTaskComponent;
import com.example.dmitry.a1c_client.di.equipment.EquipmentTaskComponent;
import com.example.dmitry.a1c_client.di.income_task.DaggerIncomeTaskComponent;
import com.example.dmitry.a1c_client.di.income_task.IncomeTaskComponent;
import com.example.dmitry.a1c_client.di.income_task.IncomeTaskModule;
import com.example.dmitry.a1c_client.di.MainComponent;
import com.example.dmitry.a1c_client.di.shipment_task.DaggerShipmentTaskComponent;
import com.example.dmitry.a1c_client.di.shipment_task.ShipmentTaskComponent;
import com.example.dmitry.a1c_client.misc.Logger;
import com.example.dmitry.a1c_client.presentation.IncomeTaskView;

import java.util.Date;


public class MyApplication extends Application {
    private MainComponent mainComponent;
    private IncomeTaskComponent incomeTaskComponent;
    private ShipmentTaskComponent shipmentTaskComponent;
    private EquipmentTaskComponent equipmentTaskComponent;
    public static Logger logger = new Logger(false);

    @Override
    public void onCreate() {
        super.onCreate();
        initDI();
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }

    public IncomeTaskComponent getIncomeTaskComponent() {
        if (incomeTaskComponent == null) {
            incomeTaskComponent = DaggerIncomeTaskComponent.create();
        }
        return incomeTaskComponent;
    }

    public void clearIncomeTaskComponent(){
        incomeTaskComponent = null;
    }

    public ShipmentTaskComponent getShipmentTaskComponent(){
        if(shipmentTaskComponent == null){
            shipmentTaskComponent = DaggerShipmentTaskComponent.create();
        }
        return shipmentTaskComponent;
    }
    public void clearShipmentTaskComponent(){
        shipmentTaskComponent = null;
    }

    public EquipmentTaskComponent getEquipmentTaskComponent(){
        if(equipmentTaskComponent == null){
            equipmentTaskComponent = DaggerEquipmentTaskComponent.create();
        }
        return equipmentTaskComponent;
    }

    public void clearEquipmentTaskComponent(){
        equipmentTaskComponent = null;
    }

    private void initDI() {
        mainComponent = DaggerMainComponent.create();
    }

    private static long time = currentTime();
    private static long currentTime(){
        return (new Date()).getTime();
    }
    public static void log(String message){
        Log.d("rectest", message + " "+ (currentTime() - time));
        time = currentTime();
    }

}
