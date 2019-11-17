//Sanika Buche ssb170002
public class Node<E> {
	private E payload; //payload variable
	private Node<E> left; //left pointer
	private Node<E> right; //right ponter
	
	//constructors
	Node(){
		this.payload = null;
		this.left = null;
		this.right = null;
	}
	
	//overloaded constructor
	Node(E o){
		this.payload = o;
		left = right = null;
	}
	
	//getters
	public E getPayload() {return payload;}
	public Node<E> getLeft() {return left;}
	public Node<E> getRight() {return right;}
	
	//setters
	public void setPayload(E temp) {this.payload = temp;}
	public void setLeft(Node<E> temp) {this.left = temp;}
	public void setRight(Node<E> temp) {this.right = temp;}
}
