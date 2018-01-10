package com.company;

import java.util.ArrayList;


public class MapNode {
    private String name;
    private ArrayList<MapNode> upstreamConnections;
    private ArrayList<MapNode> downstreamConnections;

public MapNode(String name, MapNode connection){
    this.name=name;
    upstreamConnections=new ArrayList<>();
    downstreamConnections=new ArrayList<>();
    addConnection(connection);
}
public MapNode(String name){
    upstreamConnections=new ArrayList<>();
    downstreamConnections=new ArrayList<>();
    this.name=name;
}
    public void addConnection(MapNode connection){ //adds upstream and downstream connections between two nodes
    upstreamConnections.add(connection);
    connection.downstreamConnections.add(this);
}

    public String getName() {
        return name;
    }

    public ArrayList<MapNode> getDownstreamConnections() {
        return downstreamConnections;
    }

    public ArrayList<MapNode> getUpstreamConnections() {
        return upstreamConnections;
    }
}
