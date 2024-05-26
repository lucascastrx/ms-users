package com.ms.users.domain.model;

public class Stake {
    private Long id;

    private String name;
    private double amount;

    private Wallet wallet;

    public Stake() {
    }

    public Stake(Long id, String name, double amount, Wallet wallet) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.wallet = wallet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
