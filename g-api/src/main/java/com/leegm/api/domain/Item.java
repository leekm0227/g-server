package com.leegm.api.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
public class Item extends AbstractDomain {

    int templateId;
    long amount;
}