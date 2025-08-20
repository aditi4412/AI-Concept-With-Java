package assignment1AI;

import java.util.*;

public class DFS_Variations {

    static final int N = 3;
    static int exploredNodes = 0; 
    static long explorationTime = 0; 
    
    static class Node {
        int[][] board;
        int x, y;
        int level;
        Node parent;

        Node(int[][] board, int x, int y, int level, Node parent) {
            this.board = copy(board);
            this.x = x;
            this.y = y;
            this.level = level;
            this.parent = parent;
        }
    }

    static int[][] goal = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 0}
    };

    static int[][] copy(int[][] board) {
        int[][] result = new int[N][N];
        for (int i = 0; i < N; i++)
            result[i] = board[i].clone();
        return result;
    }

    static String serialize(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board)
            for (int val : row)
                sb.append(val);
        return sb.toString();
    }

    static void printPath(Node node) {
        if (node == null) return;
        printPath(node.parent);
        for (int[] row : node.board)
            System.out.println(Arrays.toString(row));
        System.out.println();
    }

    static boolean isGoal(int[][] board) {
        return Arrays.deepEquals(board, goal);
    }

    static boolean dfs(Node node, Set<String> visited, int limit) {
        long nodeStart = System.nanoTime(); 

        exploredNodes++; 

        if (isGoal(node.board)) {
            explorationTime += (System.nanoTime() - nodeStart); 
            System.out.println("DFS: Reached goal in " + node.level + " moves.");
            System.out.println("Total explored nodes: " + exploredNodes); 
            System.out.println("Time spent exploring nodes: " + (explorationTime / 1_000_000.0) + " ms");
            printPath(node);
            return true;
        }

        if (node.level > limit) {
            explorationTime += (System.nanoTime() - nodeStart);
            return false;
        }

        String state = serialize(node.board);
        visited.add(state);

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        for (int i = 0; i < 4; i++) {
            int newX = node.x + dx[i];
            int newY = node.y + dy[i];

            if (newX >= 0 && newX < N && newY >= 0 && newY < N) {
                int[][] newBoard = copy(node.board);
                newBoard[node.x][node.y] = newBoard[newX][newY];
                newBoard[newX][newY] = 0;

                String newState = serialize(newBoard);
                if (!visited.contains(newState)) {
                    Node child = new Node(newBoard, newX, newY, node.level + 1, node);
                    if (dfs(child, visited, limit)) {
                        explorationTime += (System.nanoTime() - nodeStart);
                        return true;
                    }
                }
            }
        }

        explorationTime += (System.nanoTime() - nodeStart);
        return false;
    }

    public static void main(String[] args) {
        int[][] start = {
            {7, 2, 4},
            {5, 0, 6},
            {8, 3, 1}
        };
        int startX = 1, startY = 1;

        Node root = new Node(start, startX, startY, 0, null);
        Set<String> visited = new HashSet<>();

        int depthLimit = 35;

        boolean found = dfs(root, visited, depthLimit);

        if (!found) {
            System.out.println("No solution found using DFS.");
            System.out.println("Total explored nodes: " + exploredNodes);
            System.out.println("Time spent exploring nodes: " + (explorationTime / 1_000_000.0) + " ms");
        }
    }
}
