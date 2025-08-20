package assignment1AI;

import java.util.*;

class AStarHamming {
	    static final int N = 3;

	    static class Node {
	        int[][] board;
	        int x, y; 
	        int g;    
	        int h;    
	        Node parent;

	        Node(int[][] board, int x, int y, int g, Node parent) {
	            this.board = copy(board);
	            this.x = x;
	            this.y = y;
	            this.g = g;
	            this.h = hammingDistance(this.board);
	            this.parent = parent;
	        }

	        int f() {
	            return g + h;
	        }
	    }

	    
	    static int[][] goal = {
	        {1, 2, 3},
	        {4, 5, 6},
	        {7, 8, 0}
	    };

	    
	    static int[][] copy(int[][] src) {
	        int[][] copy = new int[N][N];
	        for (int i = 0; i < N; i++)
	            copy[i] = Arrays.copyOf(src[i], N);
	        return copy;
	    }

	    
	    static int hammingDistance(int[][] board) {
	        int dist = 0;
	        for (int i = 0; i < N; i++) {
	            for (int j = 0; j < N; j++) {
	                if (board[i][j] != 0 && board[i][j] != goal[i][j])
	                    dist++;
	            }
	        }
	        return dist;
	    }

	    
	    static boolean isGoal(int[][] board) {
	        return Arrays.deepEquals(board, goal);
	    }

	    
	    static void printBoard(int[][] board) {
	        for (int[] row : board) {
	            for (int val : row) {
	                System.out.print(val + " ");
	            }
	            System.out.println();
	        }
	        System.out.println();
	    }

	    
	    static void solve(int[][] start, int x, int y) {
	        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::f));
	        Set<String> visited = new HashSet<>();

	        Node root = new Node(start, x, y, 0, null);
	        pq.add(root);

	        int[][] moves = {{1,0}, {-1,0}, {0,1}, {0,-1}};

	        int exploredNodes = 0;

	        while (!pq.isEmpty()) {
	            Node current = pq.poll();
	            exploredNodes++;

	            if (isGoal(current.board)) {
	                System.out.println("Solution found!");
	                printPath(current);
	                System.out.println("Total nodes explored: " + exploredNodes);
	                return;
	            }

	            visited.add(Arrays.deepToString(current.board));

	            for (int[] move : moves) {
	                int newX = current.x + move[0];
	                int newY = current.y + move[1];

	                if (newX >= 0 && newX < N && newY >= 0 && newY < N) {
	                    int[][] newBoard = copy(current.board);
	                    // Swap blank
	                    newBoard[current.x][current.y] = newBoard[newX][newY];
	                    newBoard[newX][newY] = 0;

	                    if (!visited.contains(Arrays.deepToString(newBoard))) {
	                        Node child = new Node(newBoard, newX, newY, current.g + 1, current);
	                        pq.add(child);
	                    }
	                }
	            }
	        }

	        System.out.println("No solution found.");
	    }

	    
	    static void printPath(Node node) {
	        if (node == null) return;
	        printPath(node.parent);
	        printBoard(node.board);
	    }

	    public static void main(String[] args) {
	        int[][] start = {
	            {1, 2, 3},
	            {4, 0, 6},
	            {7, 5, 8}
	        };
	        int x = 1, y = 1; 
	        solve(start, x, y);
	    }
	

}
