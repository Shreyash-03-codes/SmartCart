package com.smartcart.ecommerce.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public enum Roles {
    USER(Set.of(Permissions.CATEGORY_CREATE,Permissions.CATEGORY_UPDATE,Permissions.CATEGORY_VIEW,Permissions.CATEGORY_DELETE,Permissions.PRODUCT_CREATE, Permissions.PRODUCT_UPDATE,Permissions.PRODUCT_VIEW,Permissions.PRODUCT_DELETE,Permissions.ORDER_UPDATE,Permissions.ORDER_VIEW,Permissions.USER_MANAGE)),
    ADMIN(Set.of(Permissions.ORDER_CREATE,Permissions.ORDER_VIEW,Permissions.ORDER_CANCEL,Permissions.PRODUCT_VIEW,Permissions.CATEGORY_VIEW));

    private final Set<Permissions> permissions;
}
