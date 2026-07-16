package com.kuafu.common.login;

import java.io.Serializable;

public interface IControllerService<T> {

    T getById(Serializable id);
}
