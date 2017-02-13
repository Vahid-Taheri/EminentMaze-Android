package eminentdevs.maze;

import java.util.Stack;

public final class Maze {
    public Cell[][] Map;
    private byte cols, rows;
    private Cell First, End;
    public Cell[] Solution;
    public Cell[] Moves;

    public Cell getFirst() {
        return First;
    }

    public void setFirst(Cell f) {
        First = f;
    }

    public Cell getEnd() {
        return End;
    }

    public void setEnd(Cell e) {
        End = e;
    }

    public Maze(byte m, byte n) {
        if (m < 4) m = 4;
        if (m > 50) m = 50;

        if (n < 4) n = 4;
        if (n > 70) n = 70;

        cols = m;
        rows = n;
        Map = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Map[i][j] = new Cell(i, j);
            }
        }
    }

    public void createBlocks() {
        First = null;
        End = null;

        /* Make all of map block */
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Map[i][j].setStatus((byte) -1);
            }
        }

        Stack<Cell> path = new Stack<Cell>();
        Map[0][0].setStatus((byte) 0);
        path.push(Map[0][0]);
        while (path.size() != 0) {
            if (countNears(path.peek()) == 0) {
                path.pop();
                continue;
            }
            byte rand = (byte) (Math.random() * countNears(path.peek()));
            byte i = 0;
            if (checkSecondRight(path.peek())) {
                if (i == rand) {
                    Map[path.peek().getRow()][path.peek().getCol() + 1].setStatus((byte) 0);
                    Map[path.peek().getRow()][path.peek().getCol() + 2].setStatus((byte) 0);
                    path.push(Map[path.peek().getRow()][path.peek().getCol() + 2]);
                    continue;
                } else {
                    i++;
                }
            }
            if (checkSecondBottom(path.peek())) {
                if (i == rand) {
                    Map[path.peek().getRow() + 1][path.peek().getCol()].setStatus((byte) 0);
                    Map[path.peek().getRow() + 2][path.peek().getCol()].setStatus((byte) 0);
                    path.push(Map[path.peek().getRow() + 2][path.peek().getCol()]);
                    continue;
                } else {
                    i++;
                }
            }
            if (checkSecondLeft(path.peek())) {
                if (i == rand) {
                    Map[path.peek().getRow()][path.peek().getCol() - 1].setStatus((byte) 0);
                    Map[path.peek().getRow()][path.peek().getCol() - 2].setStatus((byte) 0);
                    path.push(Map[path.peek().getRow()][path.peek().getCol() - 2]);
                    continue;
                } else {
                    i++;
                }
            }
            if (checkSecondTop(path.peek())) {
                if (i == rand) {
                    Map[path.peek().getRow() - 1][path.peek().getCol()].setStatus((byte) 0);
                    Map[path.peek().getRow() - 2][path.peek().getCol()].setStatus((byte) 0);
                    path.push(Map[path.peek().getRow() - 2][path.peek().getCol()]);
                    continue;
                } else {
                    i++;
                }
            }

        }

    }

    public void createRandomBlocks(byte percent) {
        int i = 0;
        while (i < rows * cols * percent / 100) {
            byte row = (byte) (Math.random() * rows);
            byte col = (byte) (Math.random() * cols);
            if (Map[row][col].getStatus() == 0) {
                Map[row][col].setStatus((byte) -1);
                i++;
            }
        }
    }

    public void Solve() {
        Solution = null;
        Moves = null;
        Stack<Cell> solution = new Stack<Cell>();
        Stack<Cell> moves = new Stack<Cell>();

        First.setStatus((byte) 1);
        solution.push(First);
        while (Map[End.getRow()][End.getCol()].getStatus() != 1 && solution.size() != 0) {
            moves.push(Map[solution.peek().getRow()][solution.peek().getCol()]);
            if (checkRight(solution.peek())) {
                Map[solution.peek().getRow()][solution.peek().getCol() + 1].setStatus((byte) 1);
                solution.push(Map[solution.peek().getRow()][solution.peek().getCol() + 1]);
            } else if (checkBottom(solution.peek())) {
                Map[solution.peek().getRow() + 1][solution.peek().getCol()].setStatus((byte) 1);
                solution.push(Map[solution.peek().getRow() + 1][solution.peek().getCol()]);
            } else if (checkLeft(solution.peek())) {
                Map[solution.peek().getRow()][solution.peek().getCol() - 1].setStatus((byte) 1);
                solution.push(Map[solution.peek().getRow()][solution.peek().getCol() - 1]);
            } else if (checkTop(solution.peek())) {
                Map[solution.peek().getRow() - 1][solution.peek().getCol()].setStatus((byte) 1);
                solution.push(Map[solution.peek().getRow() - 1][solution.peek().getCol()]);
            } else {
                solution.pop();
            }
        }
        Solution = solution.toArray(new Cell[solution.size()]);
        Moves = moves.toArray(new Cell[moves.size()]);

    }

    public void Reset() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (Map[i][j].getStatus() == 1) {
                    Map[i][j].setStatus((byte) 0);
                }
            }
        }
        First = null;
        End = null;
    }


    private boolean checkSecondRight(Cell cur) {
        if (cur.getCol() + 2 < cols) {
            if (Map[cur.getRow()][cur.getCol() + 2].getStatus() == -1) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSecondBottom(Cell cur) {
        if (cur.getRow() + 2 < rows) {
            if (Map[cur.getRow() + 2][cur.getCol()].getStatus() == -1) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSecondLeft(Cell cur) {
        if (cur.getCol() - 2 >= 0) {
            if (Map[cur.getRow()][cur.getCol() - 2].getStatus() == -1) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSecondTop(Cell cur) {
        if (cur.getRow() - 2 >= 0) {
            if (Map[cur.getRow() - 2][cur.getCol()].getStatus() == -1) {
                return true;
            }
        }
        return false;
    }

    private byte countNears(Cell cur) {
        byte count = 0;
        if (checkSecondRight(cur)) {
            count++;
        }
        if (checkSecondBottom(cur)) {
            count++;
        }
        if (checkSecondLeft(cur)) {
            count++;
        }
        if (checkSecondTop(cur)) {
            count++;
        }

        return count;
    }

    private boolean checkRight(Cell cur) {
        if (cur.getCol() + 1 < cols) {
            if (Map[cur.getRow()][cur.getCol() + 1].getStatus() == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean checkBottom(Cell cur) {
        if (cur.getRow() + 1 < rows) {
            if (Map[cur.getRow() + 1][cur.getCol()].getStatus() == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean checkLeft(Cell cur) {
        if (cur.getCol() - 1 >= 0) {
            if (Map[cur.getRow()][cur.getCol() - 1].getStatus() == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTop(Cell cur) {
        if (cur.getRow() - 1 >= 0) {
            if (Map[cur.getRow() - 1][cur.getCol()].getStatus() == 0) {
                return true;
            }
        }
        return false;
    }
}
