package semashibaev.ifmo.server.structCollection.commands;

import semashibaev.ifmo.server.CommandProcessing;
import semashibaev.ifmo.server.structCollection.substruct.MpaaRating;

public class PrintUniqueMpaaRating extends Command {
    public PrintUniqueMpaaRating(CommandProcessing commandProcessing) {
        super(commandProcessing);
        this.helpInfo = "Prints mpaa rating of all Movie elements in collection";
    }

    @Override
    public void run() {
        this.answer="";
        int length = commandProcessing.struct.size();
        int i = 0;
        int r = 0;
        int g = 0;
        int pg = 0;
        int nc = 0;
        for (i = 0; i < length; i++) {
            MpaaRating mpaaRating = commandProcessing.struct.get(i).getmpaaRating();
            if (mpaaRating == MpaaRating.R) {
                r = 1 + r;
            }
            if (mpaaRating == MpaaRating.G) {
                g = 1 + g;
            }
            if (mpaaRating == MpaaRating.PG) {
                pg = 1 + pg;
            }
            if (mpaaRating == MpaaRating.NC_17) {
                nc = 1 + nc;
            }
        }
          if (r==1){
              this.answer+=("Unique MpaaRating is R"+"\n");
          }
        if (g==1){
            this.answer+=("Unique MpaaRating is G"+"\n");
        }
        if (pg==1){
            this.answer+=("Unique MpaaRating is PG"+"\n");
        }
        if (nc==1){
            this.answer+=("Unique MpaaRating is NC"+"\n");
        }
        if (!(r==1||g==1||pg==1||nc==1)){
            this.answer=("not unique MpaaRating ");
        }
    }


    @Override
    protected void checkArgs() throws Exception {
        if (this.args.size() != 0)
            answer="**Too many arguments";
    }
}