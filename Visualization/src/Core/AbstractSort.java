/*

Java Code References:
Copyright (c) Project Nayuki
https://www.nayuki.io/page/sorting-algorithms-demo-java
(MIT License)
Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in 
the Software without restriction, including without limitation the rights to 
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies 
of the Software, and to permit persons to whom the Software is furnished to do 
so, subject to the following conditions: - The above copyright notice and this
permission notice shall be included in all copies or substantial portions of 
the Software. - The Software is provided "as is", without warranty of any kind,
express or implied, including but not limited to the warranties of merchantability,
fitness for a particular purpose and non-infringement. In no event shall the 
authors or copyright holders be liable for any claim, damages or other liability,
whether in an action of contract, tort or otherwise, arising from, out of or in
connection with the Software or the use or other dealings in the Software.

Other references:
https://xinok.wordpress.com/2014/08/17/in-place-merge-sort-demystified-2/
https://www.cs.cmu.edu/~adamchik/15-121/lectures/Sorting%20Algorithms/sorting.html
http://www.geeksforgeeks.org/sorting-algorithms/
*/


package Core;

import java.util.Objects;

public abstract class AbstractSort implements SortAlgorithm {
	private final String name;
	
	protected AbstractSort(String name) {
		Objects.requireNonNull(name);
		this.name = name;
	}
	public final String getName() {
		return name;
	}
}
