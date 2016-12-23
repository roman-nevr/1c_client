package com.example.dmitry.a1c_client.domain;

import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;
import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;

import rx.Single;

/**
 * Created by roma on 18.12.2016.
 */
public interface IncomeTaskRepository {
    Single<NomenclaturePosition> getPositionByBarCode(String barCode);
    Single<NomenclaturePosition> getPositionByVendorCode(String vendorCode);
    Single<IncomeTaskState> getStorageInfo(IncomeTaskState taskState);
    Single<Boolean> saveBarCode(NomenclaturePosition position, String barCode);
}
