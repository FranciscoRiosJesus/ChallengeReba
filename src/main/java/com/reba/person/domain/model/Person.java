package com.reba.person.domain.model;

import com.reba.person.domain.model.enums.CountryEnum;
import com.reba.person.domain.model.enums.DocumentTypeEnum;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"documentType", "documentNumber", "country"}))
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private DocumentTypeEnum documentType;

    private String documentNumber;

    @Enumerated(EnumType.STRING)
    private CountryEnum country;

    @Nonnull
    @Temporal(TemporalType.DATE)
    private LocalDate birthDate;

    @Nonnull
    @ElementCollection
    private List<ContactData> contactData;

}
