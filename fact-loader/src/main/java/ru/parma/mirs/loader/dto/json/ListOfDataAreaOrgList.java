package ru.parma.mirs.loader.dto.json;

import java.util.List;

public class ListOfDataAreaOrgList {
    public List<DataAreaOrgList<GenericIndicatorsNew>> dataAreas;

    @Override
    public String toString() {
        return "ListOfDataAreaOrgList{" +
                "dataAreas=" + dataAreas +
                '}';
    }

    public String getSummary() {

        StringBuilder sb = new StringBuilder();
        if (dataAreas != null) {
            int total = 0;
            for (DataAreaOrgList<GenericIndicatorsNew> dataArea : dataAreas) {
                int count = dataArea.organizations == null ? 0 : dataArea.organizations.size();
                total += count;
                sb.append(count)
                    .append(" orgs in dataArea(")
                    .append(dataArea.id)
                    .append(")");
                if (dataArea.organizations != null) {
                    int positions = 0;
                    for (GenericIndicatorsNew org : dataArea.organizations) {
                        positions += org.positions == null ? 0 : org.positions.size() ;
                    }
                    sb.append(", positions="+positions);
                }
                sb.append(",\n");
            }
            sb.append("total: " + total);
        }
        return sb.toString();
    }

    public static class DataAreaOrgList<T> extends DataArea {
        public List<T> organizations;

        @Override
        public String toString() {
            return "DataAreaOrgList{" +
                    "id=" + id +
                    ", begin=" + begin +
                    ", end=" + end +
                    ", organizations=" + organizations +
                    '}';
        }
    }

}
