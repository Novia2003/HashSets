package ru.vsu.cs.novichikhin;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

public class RegularHashMultiSet<E> {
    private class EntrySetItem {
        public E element;
        public int quantity;
        public EntrySetItem next;

        public EntrySetItem(E element, int quantity, EntrySetItem next) {
            this.element = element;
            this.quantity = quantity;
            this.next = next;
        }
    }

    private EntrySetItem[] table;
    private int size = 0;

    public RegularHashMultiSet() {
        table = (EntrySetItem[]) Array.newInstance(EntrySetItem.class, 100);
    }

    public RegularHashMultiSet(int capacity) {
        if (capacity < 100) {
            capacity = 100;
        }
        table = (EntrySetItem[]) Array.newInstance(EntrySetItem.class, capacity);
    }

    private int getIndex(E key) {
        int index = key.hashCode() % table.length;

        if (index < 0) {
            index += table.length;
        }

        return index;
    }

    public boolean add(E element, int quantityAdditions) {
        if (quantityAdditions < 1) {
            return false;
        }

        int index = getIndex(element);

        for (EntrySetItem current = table[index]; current != null; current = current.next) {
            if (element.equals(current.element)) {
                current.quantity += quantityAdditions;
                return true;
            }
        }

        table[index] = new EntrySetItem(element, quantityAdditions, table[index]);
        size++;

        return true;
    }

    public boolean contains(E element) {
        int index = getIndex(element);

        for (EntrySetItem current = table[index]; current != null; current = current.next) {
            if (element.equals(current.element)) {
                return true;
            }
        }

        return false;
    }

    public boolean remove(E element, int quantityDeletions) {
        if (contains(element) && quantityDeletions > 0) {
            int index = getIndex(element);
            EntrySetItem parent = null;

            for (EntrySetItem current = table[index]; current != null; current = current.next) {
                if (element.equals(current.element)) {

                    if (current.quantity > quantityDeletions) {
                        current.quantity -= quantityDeletions;
                    } else {

                        if (parent == null) {
                            table[index] = current.next;
                        } else {
                            parent.next = current.next;
                        }
                        size--;
                    }
                    return true;
                }
                parent = current;
            }
        }

        return false;
    }

    public int getQuantity(E element) {
        int index = getIndex(element);

        for (EntrySetItem current = table[index]; current != null; current = current.next) {
            if (element.equals(current.element)) {
                return current.quantity;
            }
        }

        return 0;
    }

    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    public Iterator<E> iterator() {
        return new Iterator<>() {

            int tableIndex = -1;
            EntrySetItem current = null;

            {
                findIndex();
            }

            private void findIndex() {
                if (tableIndex >= table.length) {
                    return;
                }

                if (current != null) {
                    current = current.next;
                }

                if (current == null) {
                    for (tableIndex = tableIndex + 1; tableIndex < table.length; tableIndex++) {
                        current = table[tableIndex];
                        if (current != null) {
                            break;
                        }
                    }
                }
            }

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                EntrySetItem temp = current;
                findIndex();
                return temp.element;
            }
        };
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public RegularHashMultiSet<E> clone() {
        RegularHashMultiSet<E> set = new RegularHashMultiSet<>(table.length);

        Iterator<E> iterator = iterator();

        while (iterator.hasNext()) {
            E element = iterator.next();
            set.add(element, getQuantity(element));
        }

        return set;
    }
}
