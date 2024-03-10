package com.reba.person.domain.model;

import com.reba.person.domain.model.enums.CountryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stat {
    private CountryEnum country;
    private float percentage;
}
