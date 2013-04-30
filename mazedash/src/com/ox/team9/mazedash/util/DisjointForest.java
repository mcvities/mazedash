package com.ox.team9.mazedash.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Disjoint sets implemented as a forest.
// Sets are represented as trees of Nodes, and the roots
// of the trees are the identifiers of the sets.
// This implementation uses path compression and union by rank.

public class DisjointForest<E> {

	// A map from elements to the node representing the element
	private final HashMap<E,Node<E>> forest = new HashMap<E,Node<E>>();
	
	// Creates a new singleton set containing the given element,
	// and adds it to the forest
	public void makeSet(E elem) {
		forest.put(elem, new Node<E>(elem, 0));
	}
	
	// Returns the set identifier for the given element, or null
	// if the element belongs to no set.
	public E find(E elem) {
		Node<E> node = forest.get(elem);
		if (node == null) 
			return null;
		if (elem != node.parent) 
			node.parent = find(node.parent);
		return node.parent;
	}
	
	// Unions the sets containing elem1 and elem2, does nothing
	// if they are in the same set or are not in the forest.
	public void union(E elem1, E elem2) {
		E set1 = find(elem1);
		E set2 = find(elem2);
		if (set1 == null || set2 == null || set1 == set2)
			return;
		Node<E> node1 = forest.get(set1);
		Node<E> node2 = forest.get(set2);
		// Make smaller tree's root a child of the larger tree's root
		if (node1.rank > node2.rank)
			node2.parent = set1;
		else {
			node1.parent = set2;
			if (node1.rank == node2.rank)
				node2.rank++;
		}
	}
	
	public void toList(List<E> list) {
		list.addAll(forest.keySet());
	}
	
	
	// Take a list of elements, and return them separated into a list of lists,
	// with disjoint elements being in different lists
	public List<List<E>> disjointElements(List<E> elems, List<List<E>> list) {
		HashMap<E,List<E>> lists = new HashMap<E,List<E>>();
		
		for (E elem : elems) {
			E key = find(elem);
					
			if (!lists.containsKey(key))
				lists.put(key, new ArrayList<E>());
			
			lists.get(key).add(elem);
		}
		list.addAll(lists.values());
		return list;
	}
	
	
	
	
	// Used to represent the nodes of the forest.
	private static class Node<T> {
		public int rank;
		public T parent;
		
		public Node(T parent, int rank) {
			this.parent = parent;
			this.rank = rank;
		}
	}
}
