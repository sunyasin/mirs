package ru.parma.mirs.service.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="favorite_measure")
public class FavoriteMeasure {

    @Id
    @GeneratedValue
    @Column(name = "favorite_measure_id")
    private Integer favoriteMeasureId;

    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "measure_id")
    private Integer measureId;
    @Column(name = "begin_date")
    private LocalDate beginDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    public Integer getFavoriteMeasureId() {
        return favoriteMeasureId;
    }

    public void setFavoriteMeasureId(Integer favoriteMeasureId) {
        this.favoriteMeasureId = favoriteMeasureId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Integer measureId) {
        this.measureId = measureId;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
