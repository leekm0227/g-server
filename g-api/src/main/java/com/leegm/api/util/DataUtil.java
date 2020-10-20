package com.leegm.api.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoJsonSchemaCreator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class DataUtil {

    @Autowired
    MongoTemplate mongoTemplate;

    private static final String DOMAIN_PACKAGE = "com.leegm.api.domain.";
    private final HashMap<String, String> schemaMap = new HashMap<>();

    @PostConstruct
    public void init() {
        mongoTemplate.getCollectionNames().forEach(collectionName -> {
            try {
                String className = DOMAIN_PACKAGE + StringUtils.capitalize(collectionName);
                MongoJsonSchema schema = MongoJsonSchemaCreator.create(mongoTemplate.getConverter()).createSchemaFor(Class.forName(className));
                schemaMap.put(collectionName, schema.toDocument().toJson());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public String getSchema(String key){
        return schemaMap.get(key);
    }
}
