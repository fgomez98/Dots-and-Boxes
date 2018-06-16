public class Board {
    private Integer[][] boxBoard;
    private Integer[][] boxHorizontal;
    private Integer[][] boxVertical;

    public Board(int N) {
        boxBoard = new Integer[N-1][N-1];
        boxHorizontal = new Integer[N][N-1];
        boxVertical = new Integer[N-1][N];
    }


}
