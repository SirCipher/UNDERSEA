package com.type2labs.undersea.controller.controllerDefault;


import com.type2labs.undersea.controller.controller.Executor;
import com.type2labs.undersea.controller.controller.Knowledge;

public class ExecutorDefault extends Executor {

    public ExecutorDefault(Knowledge knowledge) {
        super(knowledge);
    }

    @Override
    public void run() {
        command = "Dummy command";
    }
}
