package id.bimbel.utils;

import java.util.Comparator;

/**
 * Created by Comp on 9/18/2017.
 */

public class SortByValue implements Comparator {

    public int compare(Object o1, Object o2) {
        Double p1 = (Double) o1;
        Double p2 = (Double) o2;
        // return -1, 0, 1 to determine less than, equal to or greater than
//        return (p1.getNilai() > p2.getNilai() ? 1 : (p1.getNilai() == p2.getNilai() ? 0 : -1));
        // **or** the previous return statement can be simplified to:
        return Double.compare(p1, p2);
    }
}
