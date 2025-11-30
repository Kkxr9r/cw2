package cw1.model;

import cw1.exception.InvalidDataException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RestrictedRegion {
    private Integer id;
    private String name;
    private List<Position> vertices;

    public Region toRegion() {
        return new Region(name, vertices);
    }

    private static void validateRestrictedRegion(RestrictedRegion restrictedRegion) throws InvalidDataException {
        if (restrictedRegion == null) throw new InvalidDataException("RestrictedRegion cannot be null");
        if (restrictedRegion.getId() == null) throw new InvalidDataException("RestrictedRegion.id cannot be null");
        Region region = restrictedRegion.toRegion();
        Region.validateRegion(region);
    }

    public static void validateRestrictedRegionList(RestrictedRegionList restrictedRegionList) throws InvalidDataException {
        if (restrictedRegionList == null) throw new InvalidDataException("RestrictedRegionList cannot be null");
        List<RestrictedRegion> restrictedRegions = restrictedRegionList.getRestrictedRegionList();
        for (RestrictedRegion r : restrictedRegions) validateRestrictedRegion(r);
    }
}
