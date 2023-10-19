package xyz.someboringnerd.superwispy.content.items;

import lombok.AccessLevel;
import lombok.Getter;

public class Item
{
    @Getter(AccessLevel.PUBLIC)
    private String defaultName;

    @Getter(AccessLevel.PUBLIC)
    private int ID;
}
