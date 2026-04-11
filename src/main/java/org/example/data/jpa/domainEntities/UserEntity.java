package org.example.data.jpa.domainEntities;

import org.example.data.jpa.BaseJpaEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "users")
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class UserEntity extends BaseJpaEntity {
    @Column(name = "user_type")
    private String userType;
}
