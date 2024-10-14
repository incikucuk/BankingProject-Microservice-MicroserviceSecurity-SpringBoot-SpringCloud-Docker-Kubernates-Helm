package com.bankingSystem.Accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(
        name = "Response",
        description = "Schema to  hold Response information"
)
@Data
@AllArgsConstructor
public class ResponseDto {

    @Schema(
            description = "Status Code in the response"
    )
    private String statusCode;

    @Schema(
            description = "Status message in the response"
    )
    private String statusMessage;
}
