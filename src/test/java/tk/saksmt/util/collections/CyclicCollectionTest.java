package tk.saksmt.util.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Kirill Saksin <kirill.saksin@billing.ru>
 */
public class CyclicCollectionTest {

    @Test
    public void test() {
        Collection<Integer> expected = new ArrayList<>();
        Collection<Integer> collection = new CyclicCollection<>(5);
        Assert.assertTrue(collection.isEmpty());
        for (int i = 1; i <= 5; i++) {
            Assert.assertTrue(collection.add(i));
            Assert.assertTrue(collection.contains(i));
            Assert.assertEquals(i, collection.size());
            expected.add(i);
        }
        Assert.assertFalse(collection.isEmpty());
        Assert.assertTrue(collection.containsAll(expected));
        Assert.assertArrayEquals(new Object[] {1,2,3,4,5}, collection.toArray());
        expected.clear();
        for (int i = 6; i <= 10; i++) {
            Assert.assertTrue(collection.add(i));
            Assert.assertTrue(collection.contains(i));
            Assert.assertEquals(5, collection.size());
            expected.add(i);
        }
        Assert.assertTrue(collection.containsAll(expected));
        Assert.assertTrue(collection.remove(6));
        Assert.assertEquals(4, collection.size());
        expected.remove(6);
        Assert.assertTrue(collection.retainAll(new ArrayList<>()));
        Assert.assertTrue(collection.isEmpty());
        Assert.assertTrue(collection.addAll(expected));
        Assert.assertEquals(4, collection.size());
        Assert.assertTrue(collection.removeAll(expected));
        Assert.assertTrue(collection.isEmpty());
        Assert.assertFalse(collection.remove(4_0_4));
        collection.add(42);
        collection.clear();
        Assert.assertTrue(collection.isEmpty());

        collection = new CyclicCollection<Integer>(3);
        collection.add(1);
        collection.add(2);
        collection.add(3);
        collection.add(4);
        System.out.println(collection);
    }
}
