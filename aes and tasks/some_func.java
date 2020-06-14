public class some_func {
    int[][] matrix;

    some_func(String input) {
        matrix = new int[4][4];

        int[] tmpArr = hexStringToIntArray(input.replaceAll("\\s+",""));
        int count = 0;

        for(int col = 0; col< 4; col++){
            for(int row = 0; row < 4; row ++){
                matrix[row][col] = tmpArr[count];
                count ++;
            }
        }
    }

    static int[] hexStringToIntArray(String s) {
        int len = s.length();
        int[] data = new int[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }


    //for output for tests
    String printAsHexOneLine(){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < 4; i++){
            int[] col = getColumn(i);
            for (int value : col) {
                String tmp = Integer.toHexString(value);
                if (tmp.length() == 1) {
                    tmp = "0" + tmp;
                }
                str.append(tmp);
            }
        }
        return str.toString();
    }




    private int getneeded(int row, int col){
        return matrix[row][col];
    }

    int[] getColumn(int column){
        int[] tmp = new int[4];
        for(int i=0; i< 4; i++){
            for(int j=0; j<4; j++){
                if(j == column){
                    tmp[i] = matrix[i][j];
                }
            }
        }
        return tmp;
    }

    void replaceCol(int col, int[] mixColResult) {
        for(int i=0; i< 4; i++){
            for(int j=0; j<4; j++){
                if(j == col){
                    matrix[i][j] = mixColResult[i];
                }
            }
        }
    }

    @Override
    public boolean equals(Object o){
        some_func other = (some_func) o;
        for(int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if(this.getneeded(row,col) !=
                        other.getneeded(row,col)){
                    return false;
                }
            }
        }
        return true;
    }


}