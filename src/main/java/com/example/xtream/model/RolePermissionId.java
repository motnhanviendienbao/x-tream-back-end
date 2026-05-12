package com.example.xtream.model;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
public class RolePermissionId implements Serializable {
    private Long roleId;
    private Long permissionId;
}
