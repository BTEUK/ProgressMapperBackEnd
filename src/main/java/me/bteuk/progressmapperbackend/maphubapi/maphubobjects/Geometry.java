package me.bteuk.progressmapperbackend.maphubapi.maphubobjects;

public class Geometry
{
    final GeometryType type;
    public double[][] coordinates;

    public Geometry(GeometryType type)
    {
        this.type = type;
    }

    public GeometryType getType()
    {
        return type;
    }
}

