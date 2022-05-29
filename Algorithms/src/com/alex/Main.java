package com.alex;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = IntStream.range(0, 100).boxed().collect(Collectors.toList());

        System.out.println("Find index:" + find(10, list));

        int[] findElemIndex = new Random().ints(0,300).limit(10).toArray();

        System.out.println("Find index:" + find(10, Arrays.stream(findElemIndex).boxed().collect(Collectors.toList())) +
                "; from list: " + Arrays.toString(findElemIndex));


        System.out.println("\nSelection Sort: " + selectionSort(findElemIndex) + "; for: " + Arrays.toString(findElemIndex));

        System.out.println("\nQuick Sort: " + quickSort(Arrays.stream(findElemIndex).boxed().collect(Collectors.toList())) +
                "; from list: " + Arrays.toString(findElemIndex));
        System.out.println();

        // Create Graph
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("you", new ArrayList<>(Arrays.asList("alice", "bob", "claire")));
        graph.put("bob", new ArrayList<>(Arrays.asList("anuj", "peggy")));
        graph.put("alice", new ArrayList<>(Arrays.asList("peggy")));
        graph.put("claire", new ArrayList<>(Arrays.asList("thom", "jonny")));
        graph.put("anuj", null);
        graph.put("peggy", null);
        graph.put("thom", null);
        graph.put("jonny", null);
        searchMangoSeller(graph);

        System.out.println();

        // Create Graph with Weights
        Map<String, Map<String, Integer>> weightGraph = new HashMap<>();
        weightGraph.put("start", new HashMap() {{put("a", 6); put("b", 2);}});
        weightGraph.put("a", new HashMap(){{put("fin", 1);}});
        weightGraph.put("b", new HashMap(){{put("a", 3);put("fin", 5);}});
        weightGraph.put("fin", null);

        Map<String, Integer> costs = new HashMap<>();
        costs.put("a", 6);
        costs.put("b", 2);
        costs.put("fin", Integer.MAX_VALUE);

        Map<String, String> parents = new HashMap<>();
        parents.put("a", "start");
        parents.put("b", "start");
        parents.put("fin", null);

        // Dijkstra's algorithm
        String node = findLowesCostNode(costs);
        while (node != null){
            Integer cost = costs.get(node);
            List<String> neighbors = new ArrayList<>();
            if(weightGraph.get(node) != null)
                neighbors.addAll(weightGraph.get(node).keySet());
            for(String n : neighbors){
                Integer new_cost = cost + weightGraph.get(node).get(n);
                if(costs.get(n) > new_cost){
                    costs.put(n, new_cost);
                    parents.put(n, node);
                }
            }
            processed.add(node);
            node = findLowesCostNode(costs);
        }

        System.out.println("Costs: " + costs);

        System.out.println();


        // ========= sets
        Set<String> statesNeeded = new HashSet<>(Arrays.asList("mt", "wa", "or", "id", "nv", "ut", "ca", "az"));
        Map<String, Set<String>> stations = new HashMap<>();
        stations.put("kone", new HashSet<>(Arrays.asList("id", "nv", "ut")));
        stations.put("ktwo", new HashSet<>(Arrays.asList("wa", "id", "mt")));
        stations.put("kthree", new HashSet<>(Arrays.asList("or", "nv", "ca")));
        stations.put("kfour", new HashSet<>(Arrays.asList("nv", "ut")));
        stations.put("kfive", new HashSet<>(Arrays.asList("ca", "nv", "az")));

        Set<String> finalStations = new HashSet<>();

        while (!statesNeeded.isEmpty()){
            String bestStation = null;
            Set<String> statesCovered = new HashSet<>();
            for(Map.Entry<String, Set<String>> st : stations.entrySet()){
                Set<String> covered = new HashSet<>(statesNeeded);
                covered.retainAll(st.getValue());
                if(covered.size() > statesCovered.size()){
                    bestStation = st.getKey();
                    statesCovered = covered;
                }
            }
            statesNeeded.removeAll(statesCovered);
            finalStations.add(bestStation);
        }

        System.out.println(finalStations);
    }


    private static final List<String> processed = new ArrayList<>();

    // Find Lowest Cost Node
    private static String findLowesCostNode(Map<String, Integer> costs){
        Integer lowest_cost = Integer.MAX_VALUE;
        String lowest_cost_node = null;
        for(String key : costs.keySet()){
            Integer cost = costs.get(key);
            if(cost < lowest_cost && !processed.contains(key)){
                lowest_cost = cost;
                lowest_cost_node = key;
            }
        }
        return lowest_cost_node;
    }

    // Binary Search
    private static Integer find(int number, List<Integer> list){
        int low = 0;
        int high = list.size() - 1;

        while (low <= high){
            int mid = (low + high)/2;
            int guess = list.get(mid);
            if(guess == number){
                return mid;
            } else if (guess > number){
                high = mid - 1;
            }   else {
                low = mid + 1;
            }
        }
        return null;
    }

    // Find the Smallest Number From Array
    private static Integer findSmallest(int[] arr){
        int smallest = arr[0];
        int smallest_index = 0;
        for(int i = 0; i < arr.length; i ++){
            if(arr[i] < smallest){
                smallest = arr[i];
                smallest_index = i;
            }
        }
        return smallest_index;
    }

    // Selection Sort
    private static List<Integer> selectionSort(int[] arr){
        List<Integer> newArr = new ArrayList<>();
        int k = arr.length;
        for(int i = 0; i < k; i ++){
            int smallest = arr[findSmallest(arr)];
            arr = Arrays.stream(arr).filter(j -> j != smallest).toArray();
            newArr.add(smallest);
        }
        return newArr;
    }

    // Find the Highest Number
    private static Integer highest(List<Integer> arr){
        if(arr.size() == 2){
            return arr.get(0) > arr.get(1) ? arr.get(0) : arr.get(1);
        }
        int max = highest(arr.subList(1, arr.size()));
        return arr.get(0) > max ? arr.get(0) : max;
    }

    // Quick Sort
    private static List<Integer> quickSort(List<Integer> arr){
        if(arr.size() < 2){
            return arr;
        } else {
            int pivot = arr.get(0);
            List<Integer> less = arr.stream().filter(i -> i < pivot).collect(Collectors.toList());
            List<Integer> greater = arr.stream().filter(i -> i > pivot).collect(Collectors.toList());
            return merge(quickSort(less), new ArrayList<>(Collections.singletonList(pivot)), quickSort(greater));
        }
    }

    // Merge Lists
    @SafeVarargs
    private static <T> List<T> merge(List<T>...params){
        List<T> result = new ArrayList<>();
        for(List<T> allParams : params){
            result.addAll(allParams);
        }
        return result;
    }

    // Breadth-first search
    private static boolean searchMangoSeller(Map<String, List<String>> graph){
        Deque<String> searchQueue = new ArrayDeque<>();
        searchQueue.addAll(graph.get("you"));

        List<String> searched = new ArrayList<>();

        while (searchQueue.iterator().hasNext()){
            String person = searchQueue.pollFirst();
            assert(person != null && !searched.contains(person));
            if(person.charAt(person.length() - 1) == 'm'){
                System.out.println(person + " is mango seller");
                return true;
            } else {
                if(graph.get(person) != null)
                    searchQueue.addAll(graph.get(person));
                searched.add(person);
            }
        }
        return false;
    }
}
