package com.sahaj;

enum Operation {
    ADD, SUBTRACT, MULTIPLY, DIVIDE
}

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) throw new RuntimeException("Quitting because of invalid # of args");
        final Operation operation = Operation.valueOf(args[0].toUpperCase());
        final int arity = Integer.parseInt(args[1]);
        final Calculator calculator = new Calculator();
        final OperationVarArgs op = switch (operation) {
            case ADD -> calculator::add;
            case SUBTRACT -> calculator::subtract;
            case MULTIPLY -> calculator::multiply;
            case DIVIDE -> calculator::divide;
        };
        final Runner runner = new Runner(arity);
        runner.run(op);
    }
}