package com.reba.person.application;

import com.reba.person.application.service.PersonService;
import com.reba.person.domain.model.ContactData;
import com.reba.person.domain.model.Person;
import com.reba.person.domain.model.enums.ContactDataTypeEnum;
import com.reba.person.domain.model.enums.CountryEnum;
import com.reba.person.domain.model.enums.DocumentTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PersonControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    public void testGetAllPersons() throws Exception {
        List<Person> mockPersons = List.of(
                new Person(null, "John", DocumentTypeEnum.CI, "1234567890", CountryEnum.BRA, LocalDate.parse("2006-03-09"), List.of(new ContactData(ContactDataTypeEnum.PHONE, "+5491155443322"))),
                new Person(null, "Jane", DocumentTypeEnum.CI, "9876543210", CountryEnum.USA, LocalDate.parse("2003-04-10"), List.of(new ContactData(ContactDataTypeEnum.PHONE, "+14155556666")))
        );
        when(personService.getAllPersons()).thenReturn(mockPersons);

        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].documentNumber").value("1234567890"))
                .andExpect(jsonPath("$[1].name").value("Jane"))
                .andExpect(jsonPath("$[1].documentNumber").value("9876543210"));
    }

    @Test
    public void testGetPersonById() throws Exception {
        var mockPerson = new Person(UUID.fromString("2a752c82-6019-4c14-a24d-43aa17a5a18f"), "Jorge", DocumentTypeEnum.CI, "1234567890", CountryEnum.BRA, LocalDate.parse("2006-03-09"), List.of(new ContactData(ContactDataTypeEnum.PHONE, "+5491155443322")));
        when(personService.getPersonById(UUID.fromString("2a752c82-6019-4c14-a24d-43aa17a5a18f"))).thenReturn(mockPerson);

        mockMvc.perform(get("/persons/2a752c82-6019-4c14-a24d-43aa17a5a18f"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jorge"))
                .andExpect(jsonPath("$.documentNumber").value("1234567890"))
                .andExpect(jsonPath("$.country").value("BRA"))
                .andExpect(jsonPath("$.contactData[0].value").value("+5491155443322"));
    }

    @Test
    public void testGetPersonByIdNotFound() throws Exception {
        when(personService.getPersonById(UUID.fromString("2a752c82-6019-4c14-a24d-43aa17a5a18f"))).thenReturn(null);

        mockMvc.perform(get("/persons/2a752c82-6019-4c14-a24d-43aa17a5a18f"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreatePerson() throws Exception {
        var newPerson = new Person(null, "Alice", DocumentTypeEnum.CI, "1234567890", CountryEnum.BRA, LocalDate.parse("2006-03-09"), List.of(new ContactData(ContactDataTypeEnum.PHONE, "+5491155443322")));
        when(personService.createPerson(newPerson)).thenReturn(newPerson);

        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdatePerson() throws Exception {
        var updatedPerson = new Person(null, "Update Alice", null, null, null, LocalDate.parse("2000-03-09"), List.of(new ContactData(ContactDataTypeEnum.PHONE, "+5491155443322")));
        when(personService.updatePerson(UUID.fromString("2a752c82-6019-4c14-a24d-43aa17a5a18f"), updatedPerson)).thenReturn(updatedPerson);

        mockMvc.perform(put("/persons/2a752c82-6019-4c14-a24d-43aa17a5a18f")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Update Alice\",\"birthDate\":\"2000-03-09\", \"contactData\":[{\"type\":\"PHONE\",\"value\":\"+5491155443322\"}]}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Update Alice"))
                .andExpect(jsonPath("$.contactData[0].value").value("+5491155443322"));
    }

    @Test
    public void testPatchPerson() throws Exception {
        var updatedPerson = new Person(null, "Update Alice", null, null, null, LocalDate.parse("2000-03-09"), List.of(new ContactData(ContactDataTypeEnum.PHONE, "+5491155443322")));
        when(personService.patchPerson(UUID.fromString("2a752c82-6019-4c14-a24d-43aa17a5a18f"), updatedPerson)).thenReturn(updatedPerson);

        mockMvc.perform(patch("/persons/2a752c82-6019-4c14-a24d-43aa17a5a18f")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Update Alice\",\"birthDate\":\"2000-03-09\", \"contactData\":[{\"type\":\"PHONE\",\"value\":\"+5491155443322\"}]}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Update Alice"))
                .andExpect(jsonPath("$.contactData[0].value").value("+5491155443322"));
    }

    @Test
    public void testDeletePerson() throws Exception {
        personController.deletePerson(UUID.fromString("2a752c82-6019-4c14-a24d-43aa17a5a18f"));

        mockMvc.perform(get("/persons/2a752c82-6019-4c14-a24d-43aa17a5a18f"))
                .andExpect(status().isNotFound());
    }
}