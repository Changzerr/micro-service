package cn.changzer.dto;

import lombok.Data;

/**
 * @author lingqu
 * @date 2022/10/5
 * @apiNote
 */
@Data
public class OptLogDTO {
    private String requestIp; //操作IP
    private String type; //日志类型 LogType{OPT:操作类型;EX:异常类型}
    private String userName; //操作人
    private String description; //操作描述
}
