package myAlgorithms;

import Core.AbstractSort;
import Core.SortAlgorithm;
import Core.SortArray;

public final class BubbleSort extends AbstractSort {
	
	public static final SortAlgorithm INSTANCE = new BubbleSort();
	
	
	public void sort(SortArray array) {
		for (int i = array.length(); i >= 1; i--) {
			for (int j = 0; j < i - 1; j++)
				array.compareAndSwap(j, j + 1);
			array.setElement(i - 1, SortArray.ElementState.DONE);
		}
	}
	private BubbleSort() {
		super("Bubble Sort");
	}
	
}
