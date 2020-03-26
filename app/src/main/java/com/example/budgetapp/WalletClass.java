package com.example.budgetapp;

import java.io.Serializable;

public class WalletClass implements Serializable
{
    String walletName;
    int balance;

    //Constructor
    public WalletClass(String walletName, int balance)
    {
        this.walletName = walletName;
        this.balance = balance;
    }

    public String getWalletName()
    {
        return walletName;
    }

    public int getBalance()
    {
        return balance;
    }
}