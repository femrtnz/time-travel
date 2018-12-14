package co.uk.timetravel.controller;

import co.uk.timetravel.errors.ParadoxException;
import co.uk.timetravel.services.TravelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class TimeTravelControllerTests {

    private static final String PATH = "/time-travel-service";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TravelService travelService;

    @Test
    void given_url_when_postTravelDetails_then_shouldRespondSuccess() throws Exception {

        mockMvc.perform(post(PATH)
                .content("{ \"pgi\": \"A1234\", \"place\" : \"London\", \"date\" : \"2019-10-26\" }")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pgi").value("A1234"))
                .andExpect(jsonPath("$.place").value("London"))
                .andExpect(jsonPath("$.date").value("2019-10-26"));
    }


    @Test
    void given_pgiLessThan5_when_postTravelDetails_then_shouldRespondInvalidPgi() throws Exception {

        mockMvc.perform(post(PATH)
                .content("{ \"pgi\": \"A\", \"place\" : \"London\", \"date\" : \"2019-10-26\" }")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Validation Error"))
                .andExpect(jsonPath("$.errorMessage").value("Invalid Inputs"))
                .andExpect(jsonPath("$.errors[0]").value("Invalid PGI"));
    }

    @Test
    void given_pgiWith4Letters_when_postTravelDetails_then_shouldRespondInvalidPgi() throws Exception {

        mockMvc.perform(post(PATH)
                .content("{ \"pgi\": \"ABCD\", \"place\" : \"London\", \"date\" : \"2019-10-26\" }")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Validation Error"))
                .andExpect(jsonPath("$.errorMessage").value("Invalid Inputs"))
                .andExpect(jsonPath("$.errors[0]").value("Invalid PGI"));
    }

    @Test
    void given_pgiGraterThan10_when_postTravelDetails_then_shouldRespondInvalidPgi() throws Exception {

        mockMvc.perform(post(PATH)
                .content("{ \"pgi\": \"A1234567899\", \"place\" : \"London\", \"date\" : \"2019-10-26\" }")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Validation Error"))
                .andExpect(jsonPath("$.errorMessage").value("Invalid Inputs"))
                .andExpect(jsonPath("$.errors[0]").value("Invalid PGI"));
    }


    @Test
    void given_pgiStartingWithNumber_when_postTravelDetails_then_shouldRespondInvalidPgi() throws Exception {

        mockMvc.perform(post(PATH)
                .content("{ \"pgi\": \"1234567890\", \"place\" : \"London\", \"date\" : \"2019-10-26\" }")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Validation Error"))
                .andExpect(jsonPath("$.errorMessage").value("Invalid Inputs"))
                .andExpect(jsonPath("$.errors[0]").value("Invalid PGI"));
    }

    @Test
    void given_pgiWithInvalidChar_when_postTravelDetails_then_shouldRespondInvalidPgi() throws Exception {

        mockMvc.perform(post(PATH)
                .content("{ \"pgi\": \"123;\", \"place\" : \"London\", \"date\" : \"2019-10-26\" }")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Validation Error"))
                .andExpect(jsonPath("$.errorMessage").value("Invalid Inputs"))
                .andExpect(jsonPath("$.errors[0]").value("Invalid PGI"));
    }

    @Test
    void given_nullPgi_when_postTravelDetails_then_shouldRespondInvalidPgi() throws Exception {

        mockMvc.perform(post(PATH)
                .content("{ \"pgi\": null, \"place\" : \"London\", \"date\" : \"2019-10-26\" }")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Validation Error"))
                .andExpect(jsonPath("$.errorMessage").value("Invalid Inputs"))
                .andExpect(jsonPath("$.errors[0]").value("Invalid PGI"));
    }

    @Test
    void given_pgiMissing_when_postTravelDetails_then_shouldRespondInvalidPgi() throws Exception {

        mockMvc.perform(post(PATH)
                .content("{ \"place\" : \"London\", \"date\" : \"2019-10-26\" }")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Validation Error"))
                .andExpect(jsonPath("$.errorMessage").value("Invalid Inputs"))
                .andExpect(jsonPath("$.errors[0]").value("Invalid PGI"));
    }

    @Test
    void given_missingDate_when_postTravelDetails_then_shouldRespondInvalidPgi() throws Exception {

        mockMvc.perform(post(PATH)
                .content("{ \"pgi\": \"A123123\", \"place\" : \"London\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Validation Error"))
                .andExpect(jsonPath("$.errorMessage").value("Invalid Inputs"))
                .andExpect(jsonPath("$.errors[0]").value("Date must not be empty or null"));
    }

    @Test
    void given_dateInvalidFormat_when_postTravelDetails_then_shouldReturnError() throws Exception {
        doThrow(ParadoxException.class).when(travelService).save(any());

        mockMvc.perform(post(PATH)
                .content("{ \"pgi\": \"A1234\", \"place\" : \"London\", \"date\" : \"10-10-2019\" }")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Validation Error"))
                .andExpect(jsonPath("$.errorMessage").value("Invalid Date"))
                .andExpect(jsonPath("$.errors[0]").value("Invalid Date Format"));
    }

    @Test
    void given_invalidDate_when_postTravelDetails_then_shouldReturnError() throws Exception {
        doThrow(ParadoxException.class).when(travelService).save(any());

        mockMvc.perform(post(PATH)
                .content("{ \"pgi\": \"A1234\", \"place\" : \"London\", \"date\" : \"2019-10-2611\" }")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Validation Error"))
                .andExpect(jsonPath("$.errorMessage").value("Invalid Date"))
                .andExpect(jsonPath("$.errors[0]").value("Invalid Date Format"));
    }

    @Test
    void given_missingPlace_when_postTravelDetails_then_shouldRespondInvalidPgi() throws Exception {

        mockMvc.perform(post(PATH)
                .content("{ \"pgi\": \"A1234\", \"date\" : \"2019-10-26\" }")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Validation Error"))
                .andExpect(jsonPath("$.errorMessage").value("Invalid Inputs"))
                .andExpect(jsonPath("$.errors[0]").value("Place must not be empty or null"));
    }
    @Test
    void given_duplicatedPlace_when_postTravelDetails_then_shouldReturnParadoxError() throws Exception {
        doThrow(ParadoxException.class).when(travelService).save(any());

        mockMvc.perform(post(PATH)
                .content("{ \"pgi\": \"A1234\", \"place\" : \"London\", \"date\" : \"2019-10-26\" }")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Paradox Error"))
                .andExpect(jsonPath("$.errorMessage").value("Invalid Date"))
                .andExpect(jsonPath("$.errors[0]").value("choose another date, you have been here before in the same day"));
    }

}
