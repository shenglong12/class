package com.kuafu.llm.embedding;

import com.alibaba.dashscope.embeddings.TextEmbedding;
import com.alibaba.dashscope.embeddings.TextEmbeddingParam;
import com.alibaba.dashscope.embeddings.TextEmbeddingResult;
import com.alibaba.dashscope.utils.Constants;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@ConditionalOnExpression("${llm.enable:false} and '${llm.embedding.provider}'.equalsIgnoreCase('ali')")
public class AliEmbedding implements Embedding {

    @Value("${llm.embedding.api_key}")
    private String apiKey;

    @SneakyThrows
    @Override
    public List<Double> embedding(String text) {
        Constants.apiKey = apiKey;
        TextEmbeddingParam param = TextEmbeddingParam
                .builder()
                .model(TextEmbedding.Models.TEXT_EMBEDDING_V1)
                .texts(Collections.singletonList(text)).build();
        TextEmbedding textEmbedding = new TextEmbedding();
        TextEmbeddingResult result = textEmbedding.call(param);
        return result.getOutput().getEmbeddings().get(0).getEmbedding();
    }
}
