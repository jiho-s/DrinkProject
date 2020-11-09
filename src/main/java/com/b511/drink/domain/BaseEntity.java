package com.b511.drink.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.id == null;
    }

}
