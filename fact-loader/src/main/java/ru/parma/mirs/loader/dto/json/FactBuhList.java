package ru.parma.mirs.loader.dto.json;

import java.util.List;

public class FactBuhList {
    public List<FactBuh> dataAreas;

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        if (dataAreas != null) {
            for (FactBuh dataArea : dataAreas) {
                sb.append(dataArea.orgList == null ? "0" : dataArea.orgList.size())
                        .append(" orgs in dataArea(")
                        .append(dataArea.id)
                        .append(")")
                        .append(",\n");
            }
        }
        return sb.toString();
    }
}
