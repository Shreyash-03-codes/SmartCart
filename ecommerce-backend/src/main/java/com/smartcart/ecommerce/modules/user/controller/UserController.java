package com.smartcart.ecommerce.modules.user.controller;

import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.modules.user.dtos.GetUserProfileDto;
import com.smartcart.ecommerce.modules.user.dtos.UpdateUserProfileDto;
import com.smartcart.ecommerce.modules.user.dtos.UpdateUserProfileImageDto;
import com.smartcart.ecommerce.modules.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<GetUserProfileDto> getUserProfile(){
        return ResponseEntity.ok(userService.getUser());
    }

    @PutMapping
    public ResponseEntity<GenericResponseDto> updateProfile(@RequestBody UpdateUserProfileDto updateUserProfileDto){
        return ResponseEntity.ok(userService.updateUserProfile(updateUserProfileDto));
    }

    @PutMapping("/profile-photo")
    public ResponseEntity<GenericResponseDto> updateUserProfilePhoto(@ModelAttribute UpdateUserProfileImageDto updateUserProfileImageDto){
        return ResponseEntity.ok(userService.updateUserProfilePhoto(updateUserProfileImageDto));
    }

}
