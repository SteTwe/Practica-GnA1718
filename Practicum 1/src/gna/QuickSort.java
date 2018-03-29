
package gna;

/**
 * Performs sort by using the Quick Sort algorithm.
 *
 */
public class QuickSort extends SortingAlgorithm{
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
		int i = low;
        int j = high +1;
        Comparable pivot = array[low];
        while (true){
            while (less(array[++i], pivot)){
                counter++;
                if (i == high) break;
            }
            counter++;
            while (less(pivot, array[--j])){
                counter++;
                if (j == low) break;
            }
            counter++;
			if (i >= j) break;
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

	@Override
	public long sort(Comparable[] array) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}
}
