package com.example.budgetapp;

public class Wallet {
    String walletName;
    int balance;

    //Constructor
    public Wallet(String walletName, int balance) {
        this.walletName = walletName;
        this.balance = balance;
    }

    public String getWalletName(){
        return walletName;
    }

    public int getBalance() {
        return balance;
    }
}
