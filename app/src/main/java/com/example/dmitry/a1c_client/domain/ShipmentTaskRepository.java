package com.example.dmitry.a1c_client.domain;

import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState;

import java.util.List;

import rx.Single;

/**
 * Created by Admin on 23.12.2016.
 */

public interface ShipmentTaskRepository {
    Single<List<ShipmentTaskPosition>> getTask(String id);
    Single<ShipmentTaskState> saveProgress(ShipmentTaskState taskState);
    Single<ShipmentTaskState> postInsufficientReport(ShipmentTaskState taskState);
}
