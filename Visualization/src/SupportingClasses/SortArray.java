package SupportingClasses;

import Core.AbstractSortArray;


final class SortArray extends AbstractSortArray implements Cloneable {
	public long diffNumber;
	public long changeNumber;
	
	public SortArray(int size) {
		super(size);
		shuffle();
		diffNumber = 0;
		changeNumber = 0;
	}
	
	public int compare(int i, int j) {
		diffNumber++;
		return super.compare(i, j);
	}
	
	
	public void swap(int i, int j) {
		changeNumber++;
		super.swap(i, j);
	}
	
	
	public SortArray clone() {
		try {
			SortArray result = (SortArray)super.clone();
			result.values = result.values.clone();
			return result;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(e);
		}
	}
	
}
