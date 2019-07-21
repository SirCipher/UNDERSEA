package com.type2labs.undersea.controller.controllerDefault;


import com.type2labs.undersea.controller.controller.Executor;

public class ExecutorDefault extends Executor {

    public ExecutorDefault() {
    }


    @Override
    public void run() {
        command = "Dummy command";
    }
}
