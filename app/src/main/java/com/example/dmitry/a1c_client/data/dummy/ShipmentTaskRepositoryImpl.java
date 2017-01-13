package com.example.dmitry.a1c_client.data.dummy;

import com.example.dmitry.a1c_client.domain.ShipmentTaskRepository;
import com.example.dmitry.a1c_client.domain.entity.Enums;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState;
import com.example.dmitry.a1c_client.misc.dummy.Dummy;
import com.example.dmitry.a1c_client.misc.utils;

import java.util.Collections;
import java.util.List;

import rx.Single;

import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.received;

/**
 * Created by Admin on 23.12.2016.
 */

public class ShipmentTaskRepositoryImpl implements ShipmentTaskRepository {

    @Override public Single<List<ShipmentTaskPosition>> getTask(String id) {
        utils.delay(1000);
        return Single.just(Collections.unmodifiableList(Dummy.SHIPMENT_TASK));
    }

    @Override public Single<ShipmentTaskState> saveProgress(ShipmentTaskState taskState) {
        utils.delay(1000);
        return Single.just(taskState.toBuilder()
                .transmissionState(received)
                .errorState(ok)
                .build());
    }

    @Override public Single<ShipmentTaskState> postInsufficientReport(ShipmentTaskState taskState) {
        utils.delay(1000);
        return Single.just(taskState.toBuilder()
                .transmissionState(received)
                .errorState(ok)
                .build());
    }
}
