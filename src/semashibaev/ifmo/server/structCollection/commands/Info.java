package semashibaev.ifmo.server.structCollection.commands;


import semashibaev.ifmo.server.CommandProcessing;

import java.time.LocalDateTime;


public class Info extends Command {

    public Info(CommandProcessing commandProcessing) {
        super(commandProcessing);
        this.helpInfo = "Prints all information about Movie collection";
        this.answer="";
    }


    @Override

    public void run() {
        this.answer="";
        int length = commandProcessing.struct.size();
        LocalDateTime DateInitialization = commandProcessing.struct.getLast().getCreationDate();
        this.answer = (" Linked List is type, " + length + " elements, " + DateInitialization);
    }

    @Override
    protected void checkArgs() throws Exception {
        if (this.args.size() != 0)
            this.answer = "**Too many arguments";
    }

    public String getAnswer() {
        return answer;
    }
}
