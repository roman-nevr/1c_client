package com.example.dmitry.a1c_client.data;

import com.example.dmitry.a1c_client.domain.IncomeTaskRepository;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;
import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;
import com.example.dmitry.a1c_client.domain.entity.StoreMapObject;
import com.example.dmitry.a1c_client.misc.dummy.Dummy;

import java.io.IOException;

import rx.Single;

import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.noRightsDialog;

/**
 * Created by roma on 18.12.2016.
 */
public class IncomeTaskRepositoryImpl implements IncomeTaskRepository {
    @Override
    public Single<NomenclaturePosition> getPositionByBarCode(String barCode) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        NomenclaturePosition position = Dummy.NOMENCLATURE_MAP.get(barCode);
        if (barCode.equals("99999999")){
            return Single.error(new IOException());
        }
        if(position == null){
            return Single.just(NomenclaturePosition.EMPTY);
        }else {
            return Single.just(position);
        }
    }

    @Override
    public Single<NomenclaturePosition> getPositionByVendorCode(String vendorCode) {
        if (vendorCode.equals("error")){
            return Single.error(new IOException());
        }
        if(Dummy.VENDOR_CODES_MAP.get(vendorCode) != null){
            return Single.just(Dummy.VENDOR_CODES_MAP.get(vendorCode));
        }else {
            return Single.just(NomenclaturePosition.EMPTY);
        }
    }

    @Override
    public Single<IncomeTaskState> getStorageInfo(IncomeTaskState taskState) {
        if(taskState.quantity() == 99){
            return Single.error(new IOException());
        }
        if (taskState.quantity() == 1){
            return Single.just(taskState.builder().storeMapObject(StoreMapObject.FIRST)
                    .storageElement("полка " + taskState.quantity())
                    .storagePlace("ряд "+taskState.quantity()).build());
        }else return Single.just(taskState.builder().storeMapObject(StoreMapObject.SECOND)
                .storageElement("полка " + taskState.quantity())
                .storagePlace("ряд "+taskState.quantity()).build());
    }

    @Override
    public Single<IncomeTaskState> setNomenclatureBarCode(IncomeTaskState taskState) {
        return Single.just(taskState.builder().state(noRightsDialog).build());
    }
}
