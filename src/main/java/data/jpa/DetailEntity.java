package data.jpa;

import businessLogic.CarModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity @Getter @NoArgsConstructor @AllArgsConstructor
@Table(name = "detail")
public class DetailEntity extends BaseJpaEntity {
    private UUID id;
    private String category;
    private BigDecimal price;

    @ManyToMany
    @JoinTable(
            name="detail_compatible_models",
            joinColumns = @JoinColumn(name = "detail_id"), // внешний ключ на DetailEntity
            inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    private List<CarModel> compatibleModels;
}
