package com.kuafu.web.event;

import java.util.Arrays;
import java.util.List;

public class EventConstant {
    public static final List<LoginTableVo> loginTableVoList = Arrays.asList(
                new LoginTableVo("userInfo", "userInfoId", "defaultUserName", "password")
    );
}
