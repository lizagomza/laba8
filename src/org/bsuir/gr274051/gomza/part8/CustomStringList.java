package org.bsuir.gr274051.gomza.part8;

import java.util.ArrayList;

class CustomStringList extends ArrayList<String> {
    private final Integer capacity; // if null -> unlimited

    public CustomStringList() {
        this.capacity = null;
    }

    public CustomStringList(int capacity) {
        super();
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.capacity = capacity;
    }

    @Override
    public boolean add(String s) {
        if (capacity != null && this.size() >= capacity) {
            // remove first element then add
            super.remove(0);
        }
        return super.add(s);
    }

    @Override
    public void add(int index, String element) {
        if (capacity != null && this.size() >= capacity) {
            super.remove(0);
        }
        // adjust index after removal if necessary
        int idx = Math.min(index, this.size());
        super.add(idx, element);
    }

    /**
     * Сравнивает внутренние объекты по index: возвращает 0 если equal, <0 если first<second (lexicographically), >0 иначе.
     */
    public int compareInnerObjects(int firstIndex, int secondIndex) {
        if (firstIndex < 0 || secondIndex < 0 || firstIndex >= this.size() || secondIndex >= this.size()) {
            throw new IndexOutOfBoundsException("Индексы должны быть в пределах списка");
        }
        String a = this.get(firstIndex);
        String b = this.get(secondIndex);
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        int eq = a.compareTo(b);
        if (eq == 0) return 0;
        return eq;
    }
}