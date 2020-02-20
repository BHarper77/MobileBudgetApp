package com.example.budgetapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Wallet implements Serializable{
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
