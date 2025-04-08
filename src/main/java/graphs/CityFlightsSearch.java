package graphs;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

/**
 * For an input list of flights from city A to city B, compute all possible ways to get from an input
 * city to another. Flights are directional with a start and end. This forms a graph of city nodes
 * and flight vertices.
 * Assumes no duplicate flights - could be handled by changing list of flights to set
 */
public class CityFlightsSearch {
    Map<String, List<String>> cityFlights = new HashMap<>();

    public CityFlightsSearch(List<String> hyphenSeparatedFlights) {
        if (hyphenSeparatedFlights == null) {
            throw new IllegalArgumentException("Null hyphen separated flights argument");
        }
        for (String fStr : hyphenSeparatedFlights) {
            if (fStr == null || fStr.indexOf('-') < 0) {
                throw new IllegalArgumentException("Hyphen separated flights argument has malformed entry");
            }
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
     * Finds all possible flight paths from the start city to the end city.
     * 
     * @param start The starting city name.
     * @param end The destination city name.
     * @return A list of all possible paths, where each path is a list of city names in order of travel.
     * @throws IllegalArgumentException if start or end city is null or doesn't exist in the flight network.
     */
    public List<List<String>> findAllPaths(String start, String end) {
        // Validate input parameters
        validateCities(start, end);
        
        // Initialize result collection and tracking structures
        List<List<String>> allPaths = new ArrayList<>();
        List<String> currentPath = new ArrayList<>();
        Set<String> visitedCities = new HashSet<>();
        
        // Start the recursive depth-first search
        dfs(start, end, currentPath, allPaths, visitedCities);
        
        return allPaths;
    }
    
    /**
     * Validates that the start and end cities exist in the flight network.
     * 
     * @param start The starting city name.
     * @param end The destination city name.
     * @throws IllegalArgumentException if validation fails.
     */
    private void validateCities(String start, String end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end cities cannot be null");
        }
        
        if (!cityFlights.containsKey(start)) {
            throw new IllegalArgumentException("Start city '" + start + "' does not exist in the flight network");
        }
        
        // Check if end city exists anywhere in the network
        if (!start.equals(end)) {  // Skip check if start equals end
            boolean endCityExists = cityFlights.containsKey(end);  // Check if it's a departure city
            
            if (!endCityExists) {
                // Check if it's a destination city
                for (List<String> destinations : cityFlights.values()) {
                    if (destinations.contains(end)) {
                        endCityExists = true;
                        break;
                    }
                }
                
                if (!endCityExists) {
                    throw new IllegalArgumentException("End city '" + end + "' does not exist in the flight network");
                }
            }
        }
    }
    
    /**
     * Recursive depth-first search to find all paths between cities.
     * 
     * @param current The current city being visited.
     * @param end The destination city.
     * @param path The current path being explored.
     * @param allPaths Collection of all complete paths found so far.
     * @param visited Set of cities already visited in the current path to avoid cycles.
     */
    private void dfs(String current, String end, List<String> path, List<List<String>> allPaths, Set<String> visited) {
        // Add current city to path and mark as visited
        visited.add(current);
        path.add(current);
        
        // If we've reached the destination, add this path to our results
        if (current.equals(end)) {
            allPaths.add(new ArrayList<>(path));
        } else {
            // Get all possible next destinations from current city
            List<String> destinations = cityFlights.getOrDefault(current, Collections.emptyList());
            
            // Explore each unvisited destination
            for (String nextCity : destinations) {
                if (!visited.contains(nextCity)) {
                    dfs(nextCity, end, path, allPaths, visited);
                }
            }
        }
        
        // Backtrack: remove the current city from path and mark as unvisited
        path.remove(path.size() - 1);
        visited.remove(current);
    }

    private static void printPaths(List<List<String>> paths) {
        for (List<String> path : paths) {
            StringBuilder sb = new StringBuilder();
            for (String c : path) {
                if (sb.length() != 0) {
                    sb.append(" -> ");
                }
                sb.append(c);
            }
            System.out.println(sb.toString());
        }
    }

    // Test client
    public static void main(String args[]) {
        List<String> hsf = new ArrayList<String>();
        hsf.add("Atlanta-Boston");
        hsf.add("Atlanta-Charlotte");
        hsf.add("Boston-Denver");
        hsf.add("Charlotte-Denver");
        hsf.add("Denver-New York");

        String start = "Atlanta";
        String end = "New York";
        CityFlightsSearch cfs = new CityFlightsSearch(hsf);
        List<List<String>> allPaths = cfs.findAllPaths(start, end);
        printPaths(allPaths);
    }
}
