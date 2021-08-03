package semashibaev.ifmo.server.structCollection.substruct;

public enum MpaaRating {
    G,
    PG,
    R,
    NC_17;

    public static MpaaRating fromString(String mpaaRating) throws Exception {
        if (mpaaRating.equals("G"))
            return MpaaRating.G;
        if (mpaaRating.equals("PG"))
            return MpaaRating.PG;
        if (mpaaRating.equals("R"))
            return MpaaRating.R;
        if (mpaaRating.equals("NC_17"))
            return MpaaRating.NC_17;

        throw new Exception("**Invalid Mpaa Rating");

        }
    }
