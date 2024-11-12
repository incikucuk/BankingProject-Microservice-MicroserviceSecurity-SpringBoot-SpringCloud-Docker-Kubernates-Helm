package com.bankingSystem.Cards.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "cards")
@Getter
@Setter
public class CardsContactInfoDto {

    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;

}
