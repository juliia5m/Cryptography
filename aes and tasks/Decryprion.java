class Decryprion {
    private important_func state;
    private Keys key_;

    Decryprion(String key, String cipherText){
        state = new important_func(cipherText);
        key_ = new Keys(key);
    }

    void decrypt(){

        int[][] roundKey = key_.getRoundKey(10);
        state.addRoundKey(roundKey);

        for (int i = 1; i < 10; i++){

            state.invShiftRows();

            state.invSubBytes();

            roundKey = key_.getRoundKey(10 - i);
            state.addRoundKey(roundKey);

            state.invMixCol();
        }

        state.invShiftRows();

        state.invSubBytes();

        roundKey = key_.getRoundKey(0);
        state.addRoundKey(roundKey);

        System.out.println(".");
        System.out.println(".");

        System.out.println("round_"+10+  state.printAsHexOneLine());

    }


}