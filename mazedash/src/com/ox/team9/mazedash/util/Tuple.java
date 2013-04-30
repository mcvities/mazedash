package com.ox.team9.mazedash.util;

public class Tuple<X, Y> { 
	  public final X x; 
	  public final Y y; 
	  public Tuple(X x, Y y) { 
	    this.x = x; 
	    this.y = y; 
	  }
	  
	  boolean equals(Tuple<X,Y> t) {
		  return (t.x == x && t.y == y);
	  }
} 