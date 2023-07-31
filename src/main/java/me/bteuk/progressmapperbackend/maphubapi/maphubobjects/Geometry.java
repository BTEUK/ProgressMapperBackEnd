package me.bteuk.progressmapperbackend.maphubapi.maphubobjects;

public class Geometry
{
    GeometryType type;
    public double[][] coordinates;

    public Geometry(GeometryType type)
    {
        this.type = type;
    }
}

