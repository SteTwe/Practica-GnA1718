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
		Comparable[] array = randomArray(65,10);
		System.out.println(array.length);
		InsertionSort insertionSort = new InsertionSort();
		System.out.println(insertionSort.sort(array) + " Insertion sort");

	}
}
