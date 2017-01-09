package com.example.dmitry.a1c_client.data;

import com.example.dmitry.a1c_client.domain.EquipmentRepository;
import com.example.dmitry.a1c_client.domain.entity.Kit;
import com.example.dmitry.a1c_client.misc.dummy.Dummy;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Single;

/**
 * Created by Admin on 26.12.2016.
 */

public class EquipmentRepositoryImpl implements EquipmentRepository {
    @Override public Single<List<Kit>> getKits(String id) {
        return Single
                .just(Dummy.EQUIPMENT_TASK).delay(1, TimeUnit.SECONDS);
    }
}
