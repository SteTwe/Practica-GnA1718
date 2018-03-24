package gna;

import static org.junit.Assert.*;

import java.util.Random;

import libpract.SortingAlgorithm;

import org.junit.Test;

/**
 * Tests that test whether the implementations of the sorting algorithms do sort.
 */
public class SortingAlgorithmsTest {

	private Comparable[] testingArray = {6, 5, 2, 3, 4, 4, 5, 12, 15, 12, 7,  8, 2 ,3 ,9 };
	
	@Test
	public void selectionSortSorted() {
		SelectionSort sort = new SelectionSort();
		//Sort array with build-in sorting
		Comparable[] arrayJava = testingArray.clone();
		java.util.Arrays.sort(arrayJava);		
		//Sort array with own algorithm
		Comparable[] arrayOwn = testingArray.clone();
		sort.sort(arrayOwn);
		//Check if not null
		assertNotNull(arrayOwn);
		//Check if they are the same
		assertArrayEquals(arrayJava, arrayOwn);		
	}
	
	
	
	@Test
	public void insertionSortSorted() {
		InsertionSort sort = new InsertionSort();
		//Sort array with build-in sorting
		Comparable[] arrayJava = testingArray.clone();
		java.util.Arrays.sort(arrayJava);		
		//Sort array with own algorithm
		Comparable[] arrayOwn = testingArray.clone();
		sort.sort(arrayOwn);
		//Check if not null
		assertNotNull(arrayOwn);
		//Check if they are the same
		assertArrayEquals(arrayJava, arrayOwn);		
	}
	
	@Test
	public void quickSortSorted() {
		QuickSort sort = new QuickSort();
		//Sort array with build-in sorting
		Comparable[] arrayJava = testingArray.clone();
		java.util.Arrays.sort(arrayJava);		
		//Sort array with own algorithm
		Comparable[] arrayOwn = testingArray.clone();
		sort.sort(arrayOwn, 0, arrayOwn.length -1);
		//Check if not null
		assertNotNull(arrayOwn);
		//Check if they are the same
		assertArrayEquals(arrayJava, arrayOwn);		
	}
	
	public static void main(){
		int amount = 20;
		int bovengrens= 50;
		Comparable[] array = new Comparable[amount];
		Random random = new Random();
		for (int i=0; i<amount; i++){
			Comparable element = random.nextInt(bovengrens);
			array[i] = element;
		}
		System.out.println(java.util.Arrays.toString(array));
	}


	

}
