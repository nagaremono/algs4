import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
  private Item[] arr;
  private int first;
  private int last;
  private int n;

  public Deque() {
    arr = (Item[]) new Object[2];
    first = 0;
    last = 0;
    n = 0;
  }

  public boolean isEmpty() {
    return n == 0;
  }

  public int size() {
    return n;
  }

  public void addFirst(Item item) {
    validateAdd(item);
    if (isEmpty()) {
      arr[first] = item;
      n++;
      return;
    }
    if (first == 0) {
      resize(arr.length * 2);
    }

    n++;
    arr[--first] = item;
  }

  public void addLast(Item item) {
    validateAdd(item);
    if (isEmpty()) {
      arr[last] = item;
      n++;
      return;
    }
    if (last == arr.length - 1) {
      resize(arr.length * 2);
    }

    n++;
    arr[++last] = item;
  }

  public Item removeFirst() {
    validateRemove();
    Item item = arr[first];
    arr[first] = null;
    n--;
    if (!isEmpty()) {
      first++;
    }

    if (n > 0 && n == arr.length / 4) {
      resize(arr.length / 2);
    }

    return item;
  }

  public Item removeLast() {
    validateRemove();
    Item item = arr[last];
    arr[last] = null;
    n--;
    if (!isEmpty()) {
      last--;
    }

    if (n > 0 && n == arr.length / 4) {
      resize(arr.length / 2);
    }

    return item;
  }

  public Iterator<Item> iterator() {
    return new ListIterator();
  }

  private class ListIterator implements Iterator<Item> {
    int i = first;

    public boolean hasNext() {
      return !isEmpty() && i != last + 1;
    }

    public Item next() {
      if (!hasNext())
        throw new NoSuchElementException();

      Item item = arr[i];
      i++;
      return item;
    }
  }

  public static void main(String[] args) {
    Deque<Integer> d = new Deque<Integer>();

    d.addFirst(1);
    d.removeFirst();
    d.addFirst(4);
  }

  private void resize(int size) {
    Item[] newArr = (Item[]) new Object[size];
    int newP = (size - n) / 2;
    int newFirst = newP;

    for (int i = first; i != last + 1; i++) {
      newArr[newP] = arr[i];
      newP++;
    }

    int newLast = newP;

    first = newFirst;
    last = newLast - 1;
    arr = newArr;
  }

  private void validateAdd(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
  }

  private void validateRemove() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
  }
}
