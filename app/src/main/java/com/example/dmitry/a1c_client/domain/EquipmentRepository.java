package com.example.dmitry.a1c_client.domain;

import com.example.dmitry.a1c_client.domain.entity.Kit;

import java.util.List;

import rx.Single;

/**
 * Created by Admin on 26.12.2016.
 */

public interface EquipmentRepository {
    Single<List<Kit>> getKits(String id);
}
