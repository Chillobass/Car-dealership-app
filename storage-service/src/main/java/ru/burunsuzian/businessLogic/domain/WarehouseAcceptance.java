package ru.burunsuzian.businessLogic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;

import java.util.Set;
import java.util.UUID;

@Getter @AllArgsConstructor
public class WarehouseAcceptance {
    private final UUID id;
    private final UUID carOrderId;
    private final String orderType;
    private final UUID carId;
    private final UUID carModelId;
    private final Set<UUID> requiredComponentsIds;
    private UUID responsibleStaffId;
    private String state;

    public void assignStaff(UUID staffId){
        if ("ASSEMBLED".equals(this.state) || "FAIL".equals(this.state)){
            throw new IllegalStateException("заказ закрыт, сотрудник не нужен");
        }
        this.responsibleStaffId = staffId;
    }

    public void processAssembly(boolean isApproved){
        if ("ASSEMBLED".equals(this.state) || "FAIL".equals(this.state)){
            throw new IllegalStateException("сборка завершена");
        }
        this.state = isApproved ? "ASSEMBLED" : "FAIL";
    }
}
