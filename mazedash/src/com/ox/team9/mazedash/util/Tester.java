package com.ox.team9.mazedash.util;

import java.util.ArrayList;
import java.util.List;

public class Tester {

	
	public static void main(String[] args) {
		DisjointForest<Integer> forest = new DisjointForest<Integer>();
		for (int i = 0; i < 100; i++) 
			forest.makeSet(i);
		List<Integer> list = new ArrayList<Integer>();
		forest.toList(list);
		System.out.print(list);
		for (int i = 0; i < 100; i+=2) 
			forest.union(i,i+1);
		for (int i = 0; i < 100; i+=4) 
			forest.union(i,i+2);
		for (int i = 0; i < 100; i++)
			System.out.println(forest.find(i));
	}

}
