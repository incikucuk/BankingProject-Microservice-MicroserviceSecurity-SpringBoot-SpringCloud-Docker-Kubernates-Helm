package com.bankingSystem.Cards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

//@Schema(
//        name = "Response",
//        description = "Schema to hold successful response information"
//)
@Data
@AllArgsConstructor
public class ResponseDto {
    //    @Schema(
//            description = "Status code in the response"
//    )
    private String statusCode;

    //    @Schema(
//            description = "Status message in the response"
//    )
    private String statusMsg;
}
