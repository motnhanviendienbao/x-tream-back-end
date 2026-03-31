package com.example.xtream.dto.response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDTO
{
    /**
     * Data to be returned in the UI.
     **/
    public Object response;

    /**
     * List of errors generated while processing request.
     **/
    public Object error;

    /**
     * total items of pagination
     */
    public Long totalElements;

    /**
     * total page of pagination
     */
    public Integer totalPages;

    /**
     * current page of pagination
     */
    public Integer currentPage;
}

