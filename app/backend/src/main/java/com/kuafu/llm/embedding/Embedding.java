package com.kuafu.llm.embedding;

import java.util.List;

public interface Embedding {

    List<Double> embedding(String text);
}
