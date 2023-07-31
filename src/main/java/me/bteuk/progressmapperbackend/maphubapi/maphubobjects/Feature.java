package me.bteuk.progressmapperbackend.maphubapi.maphubobjects;

public class Feature
{
    String type;
    long id;
    Geometry geometry;
    Properties properties;

    public Properties getProperties()
    {
        return properties;
    }

    public Geometry getGeometry()
    {
        return geometry;
    }
}
