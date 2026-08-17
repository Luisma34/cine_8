package com.cine8.service;

import com.cine8.entity.Favourite;
import com.cine8.entity.User;
import com.cine8.repository.FavouriteRepository;
import com.cine8.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final UserRepository userRepository;

    public Favourite addFilm(Integer idFilmApi, Integer idUser) {

        if (favouriteRepository.findByIdFilmApiAndIdUsers(idFilmApi, idUser).isPresent()) {
            throw new RuntimeException("Pelicula ya añadida");
        }

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        Favourite favourite = new Favourite(null, idFilmApi, user);

        return favouriteRepository.save(favourite);
    }

    public void deleteFilm(Integer idFilmApi, Integer idUser) {

        Favourite filmDelete = favouriteRepository.findByIdFilmApiAndIdUsers(idFilmApi, idUser)
                .orElseThrow(() -> new RuntimeException("Pelicula no encontrada"));

        favouriteRepository.delete(filmDelete);

    }

}
