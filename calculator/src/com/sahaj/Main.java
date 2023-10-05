package com.sahaj;

enum Operation {
    ADD, SUBTRACT, MULTIPLY, DIVIDE
}

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) throw new RuntimeException("Quitting because of invalid # of args");
        final Operation operation = Operation.valueOf(args[0].toUpperCase());
        final Calculator calculator = new Calculator();
        final Runner runner = new Runner();
        switch(operation) {
            case ADD -> runner.run(calculator::add);
            case SUBTRACT -> runner.run(calculator::subtract);
            case MULTIPLY -> runner.run(calculator::multiply);
            case DIVIDE -> runner.run(calculator::divide);
        }
    }
}