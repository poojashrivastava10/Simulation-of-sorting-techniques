package SupportingClasses;

import myAlgorithms.QuickSort;
import myAlgorithms.InsertionSort;
import myAlgorithms.MergeSort;
import myAlgorithms.SelectionSort;
import myAlgorithms.BubbleSort;
import Core.SortAlgorithm;


public final class perform {
	private static final SortAlgorithm[] ALGORITHMS = {
		QuickSort.INSTANCE,
		MergeSort.INSTANCE,
		BubbleSort.INSTANCE,
		SelectionSort.INSTANCE,
		InsertionSort.INSTANCE,
	};
	
	private static int mySize = 500;
	
	public static void main(String[] args) {
		int size;
		if (args.length == 0)
			size = mySize;
		else if (args.length == 1)
			size = Integer.parseInt(args[0]);
		else {
			System.err.println("[ArraySize]");
			System.exit(1);
			return;
		}
		if (size <= 0)
			throw new IllegalArgumentException("We need positive number for array size");
		
		System.err.println("name\ncomparisons#\nswaps#");
		SortArray referenceArray = new SortArray(size);
		for (SortAlgorithm algo : ALGORITHMS) {
			SortArray array = referenceArray.clone();
			algo.sort(array);
			System.err.printf("%s\t%d\t%d%n", algo.getName(), array.diffNumber, array.changeNumber);
		}
	}
	
}
