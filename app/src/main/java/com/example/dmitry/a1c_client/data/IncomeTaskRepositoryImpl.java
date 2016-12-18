package com.example.dmitry.a1c_client.data;

import com.example.dmitry.a1c_client.domain.IncomeTaskRepository;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;
import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;

import rx.Single;

/**
 * Created by roma on 18.12.2016.
 */
public class IncomeTaskRepositoryImpl implements IncomeTaskRepository {
    @Override
    public Single<NomenclaturePosition> getPositionByBarCode(String barCode) {
        return null;
    }

    @Override
    public Single<NomenclaturePosition> getPositionByVendorCode(String vendorCode) {
        return null;
    }

    @Override
    public Single<IncomeTaskState> getStorageInfo(IncomeTaskState taskState) {
        return null;
    }

    @Override
    public Single<IncomeTaskState> setNomenclatureBarCode(IncomeTaskState taskState) {
        return null;
    }
}
