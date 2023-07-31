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
}
