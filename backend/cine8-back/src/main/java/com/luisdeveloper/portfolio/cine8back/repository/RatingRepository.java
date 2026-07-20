package com.luisdeveloper.portfolio.cine8back.repository;

import com.luisdeveloper.portfolio.cine8back.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    Optional<Rating> findByIdFilmApi(Integer idFilmApi);

    Optional<Rating> findByIdUsers(Integer idUsers);

    // Para evitar duplicados, se puede buscar por idFilmApi y idUsers juntos.
    Optional<Rating> findByIdFilmApiAndIdUsers(Integer idFilmApi, Integer idUsers);
}