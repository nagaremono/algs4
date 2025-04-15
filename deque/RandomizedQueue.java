import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] arr;
  private int n;

  // construct an empty randomized queue
  public RandomizedQueue() {
    arr = (Item[]) new Object[1];
    n = 0;
  }

  // is the randomized queue empty?
  public boolean isEmpty() {
    return n == 0;
  }

  // return the number of items on the randomized queue
  public int size() {
    return n;
  }

  // add the item
  public void enqueue(Item item) {
    validateEnqueue(item);
    if (n == arr.length) {
      resize(arr.length * 2);
    }
    arr[n] = item;
    n++;
  }

  // remove and return a random item
  public Item dequeue() {
    validateDequeue();
    Item item = null;
    int idx = 0;

    while (item == null) {
      idx = genRandomInt(0, n);
      item = arr[idx];
    }

    arr[idx] = arr[n - 1];
    arr[n - 1] = null;
    n--;

    if (n > 0 && n == arr.length / 4) {
      resize(arr.length / 2);
    }

    return item;
  }

  // return a random item (but do not remove it)
  public Item sample() {
    validateDequeue();
    int idx = genRandomInt(0, n);
    Item item = arr[idx];

    return item;
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new ListIterator();
  }

  private class ListIterator implements Iterator<Item> {
    int i = 0;

    public boolean hasNext() {
      return i < arr.length && arr[i] != null;
    }

    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Item item = arr[i++];
      return item;
    }
  }

  // unit testing (required)
  public static void main(String[] args) {
    RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

    rq.enqueue(337);
    rq.enqueue(380);
    rq.enqueue(542);
    System.out.println(rq.dequeue());
    System.out.println(rq.size());

    for (int i : rq) {
      System.out.println(i);
    }
  }

  private void resize(int size) {
    Item[] newArr = (Item[]) new Object[size];
    for (int i = 0; i < n; i++) {
      newArr[i] = arr[i];
    }
    arr = newArr;
  }

  private void validateEnqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
  }

  private void validateDequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
  }

  private int genRandomInt(int min, int max) {
    Random random = new Random();
    return random.nextInt(max - min) + min;
  }
}
