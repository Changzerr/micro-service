package com.changzer.dto;


import lombok.Data;

/**
 * @author lingqu
 * @date 2022/9/30
 * @apiNote
 */

@Data
public class UserDTO {
    private String userId;
    private String userName;
    private int userAge;
    private String address;
    private String birthday;

}
