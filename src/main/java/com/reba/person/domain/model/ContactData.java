package com.reba.person.domain.model;

import com.reba.person.domain.model.enums.ContactDataTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactData {
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ContactDataTypeEnum type;
    private String value;
}
