package com.revauthore.tixtaxtoe;

/**
 * Created by Revauthore on 11/3/2015.
 */
public class GameManager {
    private char[][] board; // either X or O
    private char playerValue;
    private char compValue;
    private float compTimeElapsed;
    private int pieces;
    private Turn firstTurn;
    private Turn currentTurn;
    private int status; // 0 if the game is still ongoing, 1 if the player wins, 2 if the computer wins, 3 if it is a draw

    public GameManager(Turn turn) {
        board = new char[Game.BOARD_SIZE][Game.BOARD_SIZE];
        firstTurn = currentTurn = turn;
        if (turn == Turn.PLAYER) {
            playerValue = 'X';
            compValue = 'O';
        } else {
            compValue = 'X';
            playerValue = 'O';
        }
    }

    public void update(float dt) {
        if (status == 0 && currentTurn == Turn.COMPUTER) {
            compTimeElapsed += dt;
            if (compTimeElapsed >= Game.COMP_MOVE_DELAY) {
                findMove();
                compTimeElapsed = 0;
                currentTurn = Turn.PLAYER;
            }
        }
        checkGameStatus();
    }

    /**
     * Set the move of the player specified.
     *
     * @param x is the x-position of the placement
     * @param y is the y-position of the placement
     */
    public void setMove(int x, int y) {
        if (status == 0 && currentTurn == Turn.PLAYER && board[x][y] == 0) {
            board[x][y] = playerValue;
            ++pieces;
            currentTurn = Turn.COMPUTER;
        }
    }

    /**
     * Find the move for the computer.
     */
    public void findMove() {
        ++pieces;
        if (pieces == 2 && board[1][1] == 0) {
            board[1][1] = compValue;
            return;
        }
        if (pieces > 3) {
            for (int x = 0; x < board.length; x++) {
                //============== VERTICAL CHECK ===============
                // if the cell where i = 1 has the computer's symbol...
                if (board[1][x] == compValue) {
                    // if either i = 0 or i = 2 has the computer's symbol
                    // and the complement position (the complement of i = 0 is i = 2 and the other way around) is empty,
                    // put the computer's symbol there
                    if (board[0][x] == compValue && board[2][x] == 0) {
                        board[2][x] = compValue;
                        return;

                    }
                    if (board[2][x] == compValue && board[0][x] == 0) {
                        board[0][x] = compValue;
                        return;
                    }
                }
                // if the cell where both i = 0 and i = 2 has the computer's symbols,
                // and the cell at i = 1 is empty, put a symbol there
                if (board[1][x] == 0 && board[0][x] == compValue && board[2][x] == compValue) {
                    board[1][x] = compValue;
                    return;
                }
                //=============== HORIZONTAL CHECK ================
                // if the cell where the j = 1 has the computer's symbol...
                if (board[x][1] == compValue) {
                    // if either j = 0 or j = 2 has the computer's symbol
                    // and the complement position (the complement of j = 0 is j = 2 and the other way around) is empty,
                    // put the computer's symbol there
                    if (board[x][0] == compValue && board[x][2] == 0) {
                        board[x][2] = compValue;
                        return;

                    }
                    if (board[x][2] == compValue && board[x][0] == 0) {
                        board[x][0] = compValue;
                        return;
                    }
                }
                // if the cell where both j = 0 and j = 2 has the computer's symbols,
                // and the cell at j = 1 is empty, put a symbol there
                if (board[x][1] == 0 && board[x][0] == compValue && board[x][2] == compValue) {
                    board[x][1] = compValue;
                    return;
                }
                //================= DIAGONAL CHECK ==================
                if (board[1][1] == compValue) {
                    if (board[0][0] == compValue && board[2][2] == 0) {
                        board[2][2] = compValue;
                        return;
                    }
                    if (board[2][2] == compValue && board[0][0] == 0) {
                        board[0][0] = compValue;
                        return;
                    }
                    if (board[2][0] == compValue && board[0][2] == 0) {
                        board[0][2] = compValue;
                        return;
                    }
                    if (board[0][2] == compValue && board[2][0] == 0) {
                        board[2][0] = compValue;
                        return;
                    }
                }
                if (board[1][1] == 0) {
                    if (board[0][0] == compValue && board[2][2] == compValue) {
                        board[1][1] = compValue;
                        return;
                    }
                    if (board[2][0] == compValue && board[0][2] == compValue) {
                        board[1][1] = compValue;
                        return;
                    }
                }
            }
        }

        // the computer should prevent the player to win
        for (int x = 0; x < board.length; x++) {
            //============== VERTICAL CHECK ===============
            if (board[1][x] == playerValue) {
                if (board[0][x] == playerValue && board[2][x] == 0) {
                    board[2][x] = compValue;
                    return;
                }
                if (board[2][x] == playerValue && board[0][x] == 0) {
                    board[0][x] = compValue;
                    return;
                }
            }
            if (board[1][x] == 0 && board[0][x] == playerValue && board[2][x] == playerValue) {
                board[1][x] = compValue;
                return;
            }
            //=============== HORIZONTAL CHECK ================
            if (board[x][1] == playerValue) {
                if (board[x][0] == playerValue && board[x][2] == 0) {
                    board[x][2] = compValue;
                    return;

                }
                if (board[x][2] == playerValue && board[x][0] == 0) {
                    board[x][0] = compValue;
                    return;
                }
            }
            if (board[x][1] == 0 && board[x][0] == playerValue && board[x][2] == playerValue) {
                board[x][1] = compValue;
                return;
            }
            //================= DIAGONAL CHECK ==================
            if (board[1][1] == playerValue) {
                if (board[0][0] == playerValue && board[2][2] == 0) {
                    board[2][2] = compValue;
                    return;
                }
                if (board[2][2] == playerValue && board[0][0] == 0) {
                    board[0][0] = compValue;
                    return;
                }
                if (board[2][0] == playerValue && board[0][2] == 0) {
                    board[0][2] = compValue;
                    return;
                }
                if (board[0][2] == playerValue && board[2][0] == 0) {
                    board[2][0] = compValue;
                    return;
                }
            }
            if (board[1][1] == 0) {
                if (board[0][0] == playerValue && board[2][2] == playerValue) {
                    board[1][1] = compValue;
                    return;
                }
                if (board[2][0] == playerValue && board[0][2] == playerValue) {
                    board[1][1] = compValue;
                    return;
                }
            }
        }

        // attempt to fill the corner
        if (board[0][0] == compValue && board[2][2] == compValue) {
            if (board[0][2] == 0 && board[0][1] == 0 && board[1][2] == 0) {
                board[0][2] = compValue;
                return;
            }
            if (board[2][0] == 0 && board[1][0] == 0 && board[2][1] == 0) {
                board[2][0] = compValue;
                return;
            }
        }
        if (board[2][0] == compValue && board[0][2] == compValue) {
            if (board[0][0] == 0 && board[0][1] == 0 && board[1][0] == 0) {
                board[0][0] = compValue;
                return;
            }
            if (board[2][2] == 0 && board[1][2] == 0 && board[2][1] == 0) {
                board[2][2] = compValue;
                return;
            }
        }
        if (board[0][0] == compValue && board[2][2] == 0) {
            board[2][2] = compValue;
            return;
        }
        if (board[2][2] == compValue && board[0][0] == 0) {
            board[0][0] = compValue;
            return;
        }
        if (board[0][2] == compValue && board[2][0] == 0) {
            board[2][0] = compValue;
            return;
        }
        if (board[2][0] == compValue && board[0][2] == 0) {
            board[0][2] = compValue;
            return;
        }
        if (board[0][0] == 0) {
            board[0][0] = compValue;
            return;
        }
        if (board[2][0] == 0) {
            board[2][0] = compValue;
            return;
        }
        if (board[0][2] == 0) {
            board[0][2] = compValue;
            return;
        }
        if (board[2][2] == 0) {
            board[2][2] = compValue;
            return;
        }

        // if all the corners has been already filled,
        // try to to fill the rest empty slot
        if (board[1][0] == 0) {
            board[1][0] = compValue;
            return;
        }
        if (board[0][1] == 0) {
            board[0][1] = compValue;
            return;
        }
        if (board[1][2] == 0) {
            board[1][2] = compValue;
            return;
        }
        if (board[2][1] == 0) {
            board[2][1] = compValue;
            return;
        }
        if (board[1][1] == 0) {
            board[1][1] = compValue;
            return;
        }
    }

    public void checkGameStatus() {
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[2][2] == compValue) {
            status = 2;
            return;
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[2][2] == playerValue) {
            status = 1;
            return;
        }
        if (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[0][2] == compValue) {
            status = 2;
            return;
        }
        if (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[0][2] == playerValue) {
            status = 1;
            return;
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][2] == compValue) {
                status = 2;
                return;
            }
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][2] == playerValue) {
                status = 1;
                return;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[2][i] == compValue) {
                status = 2;
                return;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[2][i] == playerValue) {
                status = 1;
                return;
            }
        }

        // if it reaches this step, check if it is a tie
        boolean isDraw = true;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    isDraw = false;
                    break;
                }
            }
        }
        if (isDraw) {
            status = 3;
        }
    }

    public boolean isGameOver() {
        return status > 0;
    }

    public int getStatus() {
        return status;
    }

    public char[][] getBoard() {
        return board;
    }

    public Turn getFirstTurn() {
        return firstTurn;
    }

    public enum Turn {PLAYER, COMPUTER}
}
