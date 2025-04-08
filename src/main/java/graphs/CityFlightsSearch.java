import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * For an input list of flights from city A to city B, compute all possible ways to get from an input
 * city to another. Flights are directional with a start and end. This forms a graph of city nodes
 * and flight vertices.
 * Assumes no duplicate flights - could be handled by changing list of flights to set
 */
public class CityFlightsSearch {
    Map<String, List<String>> cityFlights = new HashMap<>();

    public CityFlightsSearch(List<String> hyphenSeparatedFlights) {
        // TODO bad input handling
        for (String fStr : hyphenSeparatedFlights) {
            String[] split = fStr.split("-");
            String start = split[0];
            String end = split[1];
            List<String> fList;
            if (cityFlights.containsKey(start)) {
                fList = cityFlights.get(start);
            } else {
                fList = new ArrayList<>();
            }
            fList.add(end);
            cityFlights.put(start, fList);
        }
    }

    class City {
        String name;
        boolean visited = false;

        public City(String name) {
            this.name = name;
        }

        public boolean isVisited() { return visited; }

        public void visit() { visited = true; }

        public void reset() { visited = false; }

        public int hashCode() { return name.hashCode(); }

        public boolean equals(Object o) {
            if (!(o instanceof City)) {
                return false;
            }
            City that = (City) o;
            return this.name.equals(that.name);
        }
    }

    public class Flight {
        String start;
        String end;

        public Flight(String start, String end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * Finds all paths from start to end in a list of flights.
     * @param start The starting city.
     * @param end The destination city.
     * @param path The current path being explored (list of cities).
     * @param allPaths A list to store all complete paths.
     * @return A list of all paths from start to end.
     */
    public List<List<Flight>> findAllPaths(String start, String end, List<String> path, List<List<String>> allPaths) {
        // Add the current start city to the path
        path.append(start);

        if (start.equals(end)) {
            // This is a complete path
            allPaths.append(path);
            return allPaths;
        }

        for (City c : cityFlights.keys()) {
            if (c.name.equals(start) && c.isVisited()) {
                List<String> destinations = cityFlights.get(start);
                for (String fEnd : destinations) {
                    findAllPaths(fEnd, end, path, allPaths);
                }
            }
        }
        c.visit();

        return allPaths;
    }

    // Test client
    public static void main(String args[]) {
        List<String> hsf = new ArrayList<String>();
        hsf.add("Atlanta-Boston");
        hsf.add("Atlanta-Charlotte");
        hsf.add("Boston-Denver");
        hsf.add("Charlotte-Denver")
        hsf.add("Denver-New York"); // where the fuck is Enrique?

        String start = "Atlanta";
        String end = "New York";
        CityFlightsSearch cfs = new CityFlightsSearch(hsf);
        List<List<String>> all_paths = cfs.findAllPaths(hsf, start_city, end_city);
        print(all_paths)  # Output: [['A', 'B', 'D', 'E'], ['A', 'C', 'D', 'E']]
    }
}
