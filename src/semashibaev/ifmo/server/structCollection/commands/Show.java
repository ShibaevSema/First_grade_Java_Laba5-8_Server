package semashibaev.ifmo.server.structCollection.commands;

import semashibaev.ifmo.server.CommandProcessing;

import java.util.Collections;

public class Show extends Command {

    public Show(CommandProcessing commandProcessing) {
        super(commandProcessing);
        this.helpInfo = "Prints all Movie elements in collection";
        this.answer="";
    }

    @Override
    public void run() {
        if(!(this.commandProcessing.struct.isEmpty())) {
            this.answer = "";
            Collections.sort(this.commandProcessing.struct);
            this.commandProcessing.struct.forEach(movie -> this.answer += movie.toString() + "\n");
        } else {
            this.answer="No elements";
        }
    }


    @Override
    protected void checkArgs() throws Exception {
        if (this.args.size() != 0)
            throw new Exception("**Too many arguments");
    }
}
