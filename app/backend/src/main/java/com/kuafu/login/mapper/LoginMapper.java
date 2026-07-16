package com.kuafu.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kuafu.login.domain.Login;
import org.apache.ibatis.annotations.Mapper;

/**
* @author www.macpe.cn
* @description 针对表【login】的数据库操作Mapper
* @createDate 2024-08-18 10:16:15
* @Entity com.kuafu.login.domain.Login
*/
@Mapper

public interface LoginMapper extends BaseMapper<Login> {

}




