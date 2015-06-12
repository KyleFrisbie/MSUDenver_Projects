/**
 * Created by Kyle on 10/17/2014.
 */
/**
 * @author Kyle L Frisbie
 * Stack:
 *  This stack provides two constructors, a default constructor which initializes the
 *  stack array to a length of 10, and a seperate constructor which can take a
 *  predefined length from the client. This stack class contains the basic stack
 *  operations plus an additional "peek" method, which allows the client to see the
 *  top item of the stack without removing it.
 */
public class Stack<Item> {
    Item[] stackArray;
    int top;

    /********************************************************************************/
    /**
     * Stack constructor, sets default array size to 10
     */
    public Stack() {
        stackArray = (Item[]) new Object[10];
        top = -1;
    } // Stack

    /********************************************************************************/
    /**
     * Stack constructor with int "size" parameter, sets stack size to user defined
     * size
     * @param size int, set size of stack
     */
    public Stack(int size) {
        stackArray = (Item[]) new Object[size];
        top = -1;
    } // Stack

    /********************************************************************************/
    /**
     * push item to top of stack
     * @param itemIn Item to be placed on top of stack
     */
    public void push(Item itemIn) {
        top++;
        stackArray[top] = itemIn;
    } // push

    /********************************************************************************/
    /**
     * pop top item off of stack
     * @return Item from top of stack
     */
    public Item pop() {
        Item result = stackArray[top];
        top--;
        return result;
    } // pop

    /********************************************************************************/
    /**
     * glance at item on top of stack without altering stack
     * @return Item from top of stack, does not alter stack
     */
    public Item peek() {
        Item item = pop();
        push(item);
        return item;
    } // peek

    /********************************************************************************/
    /**
     * returns size of stack
     * @return int, size of stack
     */
    public int size() {
        return top + 1;
    } // size

    /********************************************************************************/
    /**
     * returns true if stack is empty
     * @return boolean, true if empty
     */
    public boolean isEmpty() {
        return top == -1;
    } // isEmpty
} // Stack class
