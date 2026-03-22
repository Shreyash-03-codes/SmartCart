package com.smartcart.ecommerce.modules.user.services;

import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.modules.user.dtos.GetUserProfileDto;
import com.smartcart.ecommerce.modules.user.dtos.UpdateUserProfileDto;
import com.smartcart.ecommerce.modules.user.dtos.UpdateUserProfileImageDto;

public interface UserService {
    GetUserProfileDto getUser();

    GenericResponseDto updateUserProfile(UpdateUserProfileDto updateUserProfileDto);

    GenericResponseDto updateUserProfilePhoto(UpdateUserProfileImageDto updateUserProfileImageDto);
}
