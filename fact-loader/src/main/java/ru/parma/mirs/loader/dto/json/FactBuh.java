package ru.parma.mirs.loader.dto.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FactBuh extends DataArea {

    @JsonProperty("organizations")
    public List<Organization> orgList;

    public static class Organization {
        public String inn;
        public int totalOperations;
        public int manualOperations;
        public int correctiveOperations;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        public Date lastOperationDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        public Date closingDate;//"1979-12-31T12:00:00"
        public BigDecimal accountBalance;

        @Override
        public String toString() {

            return "Organization{" +
                    "inn='" + inn + '\'' +
                    ", totalOperations=" + totalOperations +
                    ", manualOperations=" + manualOperations +
                    ", correctiveOperations=" + correctiveOperations +
                    ", lastOperationDate=" + lastOperationDate +
                    ", closingDate=" + closingDate +
                    ", accountBalance=" + accountBalance +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FactBuh{" +
                "id=" + id +
                ", begin=" + begin +
                ", end=" + end +
                ", orgList=" + orgList +
                '}';
    }
}
