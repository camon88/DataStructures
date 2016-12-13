

/**
 * A DoubleLinkedSeq is a sequence of double numbers. The sequence can have a
 * special &quot;current element&quot;, which is specified and accessed through
 * four methods that are not available in the IntArrayBag class (start,
 * getCurrent, advance, and isCurrent).
 * 
 * Limitations:
 * 
 * Beyond Integer.MAX_VALUE element, the size method does not work.
 * 
 * @author Jonathan
 * 
 */
public class DoubleLinkedSeq implements Cloneable 
{

    private int manyNodes;

    private DoubleNode head;
    private DoubleNode tail;
    private DoubleNode cursor;
    private DoubleNode precursor;

    /**
     * Initializes an empty DoubleLinkedSeq.
     * 
     * @postcondition This sequence is empty.
     */
    public DoubleLinkedSeq()
    {
        head = null;
        tail = null;
        cursor = null;
        precursor = null;

        manyNodes = 0;



    }

    /**
     * Adds a new element to this sequence.
     * 
     * @param element
     *            the new element that is being added to this sequence.
     * 
     * @postcondition a new copy of the element has been added to this sequence.
     *                If there was a current element, then this method places
     *                the new element before the current element. If there was
     *                no current element, then this method places the new
     *                element at the front of this sequence. The newly added
     *                element becomes the new current element of this sequence.
     */
    public void addBefore(double element)
    {
        if (head == null)
        {
            head = new DoubleNode(element, head);
            cursor = head;
            tail = head;
        }
        else if (cursor == null || cursor == head)
        {
            head = new DoubleNode(element, head);
            cursor = head;
        }
        else
        {
            precursor.addNodeAfter(element);
            cursor = precursor.getLink();
        }
        manyNodes++;
    }

    /**
     * Adds a new element to this sequence.
     * 
     * @param element
     *            the new element that is being added to this sequence.
     * 
     * @postcondition a new copy of the element has been added to this sequence.
     *                If there was a current element, then this method places
     *                the new element after the current element. If there was no
     *                current element, then this method places the new element
     *                at the end of this sequence. The newly added element
     *                becomes the new current element of this sequence.
     */
    public void addAfter(double element)
    {
        if (isCurrent())
        {
            cursor.addNodeAfter(element);
            if (cursor == tail)
            {
                tail = tail.getLink();

            }
            precursor = cursor;
            cursor = cursor.getLink();
            manyNodes++;

        }
        else
        {
            if (tail == null)
            {
                tail = new DoubleNode(element, null);
                cursor = tail;
                precursor = tail;
                head = tail;


            }
            else
            {
                tail.addNodeAfter(element);
                precursor = tail;
                tail = tail.getLink();
                cursor = tail;

            }

            manyNodes++;

        }
    }

    /**
     * Places the contents of another sequence at the end of this sequence.
     * 
     * @precondition other must not be null.
     * 
     * @param other
     *            a sequence show contents will be placed at the end of this
     *            sequence.
     * 
     * @postcondition the elements from other have been placed at the end of
     *                this sequence. The current element of this sequence
     *                remains where it was, and other is unchanged.
     * 
     * @throws NullPointerException
     *             if other is null.
     */
    public void addAll(DoubleLinkedSeq other) throws NullPointerException
    {
        DoubleNode tempNode;
        DoubleNode otherNode;

        tempNode = tail;
        otherNode = other.head;

        if (tail == null)
        {
            head = tail;
            tail = tempNode;
            tempNode = new DoubleNode(otherNode.getData(), null);
            otherNode = otherNode.getLink();
            throw new NullPointerException("Cannot AddAll, Null");
        }
        while (otherNode != null)
        {
            DoubleNode node = new DoubleNode(otherNode.getData(), null);
            tempNode.setLink(node);
            tempNode = tempNode.getLink();
            otherNode = otherNode.getLink();
            tail = tempNode;
        }
        manyNodes += other.size();

    }


    /**
     * Move forward so that the current element is now the next element in the
     * sequence.
     * 
     * @precondition isCurrent() returns true.
     * 
     * @postcondition If the current element was already the end element of this
     *                sequence (with nothing after it), then there is no longer
     *                any current element. Otherwise, the new element is the
     *                element immediately after the original current element.
     * 
     * @throws IllegalStateException
     *             if there is not current element.
     */
    public void advance() throws IllegalStateException
    {
        if (isCurrent() == false)
        {
            throw new IllegalStateException("There is no current Element.");

        }
        else
        {
            precursor = cursor;
            cursor = cursor.getLink();

        }
    }

    /**
     * Creates a copy of this sequence.
     * 
     * @return a copy of this sequence. Subsequent changes to the copy will not
     *         affect the original, nor vice versa.
     * @throws RuntimeException
     *             if this class does not implement Cloneable.
     * 
     */
    public DoubleLinkedSeq clone() throws RuntimeException
    {


        DoubleLinkedSeq ans = new DoubleLinkedSeq();

        DoubleNode node[] = new DoubleNode[2];
        DoubleNode node1[] = new DoubleNode[2];
        try
        {
            ans = (DoubleLinkedSeq) super.clone();

        }
        catch (CloneNotSupportedException d)

        {
            throw new RuntimeException("Cannot clone");


        }

        if (manyNodes == 0)
        {
            DoubleNode.listCopyWithTail(ans.head);
            ans.head = node[0];
            ans.tail = node[1];
            ans.precursor = null;
            ans.cursor = null;



        }

        else if (cursor == head)
        {
            DoubleNode.listCopyWithTail(ans.head);
            ans.head = node[0];
            ans.tail = node[1];
            ans.precursor = null;
            ans.cursor = ans.head;

        }
        else if (cursor != head)
        {

            DoubleNode.listPart(ans.head, ans.precursor);
            DoubleNode.listPart(ans.cursor, ans.tail);
            ans.head = node[0];
            ans.precursor = node[1];
            ans.cursor = node1[0];
            ans.tail = node1[1];


        }
        return ans;
    }



    /**
     * Creates a new sequence that contains all the elements from s1 followed by
     * all of the elements from s2.
     * 
     * @precondition neither s1 nor s2 are null.
     * 
     * @param s1
     *            the first of two sequences.
     * @param s2
     *            the second of two sequences.
     * 
     * @return a new sequence that has the elements of s1 followed by the
     *         elements of s2 (with no current element).
     * 
     * @throws NullPointerException
     *             if s1 or s2 are null.
     */
    public static DoubleLinkedSeq concatenation(DoubleLinkedSeq s1,
        DoubleLinkedSeq s2) throws NullPointerException
        {
        if (s1 == null)
        {
            throw new NullPointerException("S1 is null.");
        }

        else if (s2 == null)
        {
            throw new NullPointerException("S2 is null.");
        }

        DoubleLinkedSeq s3 = new DoubleLinkedSeq();
        DoubleNode[] temporary;
        temporary = DoubleNode.listCopyWithTail(s1.head);
        s3.head = temporary[0];
        s3.tail = temporary[1];

        s3.addAll(s2);
        s1.manyNodes += s2.manyNodes;
        s3.manyNodes = s1.manyNodes;
        return s3;
        }

    /**
     * Returns a copy of the current element in this sequence.
     * 
     * @precondition isCurrent() returns true.
     * 
     * @return the current element of this sequence.
     * 
     * @throws IllegalStateException
     *             if there is no current element.
     */
    public double getCurrent() throws IllegalStateException
    {

        if (isCurrent() == false)
        {
            throw new IllegalStateException("No Current Element");

        }
        else
        {
            return cursor.getData();
        }

    }

    /**
     * Determines whether this sequence has specified a current element.
     * 
     * @return true if there is a current element, or false otherwise.
     */
    public boolean isCurrent()
    {
        if (cursor != null)
        {
            return true;
        }

        return false;

    }

    /**
     * Removes the current element from this sequence.
     * 
     * @precondition isCurrent() returns true.
     * 
     * @postcondition The current element has been removed from this sequence,
     *                and the following element (if there is one) is now the new
     *                current element. If there was no following element, then
     *                there is now no current element.
     * 
     * @throws IllegalStateException
     *             if there is no current element.
     */
    public void removeCurrent() throws IllegalStateException
    {

        if (!isCurrent())
        {
            throw new IllegalStateException("no current Element");
        }
        else
        {

            if (isCurrent())
            {
                if (manyNodes == 0)
                {
                    head = null;
                    precursor = null;
                    cursor = null;
                    tail = null;
                }
                else if (cursor == head)
                {
                    precursor = null;
                    cursor = cursor.getLink();
                    head = head.getLink();
                    manyNodes--;
                }


                else if (cursor == tail)
                {

                    tail = precursor;
                    tail.setLink(null);
                    cursor = null;
                    precursor = null;
                    manyNodes--;
                }
                else if (manyNodes > 2 && (cursor != head || cursor != tail))
                {

                    cursor = cursor.getLink();
                    precursor.setLink(cursor);

                    manyNodes--;
                }
            }
        }
    }













    /**while (temporary.getLink() != cursor)
            {
                temporary   = temporary.getLink();

            }

            temporary.setLink(cursor.getLink());
            cursor = cursor.getLink();
           manyNodes--;
     */



    /**
     * Determines the number of elements in this sequence.
     * 
     * @return the number of elements in this sequence.
     */
    public int size()
    {
        return manyNodes;    
    }

    /**
     * Sets the current element at the front of this sequence.
     * 
     * @postcondition If this sequence is not empty, the front element of this
     *                sequence is now the current element; otherwise, there is
     *                no current element.
     */
    public void start()
    {
        cursor = head;
        precursor = head;
    }

    /**
     * Returns a String representation of this sequence. If the sequence is
     * empty, the method should return &quot;&lt;&gt;&quot;. If the sequence has
     * one item, say 1.1, and that item is not the current item, the method
     * should return &quot;&lt;1.1&gt;&quot;. If the sequence has more than one
     * item, they should be separated by commas, for example: &quot;&lt;1.1,
     * 2.2, 3.3&gt;&quot;. If there exists a current item, then that item should
     * be surrounded by square braces. For example, if the second item is the
     * current item, the method should return: &quot;&lt;1.1, [2.2],
     * 3.3&gt;&quot;.
     * 
     * @return a String representation of this sequence.
     */
    @Override
    public String toString()
    {


        String str = "<";


        if (manyNodes == 0)
        {
            str = "<>";
        }
        else
        {
            for (DoubleNode temp = head; temp != null; temp = temp.getLink())
            {
                if (temp == cursor)
                {
                    str += "[" + temp.getData() + "]";
                }
                else
                {
                    str += temp.getData();
                }
                if (temp != tail)
                {
                    str += ", ";
                } 
            }

            str += ">";


        }

        System.out.print(str);

        return str; 

    }





    /**
        DoubleNode temp;
        String str = "<" + head.getData();

            for (temp = head; temp != null; temp.setLink(temp.getLink()))
            {
                str = str + temp.getData();
            }
            System.out.println(str);
            return str;
    }
     */
    /**
     * Determines if this object is equal to the other object.
     * 
     * @param other
     *            The other object (possibly a DoubleLinkedSequence).
     * @return true if this object is equal to the other object, false
     *         otherwise. Two sequences are equal if they have the same number
     *         of elements, and each corresponding element is equal
     */
    public boolean equals(Object other)
    {


        return other.toString().equals(toString());

    }


}