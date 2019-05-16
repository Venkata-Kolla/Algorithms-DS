package dynamicProgramming;

import java.util.HashMap;
import java.util.Map;

public class FibonacciSeriesGeneration {
	//you can use array instead of hashmap!
	static Map<Integer, Integer> memo = new HashMap<>();
	public static void main(String[] args) {
		int result = fibonacciTopDown(4);
		System.out.println("result dp is:"+result);
		result = fibonacciBottomUp(4);
		System.out.println("result top down is:"+result);
	}

	/**
	 * uses DP but not very space efficient
	 * Time --> O(N) 
	 * Space --> O(N)
	 */
	private static int fibonacciTopDown(int n) {
		if(n <= 0)
			return 0;
		if(n == 1){
			return 1;
		}
		if(memo.containsKey(n))
			return memo.get(n);
		int result = fibonacciTopDown(n-1) + fibonacciTopDown(n-2);
		memo.put(n, result);
		return result;
	}
	
	/**
	 * more space efficient
	 * Time --> O(N) 
	 * Space --> O(1)
	 **/
	private static int fibonacciBottomUp(int n) {
		if(n == 0) return 0;
		if(n == 1) return 1;
		int i = 0, prev1 = 1, prev2 = 0, result = 0;
		while(i < n-2) {
			result = prev1 + prev2;
			prev2 = prev1;
			prev1 = result;
			i++;
		}
		return result;
	}
}
