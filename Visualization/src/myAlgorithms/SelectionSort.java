package myAlgorithms;

import Core.AbstractSort;
import Core.SortAlgorithm;
import Core.SortArray;

public final class SelectionSort extends AbstractSort {
		public static final SortAlgorithm INSTANCE = new SelectionSort();
	
	
	public void sort(SortArray array) {
		int length = array.length();
		for (int i = 0; i < length; i++) {
			int minIndex = i;
			for (int j = i + 1; j < length; j++) {
				if (array.compare(j, minIndex) < 0)
					minIndex = j;
			}
			array.swap(i, minIndex);
			array.setElement(i, SortArray.ElementState.DONE);
		}
	}
	
	
	private SelectionSort() {
		super("Selection Sort");
	}
	
}
