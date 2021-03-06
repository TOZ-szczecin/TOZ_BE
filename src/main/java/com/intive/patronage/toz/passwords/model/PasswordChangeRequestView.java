package com.intive.patronage.toz.passwords.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@ApiModel("Password change request body")
@Getter
@Setter
@NoArgsConstructor
public class PasswordChangeRequestView extends PasswordRequestViewBase{

    @ApiModelProperty(value = "Old password", required = true, position = 1)
    @NotEmpty
    private String oldPassword;

    public PasswordChangeRequestView(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
