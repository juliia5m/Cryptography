class Keys {
    private int[][] sBox;
    private int[][] rconst;
    private int[][] roundKeys;

    // alternative function, have another in important_func.java
    private static byte galuaMult(byte one, byte two) {
        int p = 0 ;
        int bb ;
        for (int i = 0; i < 8; ++i) {
            if ((two & 1) == 1)
                p ^= one;
            bb = one & 0x80;
            one <<= 1;
            if (bb == 0x80)
                one ^= 0x1b;
            two >>= 1;
        }
        return (byte) (p % 256);
    }


    Keys(String cipherKey) {
        roundKeys = new int[4][44];


        int[] tmpArr = some_func.hexStringToIntArray(cipherKey.replaceAll("\\s+",""));
        int count = 0;

        for(int col = 0; col< 4; col++){
            for(int row = 0; row < 4; row ++){
                roundKeys[row][col] = tmpArr[count];
                count ++;
            }
        }



        //sBox initialization
        sBox = new int[16][16];
        String forsBox = "63cab7040953d051cd60e0e7ba70e18c 7c82fdc783d1efa30c8132c8783ef8a1 77c993232c00aa40134f3a3725b59889 7b7d26c31aedfb8fecdc0a6d2e66110d f2fa36181b2043925f22498d1c4869bf 6b593f966efc4d9d972a06d5a603d9e6 6f47f7055ab133384490244eb4f68e42 c5f0cc9aa05b85f517885ca9c60e9468 30ad3407526a45bcc446c26ce8619b41 01d4a5123bcbf9b6a7eed356dd351e99 67a2e580d6be02da7eb8acf47457872d 2baff1e2b3397f213d1462ea1fb9e90f fe9c71eb294a501064de91654b86ceb0 d7a4d827e34c3cff5d5e957abdc15554 ab7231b22f589ff3190be4ae8b1d28bb 76c0157584cfa8d273db79088a9edf16";

        tmpArr = some_func.hexStringToIntArray(forsBox.replaceAll("\\s+",""));
        count = 0;
        for(int col = 0; col< 16; col++){
            for(int row = 0; row < 16; row ++){
                sBox[row][col] = tmpArr[count];
                count ++;
            }
        }

        //Rconst initialization
        rconst = new int[4][10];
        String forrCon = "01000000 02000000 04000000 08000000 10000000 20000000 40000000 80000000 1b000000 36000000";

        tmpArr = some_func.hexStringToIntArray(forrCon.replaceAll("\\s+",""));
        count = 0;
        for(int col = 0; col< 10; col++){
            for(int row = 0; row < 4; row ++){
                rconst[row][col] = tmpArr[count];
                count ++;
            }
        }

        createRoundKeys();
    }


    private void createRoundKeys(){
        int[] temp = new int[4];
        int[] prevWord = new int[4];
        int[] xOr = new int[4];

        int count = 4; // start at 4th word
        while (count < 44){
            for(int row = 0; row < 4; row ++){
                temp[row] = roundKeys[row][count - 1];
            }

            if (count % 4 == 0){
                for(int row = 0; row < 4; row ++){
                    xOr[row] = rconst[row][count/4 - 1];
                }
                temp = XOR(subWord(rotateWord(temp)), xOr);
            }
            for(int row = 0; row < 4; row ++){
                prevWord[row] = roundKeys[row][count - 4];
            }
            temp = XOR(prevWord, temp);

            for(int row = 0; row < 4; row ++){
                roundKeys[row][count] = temp[row];
            }
            count++;
        }
    }

    private int[] rotateWord(int[] our_word){
        int[] var = new int[4];
        var[0] = our_word[1];
        var[1] = our_word[2];
        var[2] = our_word[3];
        var[3] = our_word[0];
        return var;
    }

    private int[] subWord(int[] word){
        int[] temp = new int[4];

        int Rows;
        int Cols;
        for(int i = 0; i < 4; i ++){

            String hex = Integer.toHexString(word[i]);
            if(hex.length() == 1){
                hex = "0"+ hex;
            }

            Rows = important_func.subBytesHelper(Character.toString(hex.charAt(0)));
            Cols = important_func.subBytesHelper(Character.toString(hex.charAt(1)));
            temp[i] = sBox[Rows][Cols];
        }
        return temp;
    }

    private int[] XOR(int[] word, int[] forXor){
        int[] var = new int[4];
        var[0] = word[0] ^ forXor[0];
        var[1] = word[1] ^ forXor[1];
        var[2] = word[2] ^ forXor[2];
        var[3] = word[3] ^ forXor[3];
        return var;
    }

    int[][] getRoundKey(int round){
        int[][] roundKey = new int[4][4];

        int index = (4*(round));
        int count = 0;
        for (int column = index; column < index + 4; column++){
            for (int row = 0; row < 4; row ++){
                roundKey[row][count] = roundKeys[row][column];
            }
            count++;
        }
        return roundKey;
    }

    String printHex(int round){
        int[][]roundKey ;
        roundKey = getRoundKey(round);

        StringBuilder str = new StringBuilder();
        for(int i = 0; i < 4; i++){
            int[] col = getColumn(i, roundKey);
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

    private int[] getColumn(int column, int[][] roundKey){
        int[] tmp = new int[4];
        for(int i=0; i< 4; i++){
            for(int j=0; j<4; j++){
                if(j == column){
                    tmp[i] = roundKey[i][j];
                }
            }
        }
        return tmp;
    }
}