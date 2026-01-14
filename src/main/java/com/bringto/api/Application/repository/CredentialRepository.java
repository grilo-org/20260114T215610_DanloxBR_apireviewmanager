package com.bringto.api.Application.config.repository;

import com.bringto.api.Application.config.model.Credential;
import com.bringto.api.Application.config.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CredentialRepository extends JpaRepository<Credential, Long> {
    List<Credential> findByOwner(User owner);
    Optional<Credential> findByIdAndOwner(Long id, User owner);
}