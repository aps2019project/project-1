package control;

import java.util.Scanner;

abstract class Handler {

    Scanner scanner = new Scanner(System.in);
    String command;

    abstract void handleCommands();
}
