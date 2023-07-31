package me.bteuk.progressmapperbackend.maphubapi.maphubobjects;

public class Properties
{
    public String fill;
    public String stroke;
    public String media_url;
    private long group;
    public String title;
    public String description;

    private Image image;

    public void appendTestTextToDescription()
    {
        if (this.description == null)
        {
            this.description = "Plus the edit from the program !";
        }
        else
            this.description = description +" plus the edit from the program !";
    }
    public void appendTestTextToTitle()
    {
        if (this.title == null)
        {
            this.title = "S !";
        }
        else
            this.title = title +" plus the edit from the program !";
    }

    public Properties(long group, String fill, String title, String description)
    {
        this.group = group;
        this.fill = fill;
        this.title = title;
        this.description = description;
    }

    public Properties(String fill, String title, String description)
    {
        this.group = 0;
        this.fill = fill;
        this.title = title;
        this.description = description;
    }
}
