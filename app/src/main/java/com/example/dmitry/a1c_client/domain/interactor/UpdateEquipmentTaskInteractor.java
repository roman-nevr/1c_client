package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.EquipmentRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.Enums;
import com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState;
import com.example.dmitry.a1c_client.domain.entity.Kit;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.presentation.entity.BarCodeEntryMap;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notComplete;
import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notInitailased;
import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.connectionError;
import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.error;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.received;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.requested;
import static com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState.Stage.collect;

/**
 * Created by Admin on 26.12.2016.
 */

public class UpdateEquipmentTaskInteractor extends Interactor {

    @Inject StateKeeper<EquipmentTaskState> stateKeeper;
    @Inject EquipmentRepository repository;

    @Inject
    public UpdateEquipmentTaskInteractor() {}

    private String id;
    public Interactor setId(String id){
        this.id = id;
        return this;
    }

    @Override
    protected void operation() {
        if (id == null){
            throw new UnsupportedOperationException();
        }
        try {
            showProgress();
            List<Kit> kits = loadKits(id);
            List<ShipmentTaskPosition> positions = calculatePositions(kits);
            updateState(kits, positions);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            showError();
        }
    }

    private List<ShipmentTaskPosition> calculatePositions(List<Kit> kits) {
        List<ShipmentTaskPosition> result = new ArrayList<>();
        for(Kit kit : kits){
            for (ShipmentTaskPosition item : kit.kitContent()){
                int index = result.indexOf(item);
                if(index == -1){
                    result.add(item);
                }else{
                    incrementRequiredQuantity(index, result, item.requiredQuantity());
                }
            }
        }
        return result;
    }

    private void incrementRequiredQuantity(int index, List<ShipmentTaskPosition> result, int diff) {
        result.set(index, result.get(index).toBulder().requiredQuantity(result.get(index).requiredQuantity() + diff).build());
    }

    private void updateState(List<Kit> kits, List<ShipmentTaskPosition> positions) {
        stateKeeper.change(state -> state.toBuilder()
                .kits(kits)
                .positions(positions)
                .barCodeEntryMap(new BarCodeEntryMap(kits))
                .completeState(notComplete)
                .stage(collect)
                .transmissionState(received)
                .errorState(ok).build());
    }

    private List<Kit> loadKits(String id) {
        return repository.getKits(id).toBlocking().value();
    }

    private void showError() {
        stateKeeper.change(state -> state.toBuilder()
                .completeState(notInitailased)
                .transmissionState(error)
                .errorState(connectionError).build());
    }

    private void showProgress() {
        stateKeeper.change(state -> state.toBuilder()
                .completeState(notComplete)
                .transmissionState(requested)
                .errorState(ok).build());
    }
}
