package com.example.viewpager;

import com.flyco.tablayout.listener.CustomTabEntity;

public class TabEntity  implements CustomTabEntity {
    private String name;
    private int selectIcon;
    private int unSelectIcon;

    public TabEntity(String name, int selectIcon, int unSelectIcon) {
        this.name = name;
        this.selectIcon = selectIcon;
        this.unSelectIcon = unSelectIcon;
    }

    @Override
    public String getTabTitle() {
        return name;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectIcon;
    }
}
