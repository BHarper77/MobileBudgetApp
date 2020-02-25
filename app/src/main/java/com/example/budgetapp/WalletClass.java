package com.example.budgetapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WalletClass implements Serializable{
    String walletName;
    int balance;

    //Constructor
    public WalletClass(String walletName, int balance) {
        this.walletName = walletName;
        this.balance = balance;
    }
    public String getWalletName() {
        return walletName;
    }

    public int getBalance() {
        return balance;
    }
}

class WalletList {
    List<WalletClass> walletList = new ArrayList<WalletClass>();

    //Adding new wallets to list
    public void addToList(WalletClass wallet) {
        walletList.add(wallet);
    }

    //Getter
    public List<WalletClass> getWalletList() {
        return walletList;
    }
}