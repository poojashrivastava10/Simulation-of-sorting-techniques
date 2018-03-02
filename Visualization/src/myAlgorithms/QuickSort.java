package myAlgorithms;

import Core.AbstractSort;
import Core.SortAlgorithm;
import Core.SortArray;

public final class QuickSort extends AbstractSort {
	
	public static final SortAlgorithm INSTANCE = new QuickSort();
	
	
	public void sort(SortArray array) {
		sort(array, 0, array.length());
	}
	
	
	private static void sort(SortArray array, int start, int end) {
		if (start == end)
			return;
		
		array.setRange(start, end, SortArray.ElementState.INACTIVE);
		int partition = start;
		int pivot = end - 1;  // Do not change this!
		for (int i = start; i < end - 1; i++) {
			if (array.compare(i, pivot) < 0) {
				array.swap(i, partition);
				array.setElement(partition, SortArray.ElementState.INACTIVE);
				partition++;
			}
		}
		
		array.swap(pivot, partition);
		pivot = partition;
		array.setElement(pivot, SortArray.ElementState.DONE);
		array.setRange(pivot + 1, end, SortArray.ElementState.INACTIVE);
		
		sort(array, start, pivot);
		sort(array, pivot + 1, end);
	}
	
	
	private QuickSort() {
		super("Quick Sort");
	}
	
}
