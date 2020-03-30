package com.example.budgetapp;

import androidx.annotation.Nullable;

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

    public static class Transactions
    {
        private int id;
        private double amount;
        private @Nullable String reference, type;
        private boolean recurring;


        //Constructor
        public Transactions(int id, String type, double amount, boolean recurring, @Nullable String reference)
        {
            this.id = id;
            this.type = type;
            this.amount = amount;
            this.recurring = recurring;
            this.reference = reference;
        }

        //Public methods
        public int getId()
        {
            return id;
        }

        public String getType()
        {
            return type;
        }

        public double getAmount()
        {
            return amount;
        }

        public boolean getRecurring()
        {
            return recurring;
        }

        public String getReference()
        {
            return reference;
        }

    }
}