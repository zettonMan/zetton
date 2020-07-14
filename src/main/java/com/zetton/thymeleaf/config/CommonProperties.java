package com.zetton.thymeleaf.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "common")
@Getter
@Setter
public class CommonProperties {
    /**
     * excel文件路径，文件名格式为“表名.csv”
     */
    private String csvDir;
    private String csvScore;
    private String csvCanton;
    private String csvExeOffice;
    private String csvApp;
    private String csvLog;
    /**
     * 文件位置类别 1:文件系统 2:类路径下面
     */
    private Integer location;

}
