package me.bteuk.progressmapperbackend.maphubapi.maphubobjects;

public class Geometry
{
    final GeometryType type;
    public double[][] coordinates;

    public Geometry(GeometryType type)
    {
        this.type = type;
        this.coordinates = new double[0][2];
    }

    public GeometryType getType()
    {
        return type;
    }
}

