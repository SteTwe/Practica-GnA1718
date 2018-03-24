package gna;

/**
 * Performs sort by using the Insertion Sort algorithm.
 * 
 */
public class InsertionSort extends SortingAlgorithm {
	/**
	 * Sorts the given array using insertion sort.
	 * 
	 * @see super
	 */
	public long sort(Comparable[] array) throws IllegalArgumentException {
		if (array == null) {
			throw new IllegalArgumentException("argument 'array' must not be null.");
		}
		long counter = 0;
		for (int i = 1; i < array.length; i++){
            for (int j = i; j > 0;){
            	counter++;
                if (less(array[j], array[j-1])){
                    swap(array, j, j-1);
                    j--;                    
                }
                else j++;
                if (j >= array.length) break;
            }
        }
		return counter;
	}

	/**
	 * Constructor.
	 */
	public InsertionSort() {
	}
}
