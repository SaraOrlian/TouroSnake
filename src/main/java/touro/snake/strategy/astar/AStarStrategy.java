package touro.snake.strategy.astar;

import touro.snake.*;
import touro.snake.strategy.Node;
import touro.snake.strategy.SnakeStrategy;

import java.util.ArrayList;

/**
 * Implement the AStar Algorithm
 * https://www.youtube.com/watch?v=-L-WgKMFuhE
 * https://medium.com/@nicholas.w.swift/easy-a-star-pathfinding-7e6689c7f7b2
 */

public class AStarStrategy implements SnakeStrategy {
    @Override
    public void turnSnake(Snake snake, Garden garden) {

        Direction directions[] = Direction.values();
        //initialize open list -- has the possibilities
        ArrayList<Node> open = new ArrayList<>();
        //initialize closed list -- has the path among other nodes visited that had low numbers
        ArrayList<Node> closed = new ArrayList<>();

        Node headNode = new Node(snake.getHead());  //headNode = start
        Food food = garden.getFood();
        //protect against null pointer exception
        if (food == null) {
            return;
        }
        Node endNode = new Node(food.getX(), food.getY());   //endNode = target

        //put starting node on open list
        open.add(headNode);


        //loop until found the end
        while (!open.isEmpty()) {

            //let the currentNode equal the node with the least f value
            Node currentNode = getLeastCostNode(open);

            open.remove(currentNode);
            closed.add(currentNode);

            //Found the goal
            if (currentNode.equals(endNode)) {
                //snake moves according to closed list
                makeSnakeMove(snake, currentNode, headNode);
                System.out.println("I'm here");
                return;
            }

            for (Direction direction : directions) {
                Node child = new Node(currentNode.moveTo(direction), currentNode, food);
//               //adjacent square on closed list
                if (closed.contains(child)) {
                    continue;
                }
                //adjacent square not walkable
                if (snake.contains(child) || !child.inBounds()) {
                    continue;
                }

                //adjacent square not on open list
                if (!open.contains(child)) {
                    // look at a new node and calculate the cost
                    Node node = new Node(child, currentNode, endNode);
                    // Add the child to the openList
                    open.add(node);
                }

                //adjacent square on open list
                if (open.contains(child) || child.getCost() < currentNode.getCost()) {
                    //the original child info
                    int childIndex = open.indexOf(child);
                    Node oldChild = open.get(childIndex);
                    //create a new child of the same x and y coordinates since perhaps a different cost will be calculated- if the algorithm switched paths
                    Node newChild = new Node(child, currentNode, endNode);
                    //check which path is more efficient
                    if (newChild.getCost() < oldChild.getCost()) {
                        //replace the child with the one that has the least cost
                        open.remove(oldChild);
                        open.add(newChild);
                    }

                }

            }
        }

    }

    private void makeSnakeMove(Snake snake, Node currentNode, Node headNode) {
        Node path = new Node(currentNode);
        while(currentNode != headNode) {
            path = currentNode;
            currentNode = currentNode.getParent();
        }
        Direction direction = headNode.directionTo(path);
        snake.turnTo(direction);
    }


    private Node getLeastCostNode(ArrayList<Node> open) {
        Node smallest = open.get(0);
        for (int ix = 1; ix < open.size(); ix++) {
            if (smallest.getCost() > open.get(ix).getCost()) {
                smallest = open.get(ix);
            }
        }
        return smallest;
    }
}



