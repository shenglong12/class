package com.kuafu.llm.loader;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class TextLoader implements Loader {
    @Override
    public String loader(String file) {
        return FileUtil.readString(file, "UTF-8");
    }

    @Override
    public String loader(File file) {
        return FileUtil.readString(file, "UTF-8");
    }

    @Override
    public String loader(InputStream inputStream) {
        try {
            return IOUtils.toString(inputStream, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
