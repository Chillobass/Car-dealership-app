package ru.burunsuzian.businessLogic.servicesImpl;

import lombok.extern.slf4j.Slf4j;
import ru.burunsuzian.application.responses.DetailResponse;
import ru.burunsuzian.businessLogic.mappers.FromStringToReplaceable;
import ru.burunsuzian.businessLogic.details.replaceable.Replaceable;
import ru.burunsuzian.businessLogic.domain.valueObjects.Money;
import ru.burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;
import ru.burunsuzian.data.persistence.repositories.DetailRepository;
import ru.burunsuzian.data.persistence.services.DetailManagementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DetailManagementServiceImpl implements DetailManagementService {
    private DetailRepository detailRepository;

    @Override @Transactional
    public DetailResponse add(BigDecimal price, String category) {
        Replaceable detail = FromStringToReplaceable.convert(category);
        ReplaceableComponentType detailEntity = detail.toEntity(new Money(price), new HashSet<UUID>());
        detailRepository.save(detailEntity);
        return new DetailResponse(detailEntity.getId(), detailEntity.toString());
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

    @Override
    public Set<DetailResponse> extractAllDetails() {
        Set<DetailResponse> set = new HashSet<>();
        detailRepository.findAll().forEach(
                (detail) -> set.add(new DetailResponse(detail.getId(), detail.toString())));
        return set;
    }
}
