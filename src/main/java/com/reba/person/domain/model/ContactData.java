package com.reba.person.domain.model;

import com.reba.person.domain.model.enums.ContactDataTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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
