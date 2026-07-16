package com.kuafu.llm.loader;

import java.io.File;
import java.io.InputStream;

public interface Loader {

    String loader(String file);

    String loader(File file);

    String loader(InputStream inputStream);
}
