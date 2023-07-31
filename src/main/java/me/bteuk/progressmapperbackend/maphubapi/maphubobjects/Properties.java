package me.bteuk.progressmapperbackend.maphubapi.maphubobjects;

public class Properties
{
    public String fill;
    private long group;
    public String title;
    public String description;

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
}
