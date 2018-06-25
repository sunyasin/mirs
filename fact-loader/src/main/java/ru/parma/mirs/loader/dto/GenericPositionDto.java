package ru.parma.mirs.loader.dto;

import java.util.Date;

public class GenericPositionDto {
    public Integer positionId;
    public Integer departmentId;
    public String inn;
    public float fullTimePositions;
    public int dataAreaId;
    public Date periodEnd;


    public GenericPositionDto( String inn, Date periodEnd ) {
        this.inn = inn;
        this.periodEnd = periodEnd;
    }

    public GenericPositionDto( String inn, Integer positionId, Integer departmentId, Integer fullTimePositions, int dataAreaId, Date periodEnd ) {

        this.inn = inn;
        this.positionId = positionId;
        this.departmentId = departmentId;
        this.fullTimePositions = fullTimePositions;
        this.dataAreaId = dataAreaId;
        this.periodEnd = periodEnd;
    }
}
