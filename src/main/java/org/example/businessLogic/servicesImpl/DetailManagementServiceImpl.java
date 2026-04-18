package org.example.businessLogic.servicesImpl;

import org.example.application.responses.DetailResponse;
import org.example.businessLogic.Mappers.FromStringToReplaceable;
import org.example.businessLogic.details.replaceable.Replaceable;
import org.example.businessLogic.domain.valueObjects.Money;
import org.example.data.jpa.replaceableEntities.ReplaceableComponentType;
import org.example.data.persistence.repositories.DetailRepository;
import org.example.data.persistence.services.DetailManagementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DetailManagementServiceImpl implements DetailManagementService {
    private DetailRepository detailRepository;

    @Override @Transactional
    public DetailResponse add(BigDecimal price, String category) {
        Replaceable a = FromStringToReplaceable.convert(category);
        ReplaceableComponentType b = a.toEntity(new Money(price), new HashSet<UUID>());
        detailRepository.save(b);
        return new DetailResponse(b.getId());
    }

    @Override @Transactional
    public void remove(UUID detail) {
        detailRepository.deleteById(detail);
    }

    @Transactional
    public void addCompatibleModel(UUID detailId, UUID carModelId){
        detailRepository.getReferenceById(detailId).getCompatibleCars().add(carModelId);
    }

    public String getDetail(UUID detailId){
        return detailRepository.getReferenceById(detailId).toString();
    }
}
