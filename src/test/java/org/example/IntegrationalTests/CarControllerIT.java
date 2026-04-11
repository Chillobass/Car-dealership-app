package org.example.IntegrationalTests;

import jakarta.transaction.Transactional;

import org.example.data.persistence.repositories.DetailRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CarControllerIT extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DetailRepository detailRepository;

//    @Test
//    void shouldCreateDetailViaApi() throws Exception {
//        // arrange
//        BaseReplaceableComponentTypeEntity detail = new KazahskiyDecorEntity();
//        detailRepository.save(detail);
//
//        String requestJson = """
//                {
//                    "price": "5",
//                    "category": "KazahskiyDecor"
//                }
//                """.formatted(detail.getId());
//
//        // 2. act & assert
//        mockMvc.perform(post("/api/v1/cars/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(status().isOk());
//    }

    // @Test
    // void shouldGetCarByIdViaApi() throws Exception {
    //     // arrange
    //     Brand brand = Brand.create("Toyota");
    //     brandRepository.add(brand);

    //     Car car = Car.create(new Price(30000.0), "Camry", brand.getId());
    //     carRepository.add(car);

    //     // act & assert
    //     mockMvc.perform(get("/api/v1/cars/" + car.getId())
    //             .accept(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.id").value(car.getId().toString()))
    //             .andExpect(jsonPath("$.model").value("Camry"))
    //             .andExpect(jsonPath("$.price").value(30000.0));
    // }

    @Test
    void shouldReturn404WhenCarNotFound() throws Exception {
        // arrange
        UUID fakeId = UUID.randomUUID();

        // act & assert
        mockMvc.perform(get("/api/v1/cars/" + fakeId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}