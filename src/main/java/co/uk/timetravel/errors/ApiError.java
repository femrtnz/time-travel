package co.uk.timetravel.errors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class ApiError {

    private String errorCode;
    private String errorMessage;
    private List<String> errors;

}
