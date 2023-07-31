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

    public Feature(Geometry geometry, Properties properties)
    {
        this.type = "Feature";
        this.geometry = geometry;
        this.properties = properties;
    }
}
