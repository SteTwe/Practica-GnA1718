package gna;

import static org.junit.Assert.*;

import java.util.Random;

import libpract.SortingAlgorithm;

import org.junit.Test;

/**
 * Tests that test whether the implementations of the sorting algorithms do sort.
 */
public class SortingAlgorithmsTest {

	private Comparable[] randomArray(int lengte, int bovengrens){
		Random randNumGenerator = new Random();
        Comparable[] x = new Comparable[lengte];
        for (int i=0; i<x.length; i++)
        {
            x[i] = (randNumGenerator.nextInt(bovengrens)+1);
        }
        return x;
	}
	
	private Comparable[] testingArray = randomArray(25, 10);
	
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
}
