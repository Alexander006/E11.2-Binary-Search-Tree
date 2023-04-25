/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

import com.sun.source.tree.Tree;

/**
 * An implementation of the Set API using a binary search tree.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class TreeSet<T extends Comparable<T>> implements Set<T> {

	// fields: store the root of ths bst and the size.
	public Node<T> root;
	private int size;


	public TreeSet(){
		root = null;
		size = 0;
	}

	@Override
	public boolean add(T element) {
		if(root == null){
			root = new Node<>(element);
			size++;
			return true;
		}
		return add(root, element);
	}

	/**
	 * Recursive helper method to add element as a leaf in the BST.
	 * - precondition: current != null
	 *
	 * @param current
	 * @param element
	 * @return true if element is added, false if it is already in the tree.
	 */
	private boolean add(Node<T> current, T element) {
		//If the element doesn't exist yet
		//Search for any existing element corresponding to the passed element
		//in the parameters
		int cmp = current.element.compareTo(element);
		if(cmp == 0){
			return false;
		}
		else if (cmp > 0){
			if (current.left == null){
				current.left = new Node<>(element);
				size++;
				return true;
			}
			else
				return add(current.left,element);
		}
		else{
			if (current.right == null){
				current.right = new Node<>(element);
				size++;
				return true;
			}
			else
				return add(current.right, element);
		}
	}

	@Override
	public boolean contains(T element) {
		return contains(root, element);
	}

	/**
	 * Recursively search the tree to check for the element.
	 * - uses BST property to optimize the search: check only the subtree that
	 * can possibly have the element.
	 *
	 * @param current
	 * @param element
	 * @return
	 */
	private boolean contains(Node<T> current, T element) {
		if(current == null)
			return false;

		int cmp = current.element.compareTo(element);

		//If it found the element
		if(cmp == 0){
			return true;
		}
		else if (cmp > 0){
			return contains(current.left,element);
		}
		else{
			return contains(current.right, element);
		}
	}
	@Override
	public boolean containsAll(Set<T> rhs) {
		return false;
	}

	@Override
	public boolean remove(T element) {
		return removeHelper(root, null, element);
	}

	/**
	 * Recursive helper method to remove an element.
	 *
	 * @param current the current node.
	 * @param parent  the parent of the current node (null implies current == root)
	 * @param element the element t
	 * @return the value removed
	 */
	private boolean removeHelper(Node<T> current, Node<T> parent, T element) {
		// binary search is unsuccessful
		if (current == null) {
			return false;
		}

		int comparison = element.compareTo(current.element);

		// node found:
		if (comparison == 0) {
			// if we need to remove an internal node with two children
			// find the successor in the left subtree and replace the current entry
			// with the successor's entry.
			if (current.left != null && current.right != null) {
				Node<T> tmp = current; // store the current node to replace its entry

				// Ensure that `current` is the successor and that `parent` is its parent
				// so that we remove this node below
				current = current.left;

				while (current.right != null) {
					parent = current;
					current = current.right;
				}

				tmp.element = current.element;
			}

			removeNode(current, parent);
			size--;
			return true;
		}

		// descend into the subtree that could contain the node
		if (comparison < 0) {
			return removeHelper(current.left, current, element);
		}
		else {
			return removeHelper(current.right, current, element);
		}
	}

	private void removeNode(Node<T> current, Node<T> parent) {
		// case 1: root
		if (current == root) {
			if (current.left == null) {
				root = current.right;
			}
			else {
				root = current.left;
			}
		}

		// case 2: left subtree of parent
		else if (current == parent.left) {
			if (current.left == null) {
				parent.left = current.right;
			}
			else {
				parent.left = current.left;
			}
		}

		// case 3: right subtree of parent
		else {
			if (current.right == null) {
				parent.right = current.left;
			}
			else {
				parent.right = current.right;
			}
		}
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		toStringHelper(root, builder);

		return builder.toString();
	}

	private void toStringHelper(Node<T> current, StringBuilder builder) {
		if (current == null) {
			return;
		}

		toStringHelper(current.left, builder);
		builder.append(current.element);
		toStringHelper(current.right, builder);
	}

	@Override
	public void reset() {

	}

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public T next() {
		return null;
	}

}
