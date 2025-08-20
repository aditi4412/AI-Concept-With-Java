package assignment1AI;

public class IsSolvable {

	static int get_count(int [] board){
		int inv_count = 0;
		for(int i =0 ; i<9 ; i++) {
			for(int j = 0 ; j<9; j++) {
				if(board[i] > 0 && board[j]> 0 && board[i]>board[j]) {
					inv_count++;
				}
			}
		}
		return inv_count;
		
	}
	
	static boolean isSolvable(int [][] arr) {
		int [] linearArr=new int[9];
		int k=0;
		for(int i = 0 ; i<3 ; i++) {
			for(int j=0;j<3 ; j++) {
				linearArr[k]=arr[i][j];
				k++;
			}
		}
		int invCt = get_count(linearArr);
		return (invCt % 2 ==0);
	}
	public static void main(String[] args) {
		 int[][] puzzle = {{1, 2, 3},{4, 5, 6},{7, 8, 0}};
		    if(isSolvable(puzzle))
		        System.out.println("Solvable");
		    else
		    System.out.println("Not Solvable");

	}

}
