package ru.kuznetsov.shop.address.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import ru.kuznetsov.shop.represent.dto.AddressDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@Sql(value = {"/sql/init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(value = {"/sql/clean_up.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class AddressControllerTest {

    private final static String API_PATH = "/address";
    private static final String SCHEME = "address";

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper om;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Order(1)
    void createAndReturn200() throws Exception {
        AddressDto mockDto = getMockDto();

        MvcResult mvcResult = sendRequest(HttpMethod.POST, API_PATH, mockDto)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        AddressDto foundItem = om.readValue(json, AddressDto.class);

        assertNotNull(foundItem.getId());
        assertNotNull(foundItem.getCreated());
        assertNotNull(foundItem.getUpdated());
        assertEquals(mockDto.getCity(), foundItem.getCity());
        assertEquals(mockDto.getStreet(), foundItem.getStreet());
        assertEquals(mockDto.getHouse(), foundItem.getHouse());
    }

    @Test
    @Order(2)
    void createBatchAndReturn200() throws Exception {
        List<AddressDto> dtos = new ArrayList<>();
        dtos.add(getMockDto());
        dtos.add(getMockDto());
        dtos.add(getMockDto());
        dtos.add(getMockDto());

        MvcResult mvcResult = sendRequest(HttpMethod.POST, API_PATH + "/batch", dtos)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        AddressDto[] foundItems = om.readValue(json, AddressDto[].class);

        for (AddressDto foundItem : foundItems) {
            assertNotNull(foundItem.getId());
            assertNotNull(foundItem.getCreated());
            assertNotNull(foundItem.getUpdated());
        }
    }

    @Test
    void getById() throws Exception {
        int id = 1;

        MvcResult mvcResult = sendRequest(HttpMethod.GET, API_PATH + "/" + id, null)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        AddressDto foundItem = om.readValue(json, AddressDto.class);

        assertEquals(id, foundItem.getId());
    }

    @Test
    void getAll() throws Exception {
        int count = getItemCount();

        MvcResult mvcResult = sendRequest(HttpMethod.GET, API_PATH, null)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        AddressDto[] foundItem = om.readValue(json, AddressDto[].class);

        assertEquals(count, foundItem.length);
    }

    @Test
    void delete() throws Exception {
        AddressDto mockDto = getMockDto();

        MvcResult mvcResult = sendRequest(HttpMethod.POST, API_PATH, mockDto)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        Long id = om.readValue(json, AddressDto.class).getId();

        sendRequest(HttpMethod.DELETE, API_PATH + "/" + id, null);

        assertThrows(Exception.class, () -> sendRequest(HttpMethod.GET, API_PATH + "/" + id, null));
    }

    private AddressDto getMockDto() {
        AddressDto dto = new AddressDto();
        dto.setCity("Test");
        dto.setStreet("Test");
        dto.setHouse("123");

        return dto;
    }

    private ResultActions sendRequest(HttpMethod httpMethod, String apiPath, Object body) throws Exception {
        return mockMvc.perform(request(httpMethod, apiPath)
                .content(om.writeValueAsString(body))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));
    }

    protected Integer getItemCount() {
        Integer deviceCount;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            deviceCount = (Integer) entityManager
                    .createNativeQuery("select count(*) from " + SCHEME, Integer.class)
                    .getSingleResult();
        } catch (Exception e) {
            deviceCount = 0;
        }
        return deviceCount;
    }
}