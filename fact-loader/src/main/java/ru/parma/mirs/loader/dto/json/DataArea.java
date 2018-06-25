package ru.parma.mirs.loader.dto.json;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class DataArea {
    public int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public Date begin;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public Date end;

    @Override
    public String toString() {

        return "DataArea{" +
                "id=" + id +
                ", begin=" + begin +
                ", end=" + end +
                '}';
    }
}
