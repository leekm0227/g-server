package com.leegm.api.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
abstract class AbstractDomain implements Serializable {

    protected String id;

    @CreatedDate
    protected LocalDateTime createAt;

    @LastModifiedDate
    protected LocalDateTime modifyAt;
}
