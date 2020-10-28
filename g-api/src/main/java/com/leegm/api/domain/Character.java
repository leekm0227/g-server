package com.leegm.api.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
public class Character extends AbstractDomain {

    @Indexed(unique = true)
    String nickname;

    @DBRef
    Account Account;

    int level = 1;
    int exp = 0;
    float[] pos = new float[]{0, 0, 0};

    public float getPos(int pos) {
        return this.pos[pos];
    }

    public void setPos(int pos, int value) {
        this.pos[pos] = value;
    }
}
