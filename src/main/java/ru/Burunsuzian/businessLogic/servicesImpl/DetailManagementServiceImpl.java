package ru.Burunsuzian.businessLogic.servicesImpl;

import ru.Burunsuzian.application.responses.DetailResponse;
import ru.Burunsuzian.businessLogic.Mappers.FromStringToReplaceable;
import ru.Burunsuzian.businessLogic.details.replaceable.Replaceable;
import ru.Burunsuzian.businessLogic.domain.valueObjects.Money;
import ru.Burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;
import ru.Burunsuzian.data.persistence.repositories.DetailRepository;
import ru.Burunsuzian.data.persistence.services.DetailManagementService;
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
