package com.cine8.controller;


import com.cine8.entity.Favourite;
import com.cine8.entity.User;
import com.cine8.repository.UserRepository;
import com.cine8.service.FavouriteService;
import com.cine8.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favourite")
@RequiredArgsConstructor
public class FavouriteController {

    private final FavouriteService favouriteService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> addFavourite(@RequestBody Integer idFilmApi) {

        // Necesitamos saber quien hace la peticion. Extrayendo su token.
        String authUser = SecurityContextHolder.getContext().getAuthentication().getName();

        // Buscamos la entidad completa y en la DB a quien corresponde el email.
        User user = userService.findByEmail(authUser);

        // Obtenemos el id del getter.
        Integer userId = user.getId();


        favouriteService.addFilm(idFilmApi, userId);
        return ResponseEntity.ok("Favourite added");
    }

    @DeleteMapping("/{idFilm}")
    public ResponseEntity<Void> deleteFavourite(@PathVariable Integer idFilm) {

        String authUser = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByEmail(authUser);

        favouriteService.deleteFilm(idFilm, user.getId());

        return ResponseEntity.noContent().build();


    }

    @GetMapping
    public ResponseEntity<List<Favourite>> findAll() {

        String authUser = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByEmail(authUser);
        Integer userId = user.getId();

        return ResponseEntity.ok(favouriteService.getAllFavourites(userId));
    }
}
