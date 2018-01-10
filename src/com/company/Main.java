package com.company;
import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        //use this block of code if testing with a file
/*        try {
            System.setIn(new FileInputStream(new File("filepath"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/


        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;

        in.readLine(); //skips over "Map:" in the input, assuming input is well formed

        Map<String,MapNode> map= new TreeMap<String, MapNode>(new Comparator<String>()
        {public int compare(String o1, String o2) { //comparator for ordering TreeMap entries
                return o1.compareTo(o2);
            }
            });
        /* Tree Map to store MapNodes alphabetically by name,
         also provides fast o(log(n) times for get() and containsKey() since names are unique*/

        Map<String,MapNode> avoid=new TreeMap<String, MapNode>(new Comparator<String>() { //tree map storing locations to avoid
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        String[] pairs;
        String node;
        String connection;

        while (!((line=in.readLine().trim()).equals("Avoid:"))){ //moves onto parsing avoidable locations if "Avoid:" is reached
           pairs=line.split(" "); //splits line into strings separated by spaces
            node=pairs[0]; //reads the first address in the pair
            MapNode entry1;
            MapNode entry2;
            connection=pairs[1]; //assumes that input is in pairs at this point and reads the second address
            if(!map.containsKey(connection)){ //creates a node for the second address in the pair, or retrieves it if it exists
                entry1=new MapNode(connection);
                map.put(connection,entry1);
            }else{
                entry1=map.get(connection);
            }
        if(!map.containsKey(node)){ //creates a node for the first address in the pair, or retrieves it if it exists
            entry2=new MapNode(node);
            map.put(node,entry2);
        }else{
            entry2=map.get(node);
        }
        entry1.addConnection(entry2); //connects the two addresses with upstream and downstream connections
	}

	while(!((line=in.readLine().trim()).equals("Peggy:"))){ //adds nodes to avoid into a second TreeMap
        pairs=line.split(" ");
        for(int i=0; i<pairs.length;i++) {//adds every element if the split line into the avoid addresses
            node = pairs[i];
            MapNode avoidAddress = map.get(node); //assumes that avoidable addresses are in the map already
            avoid.put(node, avoidAddress);
        }
    }
    ArrayList<MapNode> PeggyStart=new ArrayList<>(); //Peggy's starting locations
	ArrayList<MapNode> SamStart=new ArrayList<>(); //Sam's starting locations

    while(!((line=in.readLine().trim()).equals("Sam:"))){ //populates a list with Peggy's starting positions
	    pairs=line.split(" ");
        for(int i=0; i<pairs.length;i++) {
            node = pairs[i];
            PeggyStart.add(map.get(node));
        }
    }

        line=in.readLine();
        pairs = line.split(" ");
           for (int i = 0; i < pairs.length; i++) { //populates a list with Sam's starting positions
               node = pairs[i];
               SamStart.add(map.get(node));
           }

        in.close();

        Map<String,MapNode> Peggy=new TreeMap<String, MapNode>(new Comparator<String>() { //nodes Peggy can access
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        Map<String,MapNode> Sam=new TreeMap<String, MapNode>(new Comparator<String>() { //nodes Sam can access
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        for(MapNode k: PeggyStart){ //traverses all nodes peggy can access and adds them to a TreeMap
            addValidLocationsDownstream(Peggy,avoid,k);
        }
        for(MapNode k: SamStart){ //traverses all nodes Sam can access and adds them to a TreeMap
            addValidLocationsUpstream(Sam,avoid,k);
        }
        Set<String> meetingLocations=Sam.keySet(); //turns tree of Sam's accessible locations into an iterable set
        for (String k:meetingLocations) { //iterates through Sam's set and prints the ones that are also in peggy's accessible locations
            if(Peggy.containsKey(k)){
                System.out.println(k);
            }
        }
        return;
    }

public static void addValidLocationsDownstream(Map<String, MapNode> PeggyMap,Map<String , MapNode> avoid, MapNode k){ //This function adds locations accessible downstream from a node into Peggy's Map
        PeggyMap.put(k.getName(),k); //adds node to Peggy's map
        for(MapNode j : k.getDownstreamConnections()){ //checks each of the nodes connections and calls this function on them if they are valid
            if(!PeggyMap.containsKey(j.getName())&&!avoid.containsKey(j.getName())){
                addValidLocationsDownstream(PeggyMap, avoid, j);
            }
        }
    }

    public static void addValidLocationsUpstream(Map<String, MapNode> SamMap,Map<String , MapNode> avoid, MapNode k){ //This function adds locations accessible upstream from a node into Sam's Map
        SamMap.put(k.getName(),k);
        for(MapNode j : k.getUpstreamConnections()){
            if(!SamMap.containsKey(j.getName())&&!avoid.containsKey(j.getName())){
                addValidLocationsUpstream(SamMap, avoid, j);
            }
        }
    }
}