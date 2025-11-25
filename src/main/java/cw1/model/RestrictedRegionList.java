package cw1.model;

import cw1.client.IlpClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RestrictedRegionList {
    private final IlpClient ilpClient;
    private List<RestrictedRegion> restrictedRegionList = new ArrayList<>();

    public RestrictedRegionList(IlpClient ilpClient){this.ilpClient = ilpClient;}

    public List<RestrictedRegion> getRestrictedRegionList() {
        return restrictedRegionList;
    }

    public void setRestrictedRegionList() {
        this.restrictedRegionList = ilpClient.fetchRestrictedRegions();
    }
}
