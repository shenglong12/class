package com.kuafu.llm.config;

import com.kuafu.llm.embedding.Embedding;
import com.kuafu.llm.vector.Vector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "llm", name = "enable", havingValue = "true")
public class LLMStartBusiness {

    @Value("${llm.vector.collection_name}")
    private String defaultCollectionName;

    @Autowired
    private Embedding embedding;

    @Autowired
    private Vector vector;

    public void init() {
        boolean del = vector.deleteCollection(defaultCollectionName);
        boolean create = vector.createCollection(defaultCollectionName, 1536);
        log.info("init ali {},{}", del, create);
    }

    public void createEmbedding(List<String> documents) {

        for (String text : documents) {

            List<Double> vectors = embedding.embedding(text);

            boolean flag = vector.save(defaultCollectionName, text, vectors);
            log.info("save vector : {}", flag);
        }
    }

    public List<String> search(String text) {
        List<Double> vectors = embedding.embedding(text);
        return vector.search(defaultCollectionName, vectors);
    }
}
