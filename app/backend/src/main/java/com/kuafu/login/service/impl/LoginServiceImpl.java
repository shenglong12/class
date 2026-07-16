package com.kuafu.login.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuafu.login.domain.Login;
import com.kuafu.login.mapper.LoginMapper;
import com.kuafu.login.service.ILoginService;
import org.springframework.stereotype.Service;

/**
* @author www.macpe.cn
* @description 针对表【login】的数据库操作Service实现
* @createDate 2024-08-18 10:16:15
*/
@Service("Login")
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login>
    implements ILoginService {

}




