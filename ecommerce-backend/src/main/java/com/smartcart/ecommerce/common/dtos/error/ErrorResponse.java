package com.smartcart.ecommerce.common.dtos.error;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponse {
    private String status;
    private String description;
}
