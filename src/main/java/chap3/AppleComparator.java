package chap3;

import java.util.Comparator;

import chap2.Apple;

public class AppleComparator implements Comparator<Apple>{

    @Override
    public int compare(Apple o1, Apple o2) {
        return o1.getWeight().compareTo(o2.getWeight());
    }
    
}
