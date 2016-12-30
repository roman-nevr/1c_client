package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.EquipmentRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
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
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.idle;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.notFound;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.received;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.requested;

/**
 * Created by Admin on 26.12.2016.
 */

public class UpdateEquipmentTaskByBarCodeInteractor extends Interactor {
    @Inject StateKeeper<EquipmentTaskState> stateKeeper;
    private String barCode;

    @Inject
    public UpdateEquipmentTaskByBarCodeInteractor() {}

    public Interactor setBarCode(String barCode){
        this.barCode = barCode;
        return this;
    }

    @Override
    protected void operation() {
        if (barCode == null){
            throw new UnsupportedOperationException("barCode must notifyDataSetChanged be null");
        }
        try {
            Kit kit = stateKeeper.getValue().barCodeEntryMap().get(barCode);
            if(kit != Kit.EMPTY){
                List<Kit> kits = increaseDoneQuantity(barCode, kit);
                updateState(kits, kit);
            }else {
                showError();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            showUnknownError();
        }
        barCode = null;
    }

    private void showUnknownError() {
        stateKeeper.change(state -> state.toBuilder()
                .kitToShow(Kit.EMPTY)
                .transmissionState(error)
                .errorState(ok).build());
    }

    private List<Kit> increaseDoneQuantity(String barCode, Kit kit) {
        List<Kit> result = stateKeeper.getValue().kits();
        int index = result.indexOf(kit);
        for(ShipmentTaskPosition item : kit.kitContent()){
            if(barCode.equals(item.position().barCode())){
                kit = kit.toBuilder().kitContent(newKitContent(item, kit)).build();
            }
        }
        result.set(index, kit);
        return result;
    }

    private List<ShipmentTaskPosition> newKitContent(ShipmentTaskPosition item, Kit kit) {
        List<ShipmentTaskPosition> kitContent = kit.kitContent();
        int index = kit.kitContent().indexOf(item);
        item = item.toBulder().doneQuantity(item.doneQuantity() + 1).build();
        kitContent.set(index, item);
        return kitContent;
    }

    private void updateState(List<Kit> kits, Kit kit) {
        stateKeeper.change(state -> state.toBuilder()
                .kits(kits)
                .kitToShow(kit)
                .barCodeEntryMap(new BarCodeEntryMap(kits))
                .transmissionState(idle)
                .build());
    }


    private void showError() {
        stateKeeper.change(state -> state.toBuilder()
                .kitToShow(Kit.EMPTY)
                .transmissionState(notFound)
                .errorState(ok).build());
    }
}
