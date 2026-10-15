package com.furkan.gameapi.mapper;

import com.furkan.gameapi.dto.AppUserCreateRequest;
import com.furkan.gameapi.dto.AppUserResponse;
import com.furkan.gameapi.model.AppUser;

public class AppUserMapper {
    public static AppUserResponse toAppUserResponse(AppUser appUser){
        return new AppUserResponse(
                appUser.getId(),
                appUser.getUsername(),
                appUser.getRole()
        );
    }
}
