package com.leegm.api.domain;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
public class Account extends AbstractDomain {

    @Indexed(unique = true)
    String platformId;

    int type;
}
