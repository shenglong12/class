package com.kuafu.llm.vector;

import java.util.List;

public interface Vector {

    /**
     * 创建集合
     *
     * @param name
     * @param dimension
     * @return
     */
    boolean createCollection(String name, Integer dimension);

    /**
     * 删除集合
     *
     * @param name
     * @return
     */
    boolean deleteCollection(String name);

    boolean save(String collectionName, String text, List<Double> vectors);

    /**
     * 搜索
     *
     * @param collectionName
     * @param vectors
     * @return
     */
    List<String> search(String collectionName, List<Double> vectors);

    /**
     * 获取 集合
     *
     * @param name
     * @return
     */
    Object getCollection(String name);
}
