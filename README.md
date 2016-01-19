# tk.saksmt.util/collections

Contains some util collection implementations

## Classes

### CyclicCollection

| Package | Interfaces  | Thread-Safe |
|---------|-------------|:-------:|
| `tk.saksmt.util.collections.CyclicCollection` | `java.util.Collection` | &#x2713; |


#### Description

Collection based on cycled queue with specified size. Overflow of collection causes cyclic removal from end and addition of new element to head (see an example below)

Consider you have a `CyclicCollection<Integer>` with size of 3:

| _ |
|:-:|
| _ |
| _ |

After addition of 3 elements you'd have:

| 1 |
|:-:|
| 2 |
| 3 |

Then you add one more element, let's say `4`, then you'd have:

| 2 |
|:-:|
| 3 |
| 4 |

#### Usage

That's an implementation of description above

```java
import tk.saksmt.util.collections.CyclicCollection;

Collection<Integer> collection = new CyclicCollection<>(3);

// Add 3 elements
collection.add(1);
collection.add(2);
collection.add(3);

// Add one more
collection.add(4);

// And the result
System.out.println(collection); // [ 2 3 4 ] 
```

## License

This library is licensed under [MIT license](https://github.com/saksmt/util-collections/blob/develop/LICENSE)
