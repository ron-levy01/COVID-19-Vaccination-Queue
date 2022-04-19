public class CoronaQueue {

    Person[] data;
    int[] next;
    int[] prev;
    int[] ref;             // the reference array
    int size;             // the current number of subjects in the queue
    int head;             // the index of the lists 'head'. -1 if the list is empty.
    int tail;             // the index of the lists 'tail'. -1 if the list is empty.
    int free;             // the index of the first 'free' element.

    /**
     * Creates an empty data structure with the given capacity.
     * The capacity dictates how many different subjects may be put into the data structure.
     * Moreover, the capacity gives an upper bound to the ID number of a Person to be put in the data structure.
     */
    public CoronaQueue(int capacity) {
        size = 0;
        head = -1;
        tail = -1;
        free = capacity - 1;
        data = new Person[capacity];
        next = new int[capacity];
        prev = new int[capacity];
        ref = new int[capacity + 1];
        for (int i = 0; i < capacity; i++) {
            next[i] = i - 1;
            ref[i] = -1;
        }
        ref[capacity] = -1;
    }

    /**
     * Returns the size of the queue.
     *
     * @return the size of the queue
     */
    public int size() {
        return size;
    }

    /**
     * Inserts a given Person into the queue.
     * Inesertion should be done at the tail of the queue.
     * If the given person is already in the queue this function should do nothing.
     * Throws an illegal state exception if the queue is full.
     *
     * @param p - the Task to be inserted.
     * @throws IllegalStateException - if queue is full.
     */
    public void enqueue(Person p) {

        if (size == data.length) {
            throw new IllegalStateException("Queue is full");
        }
        if (ref[p.id] != -1) {
            return;
        }
        int newFree = next[free];
        data[free] = p;
        ref[p.id] = free;

        prev[free] = tail;
        if (tail == -1) { //empty queue
            head = free;
        } else {
            next[tail] = free;
        }
        tail = free;
        next[tail] = -1;
        free = newFree;
        size++;

    }


    /**
     * Removes and returns a Person from the queue.
     * The person removed is the one which sits at the head of the queue.
     * If the queue is empty returns null.
     */

    public Person dequeue() {
        if (size == 0) {
            return null;
        }
        int newHead = next[head];
        Person toRemove = data[head];
        data[head] = null;
        ref[toRemove.id] = -1;
        next[head] = free;
        prev[head] = 0;
        if (size == 1) {
            tail = -1;
        }
        if (size != 1) {
            prev[newHead] = -1;
        }
        free = head;
        head = newHead;
        size--;
        return toRemove;
    }

    /**
     * Removes a Person from (possibly) the middle of the queue.
     * <p>
     * Does nothing if the Person is not already in the queue.
     * Recall that you are not allowed to traverse all elements in the queue. In particular no loops or recursion.
     * Think about all the different edge cases and the variables which need to be updated.
     * Make sure you understand the role of the reference array for this function.
     *
     * @param p - the Person to remove
     */
    public void remove(Person p) {
        if (ref[p.id] == -1) {
            return;
        }
        int indexToRemove = ref[p.id];
        ref[p.id] = -1;
        if (indexToRemove == head) {
            dequeue();
            return;
        }

        // size at least 2

        if (indexToRemove == tail) {

            int newTail = prev[tail];
            prev[tail] = 0;
            next[indexToRemove] = free;
            free = tail;
            tail = newTail;

        }
        // Remove from middle queue
        else {

            next[prev[indexToRemove]] = next[indexToRemove];
            prev[next[indexToRemove]] = prev[indexToRemove];
            next[indexToRemove] = free;
            prev[indexToRemove] = 0;
            free = indexToRemove;

        }
        data[indexToRemove] = null;
        size--;
    }

    /*
     * The following functions may be used for debugging your code.
     */
    private void debugNext() {
        for (int i = 0; i < next.length; i++) {
            System.out.println(next[i]);
        }
        System.out.println();
    }

    private void debugPrev() {
        for (int i = 0; i < prev.length; i++) {
            System.out.println(prev[i]);
        }
        System.out.println();
    }

    private void debugData() {
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i]);
        }
        System.out.println();
    }

    private void debugRef() {
        for (int i = 0; i < ref.length; i++) {
            System.out.println(ref[i]);
        }
        System.out.println();
    }

    /*
     * Test code; output should be:
		Aaron, ID number: 1
		Baron, ID number: 2
		Cauron, ID number: 3
		Dareon, ID number: 4
		Aaron, ID number: 1
		Baron, ID number: 2
		Aaron, ID number: 1
		
		Baron, ID number: 2
		Cauron, ID number: 3
		
		Aaron, ID number: 1
		Dareon, ID number: 4
		
		Aaron, ID number: 1
		Cauron, ID number: 3
     */
    public static void main(String[] args) {
        CoronaQueue demo = new CoronaQueue(4);
        Person a = new Person(1, "Aaron");
        Person b = new Person(2, "Baron");
        Person c = new Person(3, "Cauron");
        Person d = new Person(4, "Dareon");

        demo.enqueue(a);
        demo.enqueue(b);
        demo.enqueue(c);
        demo.enqueue(d);
        System.out.println(demo.dequeue());
        System.out.println(demo.dequeue());
        demo.enqueue(a);
        System.out.println(demo.dequeue());
        demo.enqueue(b);
        System.out.println(demo.dequeue());
        System.out.println(demo.dequeue());
        System.out.println(demo.dequeue());
        demo.enqueue(a);
        System.out.println(demo.dequeue());

        System.out.println();
        demo.enqueue(a);
        demo.enqueue(b);
        demo.enqueue(c);
        demo.enqueue(d);
        demo.remove(a);
        System.out.println(demo.dequeue());
        demo.remove(d);
        System.out.println(demo.dequeue());

        System.out.println();
        demo.enqueue(a);
        demo.enqueue(b);
        demo.enqueue(c);
        demo.enqueue(d);
        demo.remove(b);
        demo.remove(c);
        System.out.println(demo.dequeue());
        System.out.println(demo.dequeue());

        System.out.println();
        demo.enqueue(a);
        demo.enqueue(b);
        demo.enqueue(c);
        demo.enqueue(d);
        demo.remove(b);
        demo.remove(d);
        System.out.println(demo.dequeue());
        System.out.println(demo.dequeue());


    }
}
