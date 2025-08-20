package assignment1AI;

import java.util.*;

public class AStar {

    static final int N = 3;
    static int exploredNodes = 0;

    static class Node {
        int[][] board;
        int x, y;
        int level;  // g(n)
        int cost;   // f(n) = g(n) + h(n)
        Node parent;

        Node(int[][] board, int x, int y, int level, Node parent) {
            this.board = copy(board);
            this.x = x;
            this.y = y;
            this.level = level;
            this.parent = parent;
            this.cost = level + calculateHeuristic(this.board);
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

    static int calculateHeuristic(int[][] board) {
        int dist = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int val = board[i][j];
                if (val != 0) {
                    int targetX = (val - 1) / N;
                    int targetY = (val - 1) % N;
                    dist += Math.abs(i - targetX) + Math.abs(j - targetY);
                }
            }
        }
        return dist;
    }

    static void aStarSearch(int[][] start, int startX, int startY) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        Set<String> visited = new HashSet<>();

        Node root = new Node(start, startX, startY, 0, null);
        pq.offer(root);
        visited.add(serialize(start));

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        long explorationTime = 0; // track only node exploration time

        while (!pq.isEmpty()) {
            long nodeStart = System.nanoTime(); // start measuring time for this node

            Node current = pq.poll();
            exploredNodes++;

            if (isGoal(current.board)) {
                explorationTime += (System.nanoTime() - nodeStart);
                System.out.println("A*: Reached goal in " + current.level + " moves.");
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
                    // Swap tiles
                    newBoard[current.x][current.y] = newBoard[newX][newY];
                    newBoard[newX][newY] = 0;

                    String state = serialize(newBoard);
                    if (!visited.contains(state)) {
                        visited.add(state);
                        pq.offer(new Node(newBoard, newX, newY, current.level + 1, current));
                    }
                }
            }

            explorationTime += (System.nanoTime() - nodeStart);
        }

        System.out.println("No solution found using A*.");
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
        aStarSearch(start, startX, startY);
    }
}
