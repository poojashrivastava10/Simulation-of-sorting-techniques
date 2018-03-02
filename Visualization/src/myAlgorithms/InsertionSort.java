package myAlgorithms;

import Core.AbstractSort;
import Core.SortAlgorithm;
import Core.SortArray;

public final class InsertionSort extends AbstractSort {
	
	public static final SortAlgorithm INSTANCE = new InsertionSort();
	
	
	public void sort(SortArray array) {
		int length = array.length();
		array.setRange(0, length, SortArray.ElementState.INACTIVE);
		for (int i = 0; i < length; i++) {
			for (int j = i; j >= 1 && array.compareAndSwap(j - 1, j); j--);
		}
		array.setRange(0, length, SortArray.ElementState.DONE);
	}
	private InsertionSort() {
		super("Insertion Sort");
	}
	
}
