package knapsack.solver;

import java.util.Arrays;

import knapsack.entities.Instance;
import knapsack.entities.Item;
import knapsack.entities.ItemPool;
import knapsack.entities.Knapsack;

public class KnapsackSolver {
	
	private static int[][] W;
	private static Instance instance;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ItemPool itemPool = new ItemPool(5);
		itemPool.addToPool(new Item(0, 2, 5), 0);
		itemPool.addToPool(new Item(1, 6, 9), 1);
		itemPool.addToPool(new Item(2, 5, 20), 2);
		itemPool.addToPool(new Item(3, 3, 12), 3);
		itemPool.addToPool(new Item(4, 4, 18), 4);
		Knapsack knapsack = new Knapsack();
		knapsack.setLimit(10);
		
		instance = new Instance(0, 5, itemPool, knapsack);
		initW(countMaxPrice(instance.getItemPool().getItems()),instance.getnSize());
		fillArray();
		printW();

	}
	
	private static void initW(int maxPrice, int instSize) {
		int rows = maxPrice + 1;
		int columns = instSize + 1;
		W = new int[rows][columns];
		
		for (int i = 1; i < W.length; i++) {
			W[i][0] = Integer.MAX_VALUE; // Set first column values to infinity
		}
	}
	
	private static void fillArray() {
		int ci1, wi1; // Possible problem
		
		for (int c = 1; c < W.length; c++) {
			for (int i = 1; i < W[c].length; i++) {
				ci1 = instance.getItemPool().getItems()[i-1].getPrice();
				wi1 = instance.getItemPool().getItems()[i-1].getWeight();
				W[c][i] = getArrayValue(i-1, c, ci1, wi1);
			}
		}
	}
	
	private static int getArrayValue(int i, int c, int ci1, int wi1) {
		int option1 = W[c][i];
		int option2;
		
		if (c-ci1 < 0 || W[c-ci1][i] == Integer.MAX_VALUE)
			option2 = Integer.MAX_VALUE;
		else
			option2 =  W[c-ci1][i] + wi1;
		
		return Math.min(option1, option2);
	}
	
	private static int countMaxPrice(Item[] items) {
		int maxPrice = 0;
		
		for (Item item : items) {
			maxPrice += item.getPrice();
		}
		
		return maxPrice;
	}
	
	private static void printW() {
		for (int i = W.length -1; i >= 0; i--) {
			System.out.println(Arrays.toString(W[i]));
		}
	}

}
