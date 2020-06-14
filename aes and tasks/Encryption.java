class Encryption {

    private Keys key_;
    private important_func state;

    Encryption(String message, String key) {
        state = new important_func(message);
        key_ = new Keys(key);
    }

    void encrypt(){
        int[][] roundKey = key_.getRoundKey(0);

        state.addRoundKey(roundKey);

        for (int i = 1; i < 10; i++){

            state.subBytes();
            state.shiftRows();
            state.mixColumns();

            roundKey = key_.getRoundKey(i);
            state.addRoundKey(roundKey);
        }

        state.subBytes();
        state.shiftRows();


        roundKey = key_.getRoundKey(10);
        state.addRoundKey(roundKey);
        System.out.println(".");
        System.out.println(".");

        System.out.println("round_"+10 + state.printAsHexOneLine());
    }


}