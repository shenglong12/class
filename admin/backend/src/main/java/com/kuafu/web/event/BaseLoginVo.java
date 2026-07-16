package com.kuafu.web.event;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class BaseLoginVo   {
    @TableField(exist = false)
    private String defaultUserName;
    @TableField(exist = false)
    private String defaultPassword;
    @TableField(exist = false)
    private String defaultPhone;
}
