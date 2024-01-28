package dev.crashbandicootfm;

import dev.crashbandicootfm.bootstrap.CommandLineBootstrap;
import dev.crashbandicootfm.bootstrap.ReflectCommandLineBootstrap;

public class Main {

    private static final CommandLineBootstrap commandLineBootstrap = new ReflectCommandLineBootstrap();

    public static void main(String[] args) {
        commandLineBootstrap.bootstrap();
    }
}