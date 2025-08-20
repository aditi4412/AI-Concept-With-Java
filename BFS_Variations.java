package assignment1AI;

import java.util.*;

public class BFS_Variations {

    static final int N = 3;

    static class Node {
        int[][] board;
        int x, y;
        int level;
        Node parent;

        Node(int[][] b, int x, int y, int level, Node parent) {
            this.board = copy(b);
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

    static void bfs(int[][] start, int startX, int startY) {
        long explorationTime = 0; 

        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        Node root = new Node(start, startX, startY, 0, null);
        queue.offer(root);
        visited.add(serialize(start));

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        int exploredNodes = 0;

        while (!queue.isEmpty()) {
            long nodeStart = System.nanoTime(); 

            Node current = queue.poll();
            exploredNodes++;

            if (isGoal(current.board)) {
                explorationTime += (System.nanoTime() - nodeStart); 
                System.out.println("BFS: Reached goal in " + current.level + " moves.");
                System.out.println("Total explored nodes: " + exploredNodes);
                System.out.println("Time spent exploring nodes: " + (explorationTime / 1_000_000.0) + " ms");
                printPath(current);
                return;
            }

            for (int i = 0; i < 4; i++) {
                int newX = current.x + dx[i];
                int newY = current.y + dy[i];

                if (newX >= 0 && newX < N && newY >= 0 && newY < N) {
                    int[][] newBoard = copy(current.board);
                    // Swap
                    newBoard[current.x][current.y] = newBoard[newX][newY];
                    newBoard[newX][newY] = 0;

                    String state = serialize(newBoard);
                    if (!visited.contains(state)) {
                        visited.add(state);
                        queue.offer(new Node(newBoard, newX, newY, current.level + 1, current));
                    }
                }
            }

            explorationTime += (System.nanoTime() - nodeStart); 
        }

        System.out.println("No solution found using BFS.");
        System.out.println("Total explored nodes: " + exploredNodes);
        System.out.println("Time spent exploring nodes: " + (explorationTime / 1_000_000.0) + " ms");
    }

    public static void main(String[] args) {
        int[][] start = {
            {7, 2, 4},
            {5, 0, 6},
            {8, 3, 1}
        };

        int startX = 1, startY = 1;
        bfs(start, startX, startY);
    }
}
