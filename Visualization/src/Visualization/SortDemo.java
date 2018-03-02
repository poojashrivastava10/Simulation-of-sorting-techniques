package Visualization;

import myAlgorithms.QuickSort;
import myAlgorithms.InsertionSort;
import myAlgorithms.MergeSort;
import myAlgorithms.SelectionSort;
import myAlgorithms.BubbleSort;
import java.util.Arrays;
import Core.SortAlgorithm;

public final class SortDemo {
	
	public static void main(String[] args) {
		SortAlgorithm[] algos = {
			BubbleSort.INSTANCE,
			SelectionSort.INSTANCE,
			MergeSort.INSTANCE,
			InsertionSort.INSTANCE,
			QuickSort.INSTANCE,
			
		};
		new LaunchFrame(Arrays.asList(algos));
	}
	
}
