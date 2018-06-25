package ru.parma.mirs.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.parma.mirs.service.model.FavoriteMeasure;

import java.util.List;

@Repository
public interface FavoriteMeasureDao extends JpaRepository<FavoriteMeasure, Integer> {
    FavoriteMeasure findAllByFavoriteMeasureId(Integer favoriteMeasureId);
    List<FavoriteMeasure> findAllByUserId(Integer userId);
}
