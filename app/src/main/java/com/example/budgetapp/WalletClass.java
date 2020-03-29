package com.example.budgetapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WalletClass implements Serializable
{
    private String walletName;
    private double balance;
    public List<Transactions> transactions = new ArrayList<>();

    //Constructor
    public WalletClass(String walletName, double balance)
    {
        this.walletName = walletName;
        this.balance = balance;
    }

    //Public methods
    public String getWalletName()
    {
        return walletName;
    }

    public double getBalance()
    {
        return balance;
    }

    public class Transactions
    {
        private int id, type, amount;


        //Constructor
        public Transactions(int id, int type, int amount)
        {
            this.id = id;
            this.type = type;
            this.amount = amount;
        }

        //Public methods
        public int getId()
        {
            return id;
        }

        public int getType()
        {
            return type;
        }

        public int getAmount()
        {
            return amount;
        }
    }
}