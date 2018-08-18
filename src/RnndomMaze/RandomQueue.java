package RnndomMaze;


import java.util.LinkedList;

/*
 * 使用链表数组作为底层实现随机队列
 * 
 * 存取元素都是在队首或者队尾相同的概率
 */
public class RandomQueue<E> {
     private LinkedList<E> queue;
     
     public RandomQueue(){
    	 queue=new LinkedList<>();
    	 
     }
     public void add(E e){
    	 if(Math.random()<0.5)
    		 queue.addLast(e);
    	 else
    		 queue.addFirst(e);
     }
     public E remove(){
    	 if(queue.isEmpty())
    		 throw new IllegalArgumentException("empty");
    	 if(Math.random()<0.5)
    		 return queue.removeLast();
    	 else
    		 return queue.removeFirst();
     }
     
     public int size(){
    	 return queue.size();
     }
     public boolean isEmpty(){
    	 return queue.size()==0;
     }
}
