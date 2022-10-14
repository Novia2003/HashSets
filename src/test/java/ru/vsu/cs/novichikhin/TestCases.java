package ru.vsu.cs.novichikhin;

import java.util.Arrays;
import java.util.List;

public class TestCases {

    public RegularHashSet<Integer> getSet() {
        RegularHashSet<Integer> set = new RegularHashSet<>();
        List<Integer> additions = Arrays.asList(1, 2, 3, 2, 15, 23, 1, 2);

        for (int addition : additions) {
            set.add(addition);
        }

        return set;
    }

    public RegularHashMultiSet<Integer> getMultiSet() {
        RegularHashMultiSet<Integer> set = new RegularHashMultiSet<>();
        List<Integer> elements = Arrays.asList(1, 2, 3, 2, 15, 23, 1, 2);
        List<Integer> quantity = Arrays.asList(-3, 0, 1, 3, 5, 9, 6, 3);

        for (int i = 0; i < elements.size(); i++) {
            set.add(elements.get(i), quantity.get(i));
        }

        return set;
    }
}
