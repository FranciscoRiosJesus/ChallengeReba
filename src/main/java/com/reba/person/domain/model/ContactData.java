package com.reba.person.domain.model;

import com.reba.person.domain.model.enums.ContactDataTypeEnum;
import jakarta.persistence.*;
import lombok.Data;

@Embeddable
@Data
public class ContactData {
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ContactDataTypeEnum type;
    private String value;
}
