package gna;

import java.util.Arrays;
import java.util.Random;

public class Main {

	private static Comparable[] randomArray(int lengte, int bovengrens){
		Random randNumGenerator = new Random();
		Comparable[] x = new Comparable[lengte];
		for (int i=0; i<x.length; i++)
		{
			x[i] = (randNumGenerator.nextInt(bovengrens)+1);
		}
		return x;
	}


	/**
	 * Example main function.
	 * 
	 * You can replace this.
	 */
	public static void main(String[] args) {
		Comparable[] array = randomArray(1024000,100);
		System.out.println("Length = " + array.length);
		//System.out.println(java.util.Arrays.toString(array));
		
		QuickSort quickSort = new QuickSort();
		//InsertionSort insertionSort = new InsertionSort();
		long start = System.currentTimeMillis();
		System.out.println(quickSort.sort(array, 0, array.length-1) + " Quicksort");
		//System.out.println(insertionSort.sort(array) + " insertion sort");
		long end = System.currentTimeMillis();
		System.out.println((end - start) + " miliseconds");

	}
}
