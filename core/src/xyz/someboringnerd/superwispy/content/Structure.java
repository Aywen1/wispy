package xyz.someboringnerd.superwispy.content;

import lombok.AccessLevel;
import lombok.Getter;

public class Structure
{
    @Getter(AccessLevel.PUBLIC)
    String name;

    public int[][] content;

    public Structure(String name)
    {
        this.name = name;
    }
}
