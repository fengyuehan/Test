package com.example.contentprovider.wavesidebarrecyclerview;

import com.example.contentprovider.sortlistview.SortModel;

import java.util.Comparator;

public class PinyinComparator implements Comparator<SortModelBean>{
    @Override
    public int compare(SortModelBean o1, SortModelBean o2) {
        if (o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
