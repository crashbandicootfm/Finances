package dev.crashbandicootfm;


import dev.crashbandicootfm.bootstrap.CommandLineBootstrap;
import dev.crashbandicootfm.bootstrap.ReflectCommandLineBootstrap;
import dev.crashbandicootfm.profile.Profile;

public class Main {

    private static final CommandLineBootstrap commandLineBootstrap = new ReflectCommandLineBootstrap();

    public static void main(String[] args) {
        Profile profile = null;
        commandLineBootstrap.bootstrap(null);
    }
}