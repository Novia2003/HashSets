package ru.vsu.cs.novichikhin;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegularHashMultiSetTest {
    TestCases cases = new TestCases();

    @Test
    void addNumberWithPositiveQuantityAdditions() {
        RegularHashMultiSet<Integer> set = new RegularHashMultiSet<>();
        set.add(1, 2);

        assertTrue(set.add(1, 5) && set.contains(1) && set.getQuantity(1) == 7);
    }

    @Test
    void addNumberWithNegativeQuantityAdditions() {
        RegularHashMultiSet<Integer> set = new RegularHashMultiSet<>();

        assertFalse(set.add(1, -1) || set.contains(1));
    }

    @Test
    void removeNumberWithPositiveQuantityAdditions() {
        RegularHashMultiSet<Integer> set = new RegularHashMultiSet<>();
        set.add(1, 40);

        assertTrue(set.remove(1, 4) && set.contains(1) && set.getQuantity(1) == 36);
    }

    @Test
    void removeNumberWithNegativeQuantityAdditions() {
        RegularHashMultiSet<Integer> set = new RegularHashMultiSet<>();
        set.add(1, 40);

        assertFalse(set.remove(1, -40));
        assertTrue(set.contains(1));
    }

    @Test
    void contains() {
        RegularHashMultiSet<Integer> set = new RegularHashMultiSet<>();
        set.add(1, 40);
        set.remove(1, 40);

        assertFalse(set.contains(1));
    }

    @Test
    void getQuantity() {
        RegularHashMultiSet<Integer> set = new RegularHashMultiSet<>();

        set.add(1, 40);
        set.remove(1, 90);

        assertEquals(0, set.getQuantity(1));
        assertEquals(0, set.getQuantity(2));
    }

    @Test
    void size() {
        RegularHashMultiSet<Integer> set = new RegularHashMultiSet<>();
        set.add(2, 12);
        set.add(2, 18);
        set.add(1, 1);

        assertEquals(2, set.size());

        set.remove(1, 1);

        assertEquals(1, set.size());
    }

    @Test
    void clear() {
        RegularHashMultiSet<Integer> set = new RegularHashMultiSet<>();
        set.add(2, 12);
        set.add(1, 1);

        set.clear();

        assertEquals(0, set.size());
    }

    @Test
    void isEmpty() {
        RegularHashMultiSet<Integer> set = new RegularHashMultiSet<>();
        set.add(0, 47);

        assertFalse(set.isEmpty());

        set.remove(0, 47);

        assertTrue(set.isEmpty());

        set.add(1, 1);
        set.clear();

        assertTrue(set.isEmpty());
    }

    @Test
    void iterator() {
        RegularHashMultiSet<Integer> set = cases.getMultiSet();

        Iterator<Integer> iterator = set.iterator();
        List<Integer> numbers = new ArrayList<>();

        while (iterator.hasNext()) {
            numbers.add(iterator.next());
        }

        boolean areAllNumbersFromSet = true;

        for (int number : numbers) {
            areAllNumbersFromSet &= set.remove(number, set.getQuantity(number));
        }

        assertTrue(set.isEmpty() && areAllNumbersFromSet);
    }

    @Test
    void testClone() {
        RegularHashMultiSet<Integer> set = cases.getMultiSet();

        RegularHashMultiSet<Integer> cloneSet = set.clone();

        Iterator<Integer> firstIterator = set.iterator();
        Iterator<Integer> secondIterator = cloneSet.iterator();

        boolean result = true;

        while (firstIterator.hasNext() || secondIterator.hasNext()) {
            int firstNumber = firstIterator.next();
            int secondNumber = secondIterator.next();

            result &= (firstNumber == secondNumber &&
                    set.getQuantity(firstNumber) == cloneSet.getQuantity(secondNumber));
        }

        assertTrue(result);
    }
}