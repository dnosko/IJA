package model;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataHolder {

    private List<Stop> stops = new ArrayList<>();
    private List<Street> streets = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();

    public DataHolder(String folderPath) {

        File streetsFile = new File(folderPath + "streets.txt");
        File stopsFile = new File(folderPath + "stops.txt");
        File linesFile = new File(folderPath + "lines.txt");
        File busesFile = new File(folderPath + "buses.txt");

        Scanner scanner;


        /* handling stops file */
        try {
            scanner = new Scanner(stopsFile);

            while (scanner.hasNextLine()) {
                String fileLine = scanner.nextLine();

                String[] splitWords = fileLine.split(" ", -1);

                String id = splitWords[0];
                Coordinate coordinate = new Coordinate(Double.parseDouble(splitWords[1]), Double.parseDouble(splitWords[2]));

                Stop stop = new Stop(id, coordinate);
                this.stops.add(stop);
            }

            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
            System.exit(-1);
        }


        /* handling streets file */
        try {
            scanner = new Scanner(streetsFile);

            while (scanner.hasNextLine()) {
                String fileLine = scanner.nextLine();

                String[] splitWords = fileLine.split(" ", -1);

                List<Coordinate> coordinates = new ArrayList<>();
                coordinates.add(new Coordinate(Double.parseDouble(splitWords[1]), Double.parseDouble(splitWords[2])));
                coordinates.add(new Coordinate(Double.parseDouble(splitWords[3]), Double.parseDouble(splitWords[4])));

                List<Stop> stops = new ArrayList<>();
                if ( splitWords.length > 5 ) {
                    String[] splitStops = splitWords[5].split("-", -1);
                    for (String stopId : splitStops) {
                        for (Stop stop : this.stops) {
                            if (stop.getId().equals(stopId)) {
                                stops.add(stop);
                                break;
                            }
                        }
                    }
                }

                Street street = new Street(splitWords[0], coordinates, stops);
                this.streets.add(street);
            }

            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
            System.exit(-1);
        }


        /* handling lines file */
        try {
            scanner = new Scanner(linesFile);

            while (scanner.hasNextLine()) {
                String fileLine = scanner.nextLine();

                String[] splitWords = fileLine.split(" ", -1);
                String[] splitStops = splitWords[1].split("-", -1);
                String[] splitStreets = splitWords[2].split("-", -1);

                List<Stop> stops = new ArrayList<>();
                for ( String stopId : splitStops ) {
                    for ( Stop stop : this.stops ) {
                        if ( stop.getId().equals(stopId) ) {
                            stops.add(stop);
                            break;
                        }
                    }
                }

                List<Street> streets = new ArrayList<>();
                for ( String streetId : splitStreets ) {
                    for ( Street street : this.streets ) {
                        if ( street.getId().equals(streetId) ) {
                            streets.add(street);
                            break;
                        }
                    }
                }

                Line line = new Line(splitWords[0], stops, streets);
                this.lines.add(line);
            }

            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
            System.exit(-1);
        }


        /* handling buses file */
        try {
            scanner = new Scanner(busesFile);

            while (scanner.hasNextLine()) {
                String fileLine = scanner.nextLine();

                String[] splitWords = fileLine.split(" ", -1);
                String[] splitTime = splitWords[1].split(":", -1);
                int time = Integer.parseInt(splitTime[0]) * 60 + Integer.parseInt(splitTime[1]);

                for ( Line line : this.lines ) {
                    if ( line.getId().equals(splitWords[0]) ) {
                        line.setBusesTimes(time);
                        break;
                    }
                }
            }

            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public List<Stop> getStops() {
        return this.stops;
    }

    public List<Street> getStreets() {
        return this.streets;
    }

    public List<Line> getLines() {
        return this.lines;
    }
}
