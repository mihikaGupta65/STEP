
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Pb5 {

    public static int[][] exitDistance(char[][] plan) {

        int rows = plan.length;
        int cols = plan[0].length;

        int[][] distance = new int[rows][cols];

        Queue<int[]> queue = new LinkedList<>();

        // Initialize all cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                if (plan[i][j] == '#') {
                    distance[i][j] = -1;
                } 
                else if (plan[i][j] == 'E') {
                    distance[i][j] = 0;
                    queue.add(new int[]{i, j});
                } 
                else {
                    distance[i][j] = -1;
                }
            }
        }

        // Directions: up, down, left, right
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // Multi-source BFS
        while (!queue.isEmpty()) {

            int[] current = queue.poll();

            int r = current[0];
            int c = current[1];

            for (int k = 0; k < 4; k++) {

                int nr = r + dr[k];
                int nc = c + dc[k];

                if (nr >= 0 && nr < rows &&
                    nc >= 0 && nc < cols &&
                    plan[nr][nc] == '.' &&
                    distance[nr][nc] == -1) {

                    distance[nr][nc] = distance[r][c] + 1;

                    queue.add(new int[]{nr, nc});
                }
            }
        }

        return distance;
    }

    public static void main(String[] args) {

        char[][] plan = {
            {'.', '#', '.', 'E'},
            {'.', '.', '.', '#'},
            {'E', '#', '.', '.'}
        };

        int[][] result = exitDistance(plan);

        System.out.println("Exit Distances:");

        for (int i = 0; i < result.length; i++) {
            System.out.println(Arrays.toString(result[i]));
        }
    }
}
