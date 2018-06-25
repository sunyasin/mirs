package ru.parma.mirs.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.parma.mirs.service.dao.FavoriteMeasureDao;
import ru.parma.mirs.service.model.FavoriteMeasure;

import java.net.URI;
import java.util.List;
@RestController
@RequestMapping(value = "/indicator/favorite")
public class FavoriteMeasuresController {

    @Autowired public FavoriteMeasureDao favoriteMeasureDao;

    @GetMapping("/{user_id}")
    public List<FavoriteMeasure> findAll(@PathVariable(value = "user_id") Integer userId) {
        return favoriteMeasureDao.findAllByUserId(userId);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createFavorite(@RequestBody FavoriteMeasure favoriteMeasure) {
        FavoriteMeasure saveFavoriteMeasure = favoriteMeasureDao.save(favoriteMeasure);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(saveFavoriteMeasure.getFavoriteMeasureId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/{favorite_measure_id}")
    public ResponseEntity<Object> updateFavorite(@RequestBody FavoriteMeasure favoriteMeasure, @PathVariable(value = "favorite_measure_id") Integer favoriteMeasureId) {

        FavoriteMeasure favoriteMeasureOptional = favoriteMeasureDao.findAllByFavoriteMeasureId(favoriteMeasureId);

        if (favoriteMeasureOptional == null)
            return ResponseEntity.notFound().build();

        favoriteMeasure.setFavoriteMeasureId(favoriteMeasureId);
        favoriteMeasureDao.save(favoriteMeasure);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{favorite_measure_id}")
    public void deleteFavorite(@PathVariable(value = "favorite_measure_id") Integer favoriteMeasureId) {
        favoriteMeasureDao.delete(favoriteMeasureDao.findAllByFavoriteMeasureId(favoriteMeasureId));
    }
}
