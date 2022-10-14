package ru.vsu.cs.novichikhin;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegularHashSetTest {
    TestCases cases = new TestCases();

    @Test
    void addOneNumber() {
        RegularHashSet<Integer> set = new RegularHashSet<>();

        assertTrue(set.add(2) && set.contains(2));
    }

    @Test
    void addOneNumberMultiplyTimes() {
        RegularHashSet<Integer> set = new RegularHashSet<>();
        boolean isSuccessfullyAdded = set.add(1);

        for (int i = 0; i < 2; i++) {
            isSuccessfullyAdded &= set.add(1);
        }

        assertFalse(isSuccessfullyAdded);
        assertTrue(set.contains(1));
    }

    @Test
    void removeOneNumber() {
        RegularHashSet<Integer> set = new RegularHashSet<>();
        set.add(1);

        assertTrue(set.remove(1));
        assertFalse(set.contains(1));
    }

    @Test
    void removeOneNumberMultiplyTimes() {
        RegularHashSet<Integer> set = new RegularHashSet<>();
        set.add(1);

        boolean isSuccessfullyDeleted = set.remove(1);

        for (int i = 0; i < 2; i++) {
            isSuccessfullyDeleted &= set.remove(1);
        }

        assertFalse(isSuccessfullyDeleted);
        assertFalse(set.contains(1));
    }

    @Test
    void contains() {
        RegularHashSet<Integer> set = new RegularHashSet<>();
        set.add(1);

        assertFalse(set.contains(2));
    }

    @Test
    void size() {
        RegularHashSet<Integer> set = new RegularHashSet<>();
        set.add(2);
        set.add(2);
        set.add(1);

        assertEquals(2, set.size());

        set.remove(2);

        assertEquals(1, set.size());
    }

    @Test
    void clear() {
        RegularHashSet<Integer> set = new RegularHashSet<>();
        set.add(1);
        set.add(8);

        set.clear();

        assertEquals(0, set.size());
    }

    @Test
    void isEmpty() {
        RegularHashSet<Integer> set = new RegularHashSet<>();
        set.add(1);

        assertFalse(set.isEmpty());

        set.remove(1);

        assertTrue(set.isEmpty());

        set.add(2);
        set.clear();

        assertTrue(set.isEmpty());
    }

    @Test
    void iterator() {
        RegularHashSet<Integer> set = cases.getSet();

        Iterator<Integer> iterator = set.iterator();
        List<Integer> numbers = new ArrayList<>();

        while (iterator.hasNext()) {
            numbers.add(iterator.next());
        }

        boolean areAllNumbersFromSet = true;

        for (int number : numbers) {
            areAllNumbersFromSet &= set.remove(number);
        }

        assertTrue(set.isEmpty() && areAllNumbersFromSet);
    }

    @Test
    void testClone() {
        RegularHashSet<Integer> set = cases.getSet();

        RegularHashSet<Integer> cloneSet = set.clone();

        Iterator<Integer> firstIterator = set.iterator();
        Iterator<Integer> secondIterator = cloneSet.iterator();

        boolean result = true;

        while (firstIterator.hasNext() || secondIterator.hasNext()) {
            result &= firstIterator.next().equals(secondIterator.next());
        }

        assertTrue(result);
    }
}