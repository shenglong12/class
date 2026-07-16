package com.kuafu.llm.vector;

import com.aliyun.dashvector.DashVectorClient;
import com.aliyun.dashvector.DashVectorClientConfig;
import com.aliyun.dashvector.DashVectorCollection;
import com.aliyun.dashvector.models.Doc;
import com.aliyun.dashvector.models.DocOpResult;
import com.aliyun.dashvector.models.requests.CreateCollectionRequest;
import com.aliyun.dashvector.models.requests.InsertDocRequest;
import com.aliyun.dashvector.models.requests.QueryDocRequest;
import com.aliyun.dashvector.models.responses.Response;
import com.aliyun.dashvector.proto.FieldType;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@ConditionalOnExpression("${llm.enable:false} and '${llm.vector.provider}'.equalsIgnoreCase('ali')")
public class AliVector implements Vector, InitializingBean {

    @Value("${llm.vector.api_key}")
    private String apiKey;

    @Value("${llm.vector.end_point}")
    private String endPoint;

    private DashVectorClient client;


    private DashVectorClient getClient() {
        return this.client;
    }

    @Override
    public boolean createCollection(String name, Integer dimension) {

        CreateCollectionRequest request = CreateCollectionRequest.builder()
                .name(name)
                .dimension(dimension)
                .filedSchema("text", FieldType.STRING)
                .build();

        Response<Void> response = getClient().create(request);
        return response.isSuccess();
    }

    @Override
    public boolean deleteCollection(String name) {
        Response<Void> response = getClient().delete(name);
        return response.isSuccess();
    }

    @Override
    public Object getCollection(String name) {
        return getClient().get(name);
    }

    @Override
    public boolean save(String collectionName, String text, List<Double> vectors) {

        DashVectorCollection collection = getClient().get(collectionName);

        List<Float> trans = vectors.stream().map(Double::floatValue).collect(Collectors.toList());

        Doc doc = Doc.builder()
                .field("text", text)
                .vector(com.aliyun.dashvector.models.Vector.builder().value(trans).build())
                .build();

        Response<List<DocOpResult>> response = collection.insert(InsertDocRequest.builder().doc(doc).build());

        return response.isSuccess();
    }

    @Override
    public List<String> search(String collectionName, List<Double> vectors) {

        DashVectorCollection collection = getClient().get(collectionName);

        List<Float> trans = vectors.stream().map(Double::floatValue).collect(Collectors.toList());

        QueryDocRequest request = QueryDocRequest.builder()
                .vector(com.aliyun.dashvector.models.Vector.builder().value(trans).build())
                .topk(2)
                .build();

        Response<List<Doc>> response = collection.query(request);

        if (response.isSuccess()) {
            return response.getOutput().stream().map(doc -> doc.getFields().get("text").toString()).collect(Collectors.toList());
        } else {
            return Lists.newArrayList();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DashVectorClientConfig config = DashVectorClientConfig.builder()
                .apiKey(apiKey)
                .endpoint(endPoint)
                .timeout(10f)
                .build();
        this.client = new DashVectorClient(config);
    }
}
