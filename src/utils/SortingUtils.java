package utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortingUtils {

    public static <T> List<T> sortedCopy(List<T> items, Comparator<T> comparator) {
        List<T> copy = new ArrayList<>(items);
        copy.sort(comparator);
        return copy;
    }
}
