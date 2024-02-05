package hr.carpazar.controllerTests;

import hr.carpazar.dtos.SpecificationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Year;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class SpecificationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAddListingSpecs(){

        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("user_id", "testUserId");
        httpSession.setAttribute("listing_id", "testListingId");

        SpecificationDto specificationDto = new SpecificationDto();
        specificationDto.setId("testListingId");
        specificationDto.setConsumption(4.3D);
        specificationDto.setBrand("TEST");
        specificationDto.setModel("TEST");
        specificationDto.setEnginePower("77");
        specificationDto.setPowerUnit("hp");
        specificationDto.setEngineType("diesel");
        specificationDto.setShifterType("manual");
        specificationDto.setKilometersTravelled(100);
        specificationDto.setManufactureYear(Year.parse("2001"));
        specificationDto.setInTrafficSince(Year.parse("2002"));
        specificationDto.setDoorCount(5);
        specificationDto.setGearCount(5);
        specificationDto.setLocation("Splitsko-makarska");
        specificationDto.setBodyShape("coupe");
        specificationDto.setIsUsed(Boolean.TRUE);
        specificationDto.setDriveType("rwd");
        specificationDto.setAcType("automatic");
        specificationDto.setSeatCount(5);
        specificationDto.setRegistrationUntil(LocalDate.parse("2024-01-01"));
        specificationDto.setOwnerNo(3);
        specificationDto.setColor("Yellow");
        specificationDto.setAdditionalEquipment(0);
        specificationDto.setExtraFeatures(0);


        try {
            mockMvc.perform(post("/add-info")
                            .session(httpSession)
                            .flashAttr("specificationDto", specificationDto))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/listingWithImages/testListingId"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
