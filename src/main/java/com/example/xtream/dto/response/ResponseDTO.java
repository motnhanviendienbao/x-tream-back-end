package com.example.xtream.dto.response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

/**
 * @JsonInclude(JsonInclude.Include.NON_NULL)
 * Declare for jackson knows just include NON_NULL fields in deserialize
 * @JsonIgnoreProperties(ignoreUnknown = true)
 * Declare for jackson knows do not care to serialize unknown field
 * <p>
 * This response DTO so:
 * All filed must set object type
 * if not, the filed will return default value of primitive[long,int] : 0
 * Not get filtered by Jackson setting no null
 */
@Builder
@Data
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

    /**
     * accessToken
     **/
    public Object accessToken;

    /**
     * refreshToken
     **/
    public Object refreshToken;
}

