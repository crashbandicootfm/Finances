package dev.crashbandicootfm;

import dev.crashbandicootfm.service.CommandLineBootstrap;

public class Main {

    private static final CommandLineBootstrap commandLineBootstrap = new CommandLineBootstrap();

    public static void main(String[] args) {
        commandLineBootstrap.boostrap();
    }
}