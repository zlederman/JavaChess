package core.engine;
import java.util.Arrays;

public class MoveHeap {
    private Move[] heap = new Move[50];
    private int size;

    private int maxSize = 50;
    public MoveHeap(){
        MoveType moveType  =MoveType.NONCAPTURE;
        Move root = new Move('x',moveType,(short)0,(short)0);
        heap[0] = root;



    }

    private int parentPos(int pos){
        return pos / 2;
    }

    private int leftChild(int pos){
        return pos * 2;

    }

    private int rightChild(int pos){
        return (2 * pos) + 1;
    }

    private boolean isLeafNode(int pos){
        if(pos >= size/2 && pos <= size){
            return true;
        }
        return false;
    }

    private void swap(int fpos, int ipos){
        Move temp;
        temp = heap[fpos];
        heap[fpos] = heap[ipos];
        heap[ipos] = temp;

    }

    private void maxHeapify(int pos){
        if(isLeafNode(pos)){
            return;
        }
        if(heap[pos].score() < heap[leftChild(pos)].score()||
            heap[pos].score() < heap[rightChild(pos)].score()){

            if(heap[leftChild(pos)].score() > heap[leftChild(pos)].score()){
                swap(pos,leftChild(pos));
                maxHeapify(leftChild(pos));
            }
            else{
                swap(pos, rightChild(pos));
                maxHeapify(rightChild(pos));
            }
        }
    }

    public void insertArr(Move[] moves){
        if(moves != null){
            for(int i = 0; i < moves.length; i++){
                if(moves[i] != null){
                    insert(moves[i]);
                }

            }
        }

    }

    public void insert(Move element){


            if(size  >= maxSize - 1){
                resize();
            }
            if(element == null){
                return;
            }
            heap[++ size] = element;
            int current = size;
            while(heap[current].score() > heap[parentPos(current)].score()){
                swap(current,parentPos(current));
                current  = parentPos(current);
            }


    }

    public Move popMax(){
        Move popped = heap[1];

        heap[1] = heap[size--];
        maxHeapify(1);
        return popped;
    }

    private void resize(){
        this.heap = Arrays.copyOf(heap, heap.length * 2);
        maxSize *= 2;
    }
    public boolean isEmpty(){
        if(size <=  0){
            return true;
        }
        return false;
    }


    public int getSize() {
        return size;
    }

    public void print()
    {
        for (int i = 1; i <= size / 2; i++) {
            System.out.print(" PARENT : " + heap[i] + " LEFT CHILD : " +
                    heap[2 * i] + " RIGHT CHILD :" + heap[2 * i + 1]);
            System.out.println();
        }
    }


}
