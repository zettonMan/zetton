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
     * excel文件路径，文件名格式为“表名.xls”
     */
    private String xlsDir;
    private String xlsScore;
    private String xlsCanton;
    private String xlsExeOffice;
    private String xlsApp;
    private String xlsLog;
    /**
     * 文件位置类别 1:文件系统 2:类路径下面
     */
    private Integer location;

}
