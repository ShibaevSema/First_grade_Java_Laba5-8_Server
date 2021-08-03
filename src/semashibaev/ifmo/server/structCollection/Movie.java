package semashibaev.ifmo.server.structCollection;

import semashibaev.ifmo.server.structCollection.substruct.Coordinates;
import semashibaev.ifmo.server.structCollection.substruct.MpaaRating;
import semashibaev.ifmo.server.structCollection.substruct.Person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Movie implements Comparable<Movie> {
    private static int incrementId = 1;
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer oscarsCount; //Значение поля должно быть больше 0, Поле может быть null
    private Integer goldenPalmCount; //Значение поля должно быть больше 0, Поле может быть null
    private double totalBoxOffice; //Значение поля должно быть больше 0, Поле не может быть null
    private MpaaRating mpaaRating; //Поле может быть null
    private Person screenwriter; //Поле  может быть null
    public String owner;

    public Movie() throws Exception {
        this.creationDate = LocalDateTime.now();
        this.id = Long.valueOf(incrementId++);
    }

    @Override
    public int compareTo(Movie movie) {

        int result = 0;
        int result1 = this.oscarsCount.compareTo(movie.oscarsCount);
        int result2 = this.goldenPalmCount.compareTo(movie.goldenPalmCount);
        if (result1 == 1 && result2 == 1) {
            result = 1;
        }
        if (result1 == 1 && result2 == 0) {
            result = 1;
        }
        if (result1 == 0 && result2 == 1) {
            result = 1;
        }
        if (result1 == -1 && result2 == 0) {
            result = -1;
        }
        if (result1 == 0 && result2 == -1) {
            result = -1;
        }
        if (result1 == -1 && result2 == -1) {
            result = -1;
        }
        if (result1 == 0 && result2 == 0) {
            result = Double.compare(this.totalBoxOffice, movie.totalBoxOffice);

        }
        return result;
    }
    public String getOwner(){
        return owner;
    }
    public void setOwner(String owner){
        this.owner=owner;
    }
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (name == null || name.isEmpty())
            throw new Exception("**Invalid name");
        this.name = name;
    }
    public void setId(long id){
        this.id= id;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinatesX, String coordinatesY) throws Exception {
        if (coordinatesX.equals("") || coordinatesY.equals(""))
            this.coordinates = null;
        try {
            long coordX = Long.parseLong(coordinatesX);
            long coordY = Long.parseLong(coordinatesY);
            this.coordinates = new Coordinates(coordX, coordY);
        } catch (Exception e) {
            throw new Exception("**Invalid coordinates format\n" + e.getMessage());
        }
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) throws Exception {
        try {
            this.creationDate = LocalDateTime.parse(creationDate);
        } catch (Exception e) {
            throw new Exception("**Invalid datetime format\n" + e.getMessage());
        }
    }

    public Integer getOscarssCount() {
        return oscarsCount;
    }
    public Integer getGoldenPalmCount() {
        return goldenPalmCount;
    }
    public Double getTotalBoxOffice() {
        return totalBoxOffice;
    }
    public void setOscarsCount(String oscarsCount) throws Exception {
        try {
            Integer num = Integer.parseInt(oscarsCount);
            if (num <= 0)
                throw new Exception("**Invalid oscarsCount (must be > 0)");

            this.oscarsCount = num;

        } catch (Exception e) {
            throw new Exception("**Invalid oscarsCount format\n" + e.getMessage());
        }

    }
    public void setGoldenPalmCount(String goldenPalmCount) throws Exception {
        try {
            Integer num = Integer.parseInt(goldenPalmCount);
            if (num <= 0)
                throw new Exception("**Invalid goldenPalmCount (must be > 0)");

            this.goldenPalmCount = num;

        } catch (Exception e) {
            throw new Exception("**Invalid goldenPalmCount format\n" + e.getMessage());
        }


    }


    public void setTotalBoxOffice(String totalBoxOffice) throws Exception {
        try {
            if (totalBoxOffice.length() > 15) throw new Exception("**Invalid totalBoxOffice(too many too many numbers) ");
        Double num = Double.parseDouble(totalBoxOffice);
        if (num <= 0)
        throw new Exception("**Invalid totalBoxOffice (must be > 0)");

        this.totalBoxOffice = num;

        } catch (Exception e) {
        throw new Exception("**Invalid totalBoxOffice format\n" + e.getMessage());
        }


}
    public MpaaRating getmpaaRating(){
        return mpaaRating;
        }

    public void setMpaaRating(String mpaaRating) throws Exception {
        if (mpaaRating.equals("")||mpaaRating.isEmpty())
            this.mpaaRating = null;
        else {
            this.mpaaRating = MpaaRating.fromString(mpaaRating);
        }
    }

    public Person getPerson(){
        return screenwriter;
        }

    public void setPerson(String screenwriter) {
        if (screenwriter.equals("")||screenwriter.isEmpty())
            this.screenwriter = null;
        else
            this.screenwriter = new Person(screenwriter);
    }

    @Override
    public String toString(){
        return id+","
                +name+","
                +coordinates+","
                +DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(creationDate)+","
                +oscarsCount+","
                +goldenPalmCount+","
                +totalBoxOffice+","
                +mpaaRating+","
                +screenwriter+","
                +owner;
        }
}


