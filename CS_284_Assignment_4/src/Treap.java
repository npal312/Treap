import java.util.Random;
import java.util.Stack;

public class Treap<E extends Comparable<E>> {

	public class Node<E> {
		/** Node data fields */
		public E data;
		public int priority;
		public Node<E> left;
		public Node<E> right;
		
		/** Creates a Node with the given data and priority fields
		 * @param data The key to be assigned to the Node
		 * @param priority The random heap priority 
		 * @param left The left child Node of this Node
		 * @param right The right child Node of this Node
		 * @throws IllegalArgumentException if data is null (can't insert null data into a Node)
		 * */
		public Node(E data, int priority) {
			if (data == null) throw new IllegalArgumentException("Must insert data into the node!");
			this.data = data;
			this.priority = priority;
			this.left = null;
			this.right = null;
		}
		
		/** Rotates the Node to the right and reassigns connections between nodes accordingly
		 * @return The new localRoot of the rotation with all the connections updated
		 */
		public Node<E> rotateRight(){
			Node<E> oldRoot = this; //stores old root
			Node<E> newRoot = this.left;
			if (newRoot.right != null) {
				oldRoot.left = newRoot.right;
			}
			else {
				oldRoot.left = null;
			}
			newRoot.right = oldRoot;
			return newRoot;
		}
		
		/** Rotates the Node to the right and reassigns connections between nodes accordingly
		 * @return The new localRoot of the rotation with all the connections updated
		 */
		public Node<E> rotateLeft(){
			Node<E> oldRoot = this; //stores old root
			Node<E> newRoot = this.right;
			if (newRoot.left != null) {
				oldRoot.right = newRoot.left;
			}
			else {
				oldRoot.right = null;
			}
			newRoot.left = oldRoot;
			return newRoot;
		}
		
		/** returns the String representation of the Node */
		public String toString() {
			return "[key: " + this.data + ", priority: " + this.priority + "]";
		}
	}
	
	/** Treap data fields */
	private Random priorityGenerator;
	private Node<E> root;

	
	/** Makes an empty Treap with a null root and no seed to initialize priorityGenerator */
	public Treap() {
		this.root = null;
		this.priorityGenerator = new Random();
	}
	
	/** Makes an empty Treap with a null root
	 * @param seed To initialize priorityGenerator with
	 */
	public Treap(long seed) {
		this.root = null;
		this.priorityGenerator = new Random(seed);
	}
	
	/** Adds a Node to the Treap with input key and randomly generated priority
	 * @param key The key to use for finding the Node
	 * @return The result of the other add function (true if added and false if not)
	 */
	public boolean add(E key) {
		int priority = priorityGenerator.nextInt();
		return(add(key, priority));
	}
	
	/** Adds a Node to the Treap using BST methodology with input key and priority values
	 * @param key The key to use for finding the Node
	 * @param priority The heap priority
	 * @return false if a Node with the same key is already present and true if the Node was successfully added to the Treap
	 */
	public boolean add(E key, int priority) {
		if (find(key) == true) { //node already exists
			return false;
		}
		
		Node<E> newNode = new Node<E>(key, priority);
		
		if (root == null) { //if empty treap
			root = newNode;
			return true;
		}
		
		Stack<Node<E>> nodeStack = new Stack<Node<E>>();
		
		Node<E> currentNode = root;
		
		while (currentNode != null) {
			if (key.compareTo(currentNode.data) > 0) {
				
				nodeStack.push(currentNode); //push to stack
				
				//check if right slot is empty
				if (currentNode.right == null) { //if right slot is empty
					currentNode.right = newNode;
					reheap(newNode, nodeStack);
					return true;
				}
				else { //if right slot is not empty
					currentNode = currentNode.right;
				}

			}
			else { //if (key.compareTo(currentNode.data) < 0)
				nodeStack.push(currentNode); //push to stack
				
				//check if left slot is empty
				if (currentNode.left == null) { //if right slot is empty
					currentNode.left = newNode;
					reheap(newNode, nodeStack);
					return true;
				}
				else { //if left slot is not empty
					currentNode = currentNode.left;
				}
			}
			
		}
		return false;
	}
	
	/** Helper function that fixes the Treap by restructuring it to restore the heap invariant
	 * @param currentNode The newly added Node in the Treap that violates the heap invariant
	 * @param nodeStack A stack of every ancestor Node of the currentNode
	 * @return void, but is done when the heap invariant is restored or the nodeStack is empty
	 */
	public void reheap(Node<E> currentNode, Stack<Node<E>> nodeStack) {
		while (nodeStack.isEmpty() == false) {	
			
			Node<E> parentNode = nodeStack.pop();
			
			if (parentNode.priority >= currentNode.priority) {
				return;
			}
			
			else {
				if (parentNode.left == currentNode) { //checks if left child
					parentNode.rotateRight();
					if (root == parentNode) {
						root = currentNode;
					}
				}
				else if (parentNode.right == currentNode) { //checks if right child
					parentNode.rotateLeft();
					if (root == parentNode) {
						root = currentNode;
					}
				}
				
				if (nodeStack.isEmpty() == true) {
					return;
				}
				
				if (nodeStack.peek().left == parentNode) { //checks if should be left child
					nodeStack.peek().left = currentNode;
				}
				else if (nodeStack.peek().right == parentNode) { //checks if should be right child
					nodeStack.peek().right = currentNode;
				}
				
			}
		}
	}
	
	/** Deletes the Node corresponding to input key while maintaining BST and heap properties
	 * @param key The key of the Node that should be deleted
	 * @return false if treap is empty or if desired Node is not present, and true if Node is successfully deleted
	 */
	public boolean delete(E key) {
		if (root == null) { //if empty treap
			return false;
		}
		
		if (find(key) == false) { //if trying to remove something not in the treap
			return false;
		}
		
		Node<E> currentNode = root;
		
		if (currentNode.left == null && currentNode.right == null) { //if root is only thing in treap
			root = null;
			return true;
		}
		
		Node<E> parentNode = null; //for removing from tree at very end (long haul variable)
		
		while (key.compareTo(currentNode.data) != 0) { //finds the node using the key
			if (key.compareTo(currentNode.data) > 0) {
				parentNode = currentNode;
				currentNode = currentNode.right;
			}
			else { //if (key.compareTo(currentNode.date) < 0)
				parentNode = currentNode;
				currentNode = currentNode.left;
			}
		}

		
		while (currentNode.left != null || currentNode.right != null) { //while not leaf
			if (currentNode.left != null && currentNode.right == null) { //if only left subtree
				Node<E> grandparentNode = parentNode; //stores to fix connections between generations
				parentNode = currentNode.left; //stores node for later
				currentNode.rotateRight();
				if (grandparentNode != null && grandparentNode.left == currentNode) { //restores connection
					grandparentNode.left = parentNode;
				}
				else if (grandparentNode != null && grandparentNode.right == currentNode) {
					grandparentNode.right = parentNode;
				}
			}
			else if (currentNode.left == null && currentNode.right != null) { //if only right subtree
				Node<E> grandparentNode = parentNode; //stores to fix connections between generations
				parentNode = currentNode.right; //stores node for later
				currentNode.rotateLeft();
				if (grandparentNode != null && grandparentNode.left == currentNode) { //restores connection
					grandparentNode.left = parentNode;
				}
				else if (grandparentNode != null && grandparentNode.right == currentNode) {
					grandparentNode.right = parentNode;
				}
			}
			else { //if both, or if (currentNode.left != null && currentNode.right != null)
				if (currentNode.left.priority > currentNode.right.priority) { //go with left node
					Node<E> grandparentNode = parentNode; //stores to fix connections between generations
					parentNode = currentNode.left; //stores node for later
					currentNode.rotateRight();
					if (grandparentNode != null && grandparentNode.left == currentNode) { //restores connection
						grandparentNode.left = parentNode;
					}
					else if (grandparentNode != null && grandparentNode.right == currentNode) {
						grandparentNode.right = parentNode;
					}
				}
				else if (currentNode.left.priority <= currentNode.right.priority) { //go with right node
					Node<E> grandparentNode = parentNode; //stores to fix connections between generations
					parentNode = currentNode.right; //stores node for later
					currentNode.rotateLeft();
					if (grandparentNode != null && grandparentNode.left == currentNode) { //restores connection
						grandparentNode.left = parentNode;
					}
					else if (grandparentNode != null && grandparentNode.right == currentNode) {
						grandparentNode.right = parentNode;
					}
				}
			}
			
			if (currentNode == root) { //in case of removing root (so it doesn't mess with the toString)
				root = parentNode;
			}
		}
		
		if (parentNode.left == currentNode) {
			parentNode.left = null;
			return true;
		}
		else if (parentNode.right == currentNode) {
			parentNode.right = null;
			return true;
		}
		return false; //something went wrong bc this shouldn't be reached
	}
	
	/** Finds a Node with the input key in the Treap with input root
	 * @param root The root of the Treap being searched
	 * @param key The key pertaining to the Node being searched for
	 * @return false if Node is not found or if empty Treap, and true if Node is found in Treap
	 */
	private boolean find(Node<E> root, E key) {
		if (root == null) { //if leaf or empty treap
			return false;
		}
		
		if (key.compareTo(root.data) == 0) { //if same as root
			return true;
		}
		
		if (key.compareTo(root.data) > 0) { //if right child
			return find(root.right, key);
		}
		
		else { //if left child, if (key.compareTo(root.data) < 0)
			return find(root.left, key);
		}
	}
	
	/** Finds a Node with the input key in the Treap
	 * @param key The key pertaining to the Node being searched for
	 * @return false if Node is not found or if empty Treap, and true if Node is found in Treap
	 */
	public boolean find(E key) {
		if (root == null) { //if empty treap
			return false;
		}
		
		if (key.compareTo(root.data) == 0) { //if same as root
			return true;
		}
		
		if (key.compareTo(root.data) > 0) { //if right child
			return find(root.right, key);
		}
		
		else { //if left child, if (key.compareTo(root.data) < 0)
			return find(root.left, key);
		}
	}
	
	/** Recursively creates a String representation of the Treap
	 * @param currentNode The Node being appended to the String representation
	 * @param depth The level of the Treap the code is currently accessing
	 * @return The String representation of the Treap
	 */
	public String toString(Node<E> currentNode, int depth) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			sb.append("-"); //num of dashes = tree depth
		}
		if (currentNode == null) {
			sb.append("null\n"); //if empty treap
		} else {
			sb.append(currentNode.toString() + "\n");
			sb.append(toString(currentNode.left, depth+1)); //recursively appending left child to String
			sb.append(toString(currentNode.right,depth+1)); //recursively appending right child to String
		}
		return sb.toString();
	}
	
	/** Returns a String representation of the Treap starting at the root and a depth of 0 */
	public String toString() {
		return toString(root, 0);
	}
	
	public static void main(String[] args) {
	}
}
