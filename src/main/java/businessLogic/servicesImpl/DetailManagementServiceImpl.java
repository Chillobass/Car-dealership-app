package businessLogic.servicesImpl;

import businessLogic.details.Detail;
import data.jpa.replaceableEntities.ReplaceableComponentType;
import data.persistence.repositories.DetailRepository;
import data.persistence.services.DetailManagementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DetailManagementServiceImpl implements DetailManagementService {
    private DetailRepository detailRepository;

    @Override
    public void add(Detail detail) {
        detailRepository.save(detail.getCategory().toEntity(detail.getPrice(), detail.getCompatibleModels()));
    }

    @Override
    public void remove(UUID detail) {
        detailRepository.deleteById(detail);
    }
}
