package myAlgorithms;

import Core.AbstractSort;
import Core.SortAlgorithm;
import Core.SortArray;
import Core.SortArray.ElementState;

public final class MergeSort extends AbstractSort {
	
	public static SortAlgorithm INSTANCE = new MergeSort();
	
	
        @Override
	public void sort(SortArray array) {
		sort(array, 0, array.length());
		array.setRange(0, array.length(), ElementState.DONE);
	}
	
	
	private static void sort(SortArray array, int start, int end) {
		if (!(0 <= start && start <= end && end <= array.length()))
			throw new IllegalArgumentException();
		if (end - start <= 1)
			return;
		
		array.setRange(start, end, ElementState.ACTIVE);
		int mid = (start + end) / 2;
		array.setRange(mid, end, ElementState.INACTIVE);
		sort(array, start, mid);
		array.setRange(start, mid, ElementState.INACTIVE);
		sort(array, mid, end);
		merge(array, start, mid, end);
	}
	
	
	private static void merge(SortArray array, int start, int mid, int end) {
		if (!(0 <= start && start <= mid && mid <= end && end <= array.length()))
			throw new IllegalArgumentException();
		if (start == mid || mid == end)
			return;
		array.setRange(start, end, ElementState.ACTIVE);
		
		int left = mid - 1;
		int right = mid;
		while (start <= left && right < end && array.compare(left, right) > 0) {
			left--;
			right++;
		}
		
		int n = right - mid;
		for (int i = 0; i < n; i++)
			array.swap(mid - n + i, mid + i);
		
		array.setRange(mid, end, ElementState.INACTIVE);
		merge(array, start, left + 1, mid);
		array.setRange(start, mid, ElementState.INACTIVE);
		merge(array, mid, right, end);
	}
	
	
	private MergeSort() {
		super("Merge Sort");
	}
	
}
