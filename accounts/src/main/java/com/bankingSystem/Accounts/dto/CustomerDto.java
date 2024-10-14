package com.bankingSystem.Accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerDto {

    @Schema(
            name = "Customers",
            description = "Schema to hold Customer and Account information"
    )
    @NotEmpty(message = "Name cannot be a null or empty!")
    @Size(min = 4, max = 30, message = "Name must be between 4 and 30 characters!")
    private String name;

    @Schema(
            description = "Email address of the customer", example = "bankingsystems@gmail.com"
    )
    @NotEmpty(message = "Email cannot be a null or empty!")
    @Email(message = "Email should be a valid email address!")
    private String email;

    @Schema(
            description = "mobileNumber of the customer", example = "4444333321"
    )
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number should be 10 digits!")
    private String mobileNumber;

    @Schema(
            description = "account Details of the Customer"
    )
    private AccountDto accountDto;
}

