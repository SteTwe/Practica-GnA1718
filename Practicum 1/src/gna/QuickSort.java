
package gna;

/**
 * Performs sort by using the Quick Sort algorithm.
 *TODO mogelijk verwijderen abstract, zie na tests
 */
public abstract class QuickSort extends SortingAlgorithm{
	/**
	 * Sorts the given array using quick sort.
	 * 
	 * @see super
	 */
	public long sort(Comparable[] array, int low, int high) throws IllegalArgumentException {
		if (array == null) {
			throw new IllegalArgumentException("argument 'array' must not be null.");
		}
		if (high <= low) return 0;
		int k = partition(array, low, high);
		sort(array, low, k -1);
		sort(array, k + 1, high);		
		return counter;
	}
	
	static long counter = 0;
	
	private static int partition(Comparable[] array, int low, int high){
		int i = low + 1;
		int j = high;
		Comparable pivot = array[low];
		while (true){
			while (less(array[i++], pivot)){
				counter++;
				if (i == high) break;
			}
			
			while (less(array[j--], pivot)){
				counter++;
				if (j == low) break;
			}
			if (i>=j) break;
			swap(array, i, j);
		}
		swap(array, j, low);
		return j;
	}

	/**
	 * Constructor.
	 */
	public QuickSort() {
	}
}
