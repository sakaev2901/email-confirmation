package ru.itis.config;

public class Test {
    public static void main(String[] args) {
        Sender sender = new Sender("sakaev2901@gmail.com", "Elvin2901");
        sender.send("Test", "Message", "sakaev2901@gmail.com", "sakaev000@bk.ru");
    }
}
