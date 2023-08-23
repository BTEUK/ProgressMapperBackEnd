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

    public long getID()
    {
        return id;
    }

    public Feature(Geometry geometry, Properties properties)
    {
        this.type = "Feature";
        this.geometry = geometry;
        this.properties = properties;
    }

    public Feature(GeometryType geometryType, String szFill, String szTitle, String szDescription)
    {
        this.type = "Feature";
        this.geometry = new Geometry(geometryType);
        this.properties = new Properties(szFill, szTitle, szDescription);
    }
}
