package com.bankingSystem.Accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountDto {

    @Schema(
            description = "AccountNumber of the Accounts"
    )
    @NotEmpty(message = "Account number cannot be a null or empty!")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Account number should be 10 digits!")
    private Long accountNumber;

    @Schema(
            description = "Account Type of the Account", example = "Savings"
    )
    @NotEmpty(message = "Account type cannot be a null or empty!")
    private String accountType;

    @Schema(
            description = "Branch Address of the customer"
    )
    @NotEmpty(message = "Branch address cannot be a null or empty!")
    private String branchAddress;

}

