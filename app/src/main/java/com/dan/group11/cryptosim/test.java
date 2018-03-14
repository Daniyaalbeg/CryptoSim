package com.dan.group11.cryptosim ;

class test {
    public static void main(String[] args) {
        CoinMarketAPI cma = new CoinMarketAPI(10) ;

        String a =cma.getCoinData("bitcoin").getName() ;
        System.out.println(a) ;
        System.exit(0);
    }
}