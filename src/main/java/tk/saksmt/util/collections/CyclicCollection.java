package tk.saksmt.util.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Collection based on cycled queue with specified size
 * Overflow of collection causes cyclic removal from end and addition of new element to head (see an example below)
 * Collection is thread-safe
 *
 * +---+          +---+          +---+          +---+
 * | 1 |          | 1 |          | 1 |          | 2 |
 * | - | + 2  =>  | 2 | + 3  =>  | 2 | + 4  =>  | 3 |
 * | - |          | - |          | 3 |          | 4 |
 * +---+          +---+          +---+          +---+
 * @author Kirill Saksin <kirill.saksin@billing.ru>
 * @version 1.0.0
 */
public class CyclicCollection<E> implements Collection<E> {
    private final Queue<E> storage;
    private final int size;
    private AtomicInteger actualSize = new AtomicInteger(0);

    /**
     * Constructor
     * @param size Size of collection
     */
    public CyclicCollection(int size) {
        this(size, new ConcurrentLinkedQueue<E>());
    }

    public CyclicCollection(int size, Queue<E> storage) {
        this.size = size;
        this.storage = storage;
        actualSize = new AtomicInteger(storage.size());
    }

    @Override
    public int size() {
        actualSize.set(storage.size());
        return actualSize.get();
    }

    @Override
    public boolean isEmpty() {
        return storage.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return storage.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return storage.iterator();
    }

    @Override
    public Object[] toArray() {
        return storage.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] t1s) {
        return storage.toArray(t1s);
    }

    @Override
    public boolean add(E e) {
        boolean result = storage.offer(e);
        if (actualSize.incrementAndGet() > size) {
            storage.poll();
        }
        return result;
    }

    @Override
    public boolean remove(Object o) {
        if (storage.remove(o)) {
            actualSize.decrementAndGet();
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return storage.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        for (E element: collection) {
            add(element);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean result = storage.removeAll(collection);
        actualSize.set(storage.size());
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean result = storage.retainAll(collection);
        actualSize.set(storage.size());
        return result;
    }

    @Override
    public void clear() {
        storage.clear();
        actualSize.set(0);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (E element: this) {
            builder.append(' ').append(String.valueOf(element));
        }
        return builder.append(" ]").toString();
    }
}
