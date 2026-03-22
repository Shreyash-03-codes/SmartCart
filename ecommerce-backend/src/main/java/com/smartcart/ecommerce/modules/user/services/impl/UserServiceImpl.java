package com.smartcart.ecommerce.modules.user.services.impl;

import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.common.exceptions.UserEmailNotFoundException;
import com.smartcart.ecommerce.common.services.FileService;
import com.smartcart.ecommerce.common.services.GetUserService;
import com.smartcart.ecommerce.modules.user.dtos.GetUserProfileDto;
import com.smartcart.ecommerce.modules.user.dtos.UpdateUserProfileDto;
import com.smartcart.ecommerce.modules.user.dtos.UpdateUserProfileImageDto;
import com.smartcart.ecommerce.modules.user.model.User;
import com.smartcart.ecommerce.modules.user.repository.UserRepository;
import com.smartcart.ecommerce.modules.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final GetUserService getUserService;

    private final FileService fileService;

    @Override
    public GetUserProfileDto getUser() {
        User user=getUserService.getUser();
        return GetUserProfileDto
                .builder()
                .email(user.getEmail())
                .name(user.getName())
                .profilePhotoPath(user.getProfilePhotoPath())
                .build();
    }

    @Override
    @Transactional
    public GenericResponseDto updateUserProfile(UpdateUserProfileDto updateUserProfileDto) {

        User user=getUserService.getUser();
        User userForUpdate=userRepository.findByEmail(user.getEmail()).orElseThrow(()->new UserEmailNotFoundException("User not found."));
        userForUpdate.setName(updateUserProfileDto.getName());
        userRepository.save(userForUpdate);
        return new GenericResponseDto("User profile updated successfully.");
    }

    @Override
    @Transactional
    public GenericResponseDto updateUserProfilePhoto(UpdateUserProfileImageDto updateUserProfileImageDto) {
        User user=getUserService.getUser();
        User userForUpdate=userRepository.findByEmail(user.getEmail()).orElseThrow(()->new UserEmailNotFoundException("User not found."));
        String imagePath=fileService.storeImage(updateUserProfileImageDto.getImage());
        userForUpdate.setProfilePhotoPath(imagePath);
        return new GenericResponseDto("User profile updated successfully.");
    }
}
