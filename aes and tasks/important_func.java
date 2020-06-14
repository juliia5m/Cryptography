import java.util.Arrays;

class important_func extends some_func {


    private int[][] s_Box;

    //Inverse sBox;
    private int[][] invSBox;

    important_func(String input) {
        super(input);


        s_Box = new int[16][16];
        String sBoxStr = "63cab7040953d051cd60e0e7ba70e18c 7c82fdc783d1efa30c8132c8783ef8a1 77c993232c00aa40134f3a3725b59889 7b7d26c31aedfb8fecdc0a6d2e66110d f2fa36181b2043925f22498d1c4869bf 6b593f966efc4d9d972a06d5a603d9e6 6f47f7055ab133384490244eb4f68e42 c5f0cc9aa05b85f517885ca9c60e9468 30ad3407526a45bcc446c26ce8619b41 01d4a5123bcbf9b6a7eed356dd351e99 67a2e580d6be02da7eb8acf47457872d 2baff1e2b3397f213d1462ea1fb9e90f fe9c71eb294a501064de91654b86ceb0 d7a4d827e34c3cff5d5e957abdc15554 ab7231b22f589ff3190be4ae8b1d28bb 76c0157584cfa8d273db79088a9edf16";

        int[] tmpArr = hexStringToIntArray(sBoxStr.replaceAll("\\s+",""));
        int count = 0;
        for(int col = 0; col< 16; col++){
            for(int row = 0; row < 16; row ++){
                s_Box[row][col] = tmpArr[count];
                count ++;
            }
        }


        invSBox = new int[16][16];
        count = 0;
        String hex;
        int newRow;
        int newCol;
        for(int col = 0; col< 16; col++){
            for(int row = 0; row < 16; row ++){
                hex = Integer.toHexString(tmpArr[count]);
                if(hex.length() == 1){
                    hex = "0"+ hex;
                }

                newRow = Integer.parseInt(String.valueOf(hex.charAt(0)),16);
                newCol = Integer.parseInt(String.valueOf(hex.charAt(1)),16);

                hex = Integer.toHexString(row) + Integer.toHexString(col);

                invSBox[newRow][newCol] = Integer.parseInt(hex, 16);
                count ++;
            }
        }
    }

    void addRoundKey(int[][] roundKey){
        for (int col = 0; col < 4; col++){
            for (int row = 0; row < 4; row++){
                matrix[row][col] = matrix[row][col] ^ roundKey[row][col];
            }
        }
    }

    void shiftRows(){

        for(int row = 1; row < matrix.length; row++){
            int[] tmp = Arrays.copyOf(matrix[row],4);

            for(int col = 0; col < matrix[0].length; col++){
                matrix[row][(col+(matrix[0].length - row)) % matrix.length ] = tmp[col];
            }
        }
    }

    void subBytes(){

        int sBoxRow; int sBoxCol;
        for(int col = 0; col< 4; col++){
            for(int row = 0; row < 4; row ++){

                String hexVal = Integer.toHexString(matrix[row][col]);
                if(hexVal.length() == 1){
                    hexVal = "0"+ hexVal;
                }

                sBoxRow = subBytesHelper(Character.toString(hexVal.charAt(0)));
                sBoxCol = subBytesHelper(Character.toString(hexVal.charAt(1)));
                matrix[row][col] = s_Box[sBoxRow][sBoxCol];
            }
        }
    }

    void mixColumns(){
        //Matrix multiplication
        for(int i = 0; i<4;i++){
            int[] col = super.getColumn(i);
            int[] mixColResult = mixColumnsHelper(col);
            super.replaceCol(i, mixColResult);
        }
    }

    private int[] mixColumnsHelper(int[] col){
        int[] resultingCol = new int[4];

        resultingCol[0] = reduce(GFMult(2,col[0]) ^ GFMult(3,col[1]) ^ col[2] ^ col[3]);
        resultingCol[1] = reduce(col[0] ^ GFMult(2,col[1]) ^ GFMult(3,col[2]) ^ col[3]);
        resultingCol[2] = reduce(col[0] ^ col[1] ^ GFMult(2,col[2]) ^ GFMult(3,col[3]));
        resultingCol[3] = reduce(GFMult(3,col[0]) ^ col[1] ^ col[2] ^ GFMult(2,col[3]));

        return resultingCol;
    }

    private int GFMult(int x, int y){

        String yStr = Integer.toBinaryString(y);

        int posVal = -1;
        int count = 0;
        int numBeforeMod = -1;
        for(int i = yStr.length()-1; i>= 0; i--){
            if(yStr.charAt(i) == '1'){
                if(posVal == -1){
                    posVal = (int)Math.pow(2,count) * x;
                    numBeforeMod = posVal;
                }else{
                    posVal = (int)Math.pow(2,count) * x;
                    numBeforeMod = numBeforeMod ^ posVal;
                }
            }
            count++;
        }
        if (numBeforeMod == -1){
            numBeforeMod = 0;
        }
        return reduce(numBeforeMod);
    }

    private int reduce(int x){

        int dividend = x;
        int divisor = 283;

        while(dividend > 255){
            String curDiv = Integer.toBinaryString(dividend);
            String firstPart = curDiv.substring(0,9);
            String secondPart = curDiv.substring(9);

            int f = Integer.parseInt(firstPart, 2);
            int xor = f ^ divisor;

            String xorPart = Integer.toBinaryString(xor);
            String combined = xorPart + secondPart;
            dividend = Integer.parseInt(combined, 2);
        }
        return dividend;
    }

    static int subBytesHelper(String s){
        switch (s){
            case "0":
                return 0;
            case "1":
                return 1;
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            case "8":
                return 8;
            case "9":
                return 9;
            case "a":
                return 10;
            case "b":
                return 11;
            case "c":
                return 12;
            case "d":
                return 13;
            case "e":
                return 14;
            case "f":
                return 15;
        }
        return -1;
    }

    void invSubBytes(){
        int invSBoxRow; int invSBoxCol;
        for(int col = 0; col< 4; col++){
            for(int row = 0; row < 4; row ++){

                String hexVal = Integer.toHexString(matrix[row][col]);
                if(hexVal.length() == 1){
                    hexVal = "0"+ hexVal;
                }

                invSBoxRow = subBytesHelper(Character.toString(hexVal.charAt(0)));
                invSBoxCol = subBytesHelper(Character.toString(hexVal.charAt(1)));

                matrix[row][col] = invSBox[invSBoxRow][invSBoxCol];
            }
        }
    }

    void invShiftRows(){

        for(int row = 1; row < matrix.length; row++){
            int[] tmp = Arrays.copyOf(matrix[row],4);

            for(int col = 0; col < matrix[0].length; col++){
                matrix[row][(col+(matrix[0].length + row)) % matrix.length ] = tmp[col];
            }
        }
    }

    void invMixCol(){

        for(int i = 0; i<4;i++){
            int[] col = super.getColumn(i);
            int[] mixColResult =  Helper(col);
            super.replaceCol(i, mixColResult);
        }
    }

    private int[]  Helper(int[] col){
        int[] res  = new int[4];

        res[0] = reduce(GFMult(14,col[0]) ^ GFMult(11,col[1]) ^ GFMult(13,col[2]) ^ GFMult(9,col[3]));
        res[1] = reduce(GFMult(9,col[0]) ^ GFMult(14,col[1]) ^ GFMult(11,col[2]) ^ GFMult(13,col[3]));
        res[2] = reduce(GFMult(13,col[0]) ^ GFMult(9,col[1]) ^ GFMult(14,col[2]) ^ GFMult(11,col[3]));
        res[3] = reduce(GFMult(11,col[0]) ^ GFMult(13,col[1]) ^ GFMult(9,col[2]) ^ GFMult(14,col[3]));
        return res ;
    }
}