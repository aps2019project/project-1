package control;

import java.util.Scanner;

abstract class Handler {

    Scanner scanner = new Scanner(System.in);
    String command;

    abstract HandlerType handleCommands();
}

enum HandlerType { ACCOUNT, BATTLE, COLLECTION, MENU, SHOP}