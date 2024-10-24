package com.newhms.repository;

import com.newhms.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
  Optional<AppUser>findByUsername(String Username);
  Optional<AppUser>findByEmail(String Email);
}