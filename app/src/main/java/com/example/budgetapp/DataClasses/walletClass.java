package com.example.budgetapp.DataClasses;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class walletClass implements Serializable
{
    private String walletName;
    private double balance;
    private List<walletClass.transactions> transactions = new ArrayList<>();

    //Constructor
    public walletClass(String walletName, double balance, List<walletClass.transactions> transactions)
    {
        this.walletName = walletName;
        this.balance = balance;
        this.transactions = transactions;
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

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public void addTransaction(walletClass.transactions transaction)
    {
        this.transactions.add(transaction);

        Collections.sort(transactions);
        Collections.reverse(transactions);
    }

    public List<walletClass.transactions> getTransactions()
    {
        return transactions;
    }

    public static class transactions implements Serializable, Comparable<walletClass.transactions>
    {
        private double amount;
        private @Nullable String reference;
        private String type, date, time;
        private boolean recurring;

        //Constructor
        public transactions(String type, double amount, boolean recurring, @Nullable String reference, String date, String time)
        {
            this.type = type;
            this.amount = amount;
            this.recurring = recurring;
            this.reference = reference;
            this.date = date;
            this.time = time;
        }

        //Public methods
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

        @Nullable
        public String getReference()
        {
            return reference;
        }

        public String getDate()
        {
            return date;
        }

        public String getTime()
        {
            return time;
        }

        @Override
        public int compareTo(walletClass.transactions transaction)
        {
            return getTime().compareTo(transaction.getTime());
        }
    }
}