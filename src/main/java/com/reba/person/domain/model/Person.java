package com.reba.person.domain.model;

import com.reba.person.domain.model.enums.CountryEnum;
import com.reba.person.domain.model.enums.DocumentTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"documentType", "documentNumber", "country"}))
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NonNull
    private String name;

    @Enumerated(EnumType.STRING)
    private DocumentTypeEnum documentType;

    private String documentNumber;

    @Enumerated(EnumType.STRING)
    private CountryEnum country;

    @NonNull
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @NonNull
    @ElementCollection
    private List<ContactData> contactData;

}
