package semashibaev.ifmo.server;

import java.nio.file.Path;


public final class App {
    private CommandProcessing commandProcessing;
    public static Struct struct;
    private Path dbPath;

//    public App(String[] args) {
//            if (args.length > 0)
//                dbPath = Paths.get(args[0]);
//            else {
//                System.err.println("Set the path to db file");
//                System.exit(1);
//            }
//        }
//
//    public int exec() {
//        try {
//            struct = Struct.fromDatabase(dbPath);
//        } catch (Exception e) {
//            System.err.println("Cannot read db file\n" + e.getMessage());
//            return 1;
//        }
//        return 0;
//    }

    public static DataBase db;


    App() throws Exception {
//        try {
            db = DataBase.getInstance();
            System.out.println(db);
            struct = Struct.fromDatabase(db);
//        } catch (Exception e) {
//            Logger logger = Logger.getLogger("main_logger");
//            logger.warning(e.toString());
//            logger.warning("Using new empty structure");
//            struct = new Struct();
//        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("Saving and Exiting...");
                stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }

    public synchronized void stop() throws Exception {
        ConnectionReception.close();
        db.connection.close();

    }


}


