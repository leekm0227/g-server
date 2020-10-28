package com.leegm.api.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoJsonSchemaCreator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class DataUtil {

    @Autowired
    MongoTemplate mongoTemplate;

    private final HashMap<String, String> schemaMap = new HashMap<>();

    @PostConstruct
    public void init() {
        mongoTemplate.getConverter().getMappingContext().getPersistentEntities().forEach(entity -> {
            MongoJsonSchema schema = MongoJsonSchemaCreator.create(mongoTemplate.getConverter()).createSchemaFor(entity.getType());
            schemaMap.put(entity.getType().getSimpleName(), schema.toDocument().toJson());
        });
    }

    public String getSchema(String key) {
        return schemaMap.get(key);
    }
}
