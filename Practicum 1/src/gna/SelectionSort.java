package gna;

/**
 * Performs sort by using the Selection Sort algorithm.
 * 
 */
public class SelectionSort extends SortingAlgorithm {
	/**
	 * Sorts the given array using selection sort.
	 * 
	 * @see super
	 */
	public long sort(Comparable[] array) throws IllegalArgumentException {
		if (array == null) {
			throw new IllegalArgumentException("argument 'array' must not be null.");
		}
		long counter = 0;
		for (int i = 0; i < array.length; i++ ){
            int t = i;
            for (int j = i+1; j < array.length; j++){
            	counter ++;
                if (less(array[j], array[t])){
                    t = j;
                }
            }
            swap(array, i, t);
        }
		return counter;
	}

	/**
	 * Constructor.
	 */
	public SelectionSort() {
	}
}
