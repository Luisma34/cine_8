package com.luisdeveloper.portfolio.cine8back.repository;

import com.luisdeveloper.portfolio.cine8back.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {

    Optional<Favourite> findByIdUsers(Integer idUsers);

    Optional<Favourite> findByIdFilmApi(Integer idFilmApi);

    // Para evitar duplicados, se puede buscar por idFilmApi y idUsers juntos.
    Optional<Favourite> findByIdFilmApiAndIdUsers(Integer idFilmApi, Integer idUsers);

}
