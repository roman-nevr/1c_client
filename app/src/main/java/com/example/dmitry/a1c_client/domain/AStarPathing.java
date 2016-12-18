package com.example.dmitry.a1c_client.domain;

import android.graphics.Point;

import com.example.dmitry.a1c_client.domain.entity.StoreMapObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by roma on 29.11.2016.
 */

public class AStarPathing {
    private StoreMapObject storeMapObject;
    private AStarValue[][] values;
    private Point start, finish;

    private List<AStarValue> uncheckedList;
    private List<AStarValue> checkedList;

    public static final int HEURISTIC_VALUE = 10;
    public static final int STEP_COST = 10;
    public static final int DIAGONAL_STEP_COST = 14;

    public AStarPathing(StoreMapObject storeMapObject) {
        this.storeMapObject = storeMapObject;
        values = new AStarValue[storeMapObject.getHeight()][storeMapObject.getWidth()];
    }

    public List<Point> pathing(){
        return pathing(storeMapObject.getStart(), storeMapObject.getFinish());
    }

    public List<Point> pathing(Point start, Point finish){
        this.start = start;
        this.finish = finish;
        calcHeuristicCost();
        uncheckedList = new ArrayList<>();
        checkedList = new ArrayList<>();
        AStarValue current = values[start.y][start.x];
        current.movementCost = 0;
        checkedList.add(values[start.y][start.x]);
        boolean stop = false;
        while (!stop){
            addPointToUncheckedList(current, uncheckedList);
            current = findBest(uncheckedList);
            checkedList.add(current);
            if (current.position.equals(finish)){
                stop = true;
            }
        }
        System.out.println(current.movementCost);
        return generatePath(current);
    }

    private List<Point> generatePath(AStarValue current) {
        List<Point> result = new LinkedList<>();
        boolean stop = false;
        do {
            result.add(0, current.position);
            current = values[current.previous.y][current.previous.x];
        }while (!current.position.equals(start));
        result.add(0,current.position);
        return result;
    }


    private AStarValue findBest(List<AStarValue> uncheckedList) {
        AStarValue result = uncheckedList.get(0);
        int cost = uncheckedList.get(0).movementCost + uncheckedList.get(0).heuristicCost;
        for (AStarValue value : uncheckedList) {
            if((value.movementCost + value.heuristicCost) < cost){
                cost = value.movementCost + value.heuristicCost;
                result = value;
            }
        }
        uncheckedList.remove(result);
        return result;
    }

    private void addPointToUncheckedList(AStarValue current, List<AStarValue> uncheckedList) {
        addPointIfExists(current.position.x - 1, current.position.y, uncheckedList, current, STEP_COST);
        addPointIfExists(current.position.x + 1, current.position.y, uncheckedList, current, STEP_COST);
        addPointIfExists(current.position.x, current.position.y - 1, uncheckedList, current, STEP_COST);
        addPointIfExists(current.position.x, current.position.y + 1, uncheckedList, current, STEP_COST);
        addPointIfExists(current.position.x + 1, current.position.y + 1, uncheckedList, current, DIAGONAL_STEP_COST);
        addPointIfExists(current.position.x + 1, current.position.y - 1, uncheckedList, current, DIAGONAL_STEP_COST);
        addPointIfExists(current.position.x - 1, current.position.y + 1, uncheckedList, current, DIAGONAL_STEP_COST);
        addPointIfExists(current.position.x - 1, current.position.y - 1, uncheckedList, current, DIAGONAL_STEP_COST);
    }

    private void addPointIfExists(int x, int y, List<AStarValue> uncheckedList, AStarValue previous, int cost) {
        if ((x >= 0) && (x < storeMapObject.getWidth()) && (y >= 0) &&
                (y < storeMapObject.getHeight()) && (!checkedList.contains(values[y][x])) &&
                (isPointReachable(x,y)) && (isPointReachable(x, previous.position.y) &&
                (isPointReachable(previous.position.x, y)))){
            if(!uncheckedList.contains(values[y][x])){
                values[y][x].movementCost = previous.movementCost + cost;
                values[y][x].previous = new Point(previous.position.x, previous.position.y);
                uncheckedList.add(values[y][x]);
            }else {
                if(values[y][x].movementCost > previous.movementCost + cost){
                    uncheckedList.remove(values[y][x]);
                }
            }
        }
    }

    private boolean isPointReachable(int x, int y){
        return storeMapObject.getType(x, y) == 0;
    }

    private void addNeighborPointIfExists(int x, int y, List<AStarValue> uncheckedList, AStarValue previous) {
        if ((x >= 0) && (x < storeMapObject.getWidth()) && (y >= 0) &&
                (y < storeMapObject.getHeight()) && (!checkedList.contains(values[y][x])) &&
                (storeMapObject.getType(x, y) == 0)){
            values[y][x].movementCost = previous.movementCost + STEP_COST;
            values[y][x].previous = new Point(previous.position.x, previous.position.y);
            uncheckedList.add(values[y][x]);
        }
    }


    private void calcHeuristicCost(){
        int movementCost = HEURISTIC_VALUE * (storeMapObject.getWidth() + storeMapObject.getHeight());
        for(int y = 0; y < storeMapObject.getHeight(); y++){
            for (int x = 0; x < storeMapObject.getWidth(); x++){
                int heuristicCost = (int) Math.sqrt(Math.pow((x - finish.x) * STEP_COST, 2.0f) +
                        Math.pow((y - finish.y) * STEP_COST, 2.0f));
                values[y][x] = new AStarValue(new Point(x, y),heuristicCost, movementCost);
            }
        }
    }

    private class AStarValue implements Comparable {
        int heuristicCost;
        int movementCost;
        Point position;
        Point previous;

        public AStarValue(Point position, int heuristicCost, int movementCost) {
            this.position = position;
            this.heuristicCost = heuristicCost;
            this.movementCost = movementCost;
        }

        public int getF(){
            return heuristicCost + movementCost;
        }

        @Override
        public String toString() {
            return "position ("+ position.x + "," +position.y +") cost = " + (movementCost + heuristicCost);
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof AStarValue){
                 return (movementCost + heuristicCost - ((AStarValue)o).movementCost - ((AStarValue)o).heuristicCost);
            }else {
                return 0;
            }
        }
    }
}
