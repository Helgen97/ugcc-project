package com.project.ugcc.repositories;

import com.project.ugcc.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface TypeRepository<T extends Type> extends JpaRepository<T, Long> {
}
