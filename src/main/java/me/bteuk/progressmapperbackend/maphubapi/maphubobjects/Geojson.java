package me.bteuk.progressmapperbackend.maphubapi.maphubobjects;

import java.util.HashMap;

public class Geojson
{
    String type;
    Feature[] features;

    Group[] groups; //Each group has a title and id.

    private String[] markers;
    private HashMap properties;

    public Feature[] getFeatures()
    {
        return features;
    }

    public Group[] getGroups()
    {
        return groups;
    }

    //Used for append - but should it be? Would type and groups etc then show up as null. Don't we need a different one?
    public Geojson(Feature[] features)
    {
        this.type = "FeatureCollection";
        this.features = features;
    }

    public Geojson(Feature[] features, Group[] groups)
    {
        this.type = "FeatureCollection";
        this.features = features;
        this.groups = groups;
    }
}
