package com.newhms.service;

import com.newhms.entity.AppUser;
import com.newhms.exception.UserAlreadyExistsException;
import com.newhms.payload.AppUserDto;
import com.newhms.payload.LoginDto;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.newhms.repository.AppUserRepository;

import java.util.Optional;

@Service
public class UserService {

   private  AppUserRepository appUserRepository;
   private ModelMapper modelMapper;
   private JWTService jwtService;

    public UserService(AppUserRepository appUserRepository, ModelMapper modelMapper, JWTService jwtService) {
        this.appUserRepository = appUserRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }
    public AppUserDto createUserDetails(AppUserDto appUserDto) {
        AppUser appUser = mapToEntity(appUserDto);

        Optional<AppUser> opUsername = appUserRepository
                .findByUsername(appUser.getUsername());
        if (opUsername.isPresent()){
            throw new UserAlreadyExistsException("Username already exists");
        }
        Optional<AppUser> opEmail = appUserRepository.findByEmail(appUser.getEmail());
        if (opEmail.isPresent()){
            throw new UserAlreadyExistsException("Email has already exist");
        }
        String en = BCrypt.hashpw(appUser.getPassword(), BCrypt.gensalt(5));
        appUser.setPassword(en);
        appUser.setRole("ROLE_USER");
        AppUser saved = appUserRepository.save(appUser);
        AppUserDto appUserDto1 = mapTODto(saved);
        return appUserDto1;
    }
    public AppUserDto createPropertyOwnerUserDetails(AppUserDto appUserDto) {
        AppUser appUser = mapToEntity(appUserDto);

        Optional<AppUser> opUsername = appUserRepository
                .findByUsername(appUser.getUsername());
        if (opUsername.isPresent()){
            throw new UserAlreadyExistsException("Username already exists");
        }
        Optional<AppUser> opEmail = appUserRepository.findByEmail(appUser.getEmail());
        if (opEmail.isPresent()){
            throw new UserAlreadyExistsException("Email has already exist");
        }
        String en = BCrypt.hashpw(appUser.getPassword(), BCrypt.gensalt(5));
        appUser.setPassword(en);
        appUser.setRole("ROLE_OWNER");
        AppUser saved = appUserRepository.save(appUser);
        AppUserDto appUserDto1 = mapTODto(saved);
        return appUserDto1;
    }
    AppUser mapToEntity(AppUserDto appUserDto){

        return  modelMapper.map(appUserDto, AppUser.class);
    }
    AppUserDto mapTODto(AppUser appUser){
        return modelMapper.map(appUser, AppUserDto.class);
    }
    public String verifyLogin(LoginDto loginDto) {
        Optional<AppUser> opUser = appUserRepository.findByUsername(loginDto.getUsername());
        if (opUser.isPresent()){
            AppUser appUser = opUser.get();
            if(BCrypt.checkpw(loginDto.getPassword(),appUser.getPassword())){
                String token = jwtService.generateToken(appUser.getUsername());
                return token;
            }
        }else {
            return null;
        }
        return null;
    }
}
