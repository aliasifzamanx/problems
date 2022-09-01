package com.problem.problem;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

	private static Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@RequestMapping("/search")
	int search(@RequestBody Payload payload) {
		logger.info("Received payload: Key=" + payload.key + "  Array=" + Arrays.toString(payload.array));
		return pivotedBinarySearch(payload.array, payload.key);
	}

	/*
	 * First find the pivot then divide the array into two sub-arrays and perform a binary search.
	 * For a sorted (in increasing order) and rotated array, the pivot element
	 * is the only element for which the next element to it is smaller than it.
	 * Using binary search based on the above idea, pivot can be found.
	 * After the pivot is found divide the array into two sub-arrays.
	 * Now the individual sub-arrays are sorted so the element can be searched using Binary Search.
	 */
	private int pivotedBinarySearch(int arr[], int key) {
		int n = arr.length;
		int pivot = findPivot(arr, 0, n - 1);


		if (pivot == -1)
			return binarySearch(arr, 0, n - 1, key);

		if (arr[pivot] == key)
			return pivot;
		if (arr[0] <= key)
			return binarySearch(arr, 0, pivot - 1, key);
		return binarySearch(arr, pivot + 1, n - 1, key);
	}

	
	static int findPivot(int arr[], int low, int high) {

		if (high < low)
			return -1;
		if (high == low)
			return low;

		int mid = (low + high) / 2;
		if (mid < high && arr[mid] > arr[mid + 1])
			return mid;
		if (mid > low && arr[mid] < arr[mid - 1])
			return (mid - 1);
		if (arr[low] >= arr[mid])
			return findPivot(arr, low, mid - 1);
		return findPivot(arr, mid + 1, high);
	}

	static int binarySearch(int arr[], int low, int high, int key) {
		if (high < low)
			return -1;

		int mid = (low + high) / 2;
		if (key == arr[mid])
			return mid;
		if (key > arr[mid])
			return binarySearch(arr, (mid + 1), high, key);
		return binarySearch(arr, low, (mid - 1), key);
	}

}

class Payload {
	public int key;
	public int array[];
}
