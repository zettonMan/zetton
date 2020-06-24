package com.zetton.thymeleaf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Api("学生实体对象")
@Data
@TableName("t_student")
@Builder
public class Student {

    @ApiModelProperty("学生id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("学生姓名")
    private String name;

    @ApiModelProperty("学生年龄")
    private Integer age;

    @ApiModelProperty("学生班级")
    @TableField(value="class_name")
    private String className;

}