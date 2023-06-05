package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A graph that is composed of a set of verticies and edges.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Graph<T extends Comparable<T>> {
  private Set<T> verticies;
  private Set<Edge<T>> edges;

  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.verticies = verticies;
    this.edges = edges;
  }

  /**
   * A method finds all roots in the graph.
   *
   * @return The set of roots
   */
  public Set<T> getRoots() {
    // TODO: Task 1.
    Set<Integer> roots = new TreeSet<>();

    // Indegree = 0 and outDegree > 0 -> root
    for (T vertex : verticies) {
      int inDegree = 0;
      int outDegree = 0;
      for (Edge<T> edge : edges) {
        if (vertex == edge.getDestination()) {
          inDegree++;
        }
        if (vertex == edge.getSource()) {
          outDegree++;
        }
      }
      if (inDegree == 0 && outDegree > 0) {
        roots.add(Integer.parseInt(vertex.toString()));
      }
    }
    // smallest vertex in equivalence relation
    for (T vertex : verticies) {
      Set<T> equivalenceClass = getEquivalenceClass(vertex);
      if (!equivalenceClass.isEmpty()) {
        roots.add(Integer.parseInt(Collections.min(equivalenceClass).toString()));
      }
    }
    // Change the type from Integer to T
    LinkedHashSet<T> rootsInOrder = new LinkedHashSet<T>();
    for (Integer root : roots) {
      rootsInOrder.add((T) (Object) String.valueOf(root));
    }

    return rootsInOrder;
  }

  /**
   * A method determines whether a graph is reflexive or not.
   *
   * @return true or false showing if the graph is reflexive
   */
  public boolean isReflexive() {
    // TODO: Task 1.
    Set<T> reflexiveVerticies = new HashSet<T>();
    for (Edge<T> edge : edges) {
      if (edge.getSource().equals(edge.getDestination())) {
        reflexiveVerticies.add(edge.getSource());
      }
    }

    if (verticies.equals(reflexiveVerticies)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * A method determines whether a graph is symmetric or not.
   *
   * @return true or false showing if the graph is symmetric
   */
  public boolean isSymmetric() {
    // TODO: Task 1.
    Set<Edge<T>> symmetricEdges = new HashSet<Edge<T>>();
    for (Edge<T> edge : edges) {
      Edge<T> symEdge = new Edge<T>(edge.getDestination(), edge.getSource());
      if (edges.contains(symEdge)) {
        symmetricEdges.add(edge);
      }
    }

    if (edges.equals(symmetricEdges)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * A method determines whether a graph is transitive or not.
   *
   * @return true or false showing if the graph is transitive
   */
  public boolean isTransitive() {
    // TODO: Task 1.
    // Copy the edges set
    Set<Edge<T>> edgesCopy = new HashSet<Edge<T>>(edges);

    // Get reflexive verticies
    Set<Edge<T>> reflexiveEdges = new HashSet<Edge<T>>();
    for (Edge<T> edge : edges) {
      if (edge.getSource().equals(edge.getDestination())) {
        reflexiveEdges.add(edge);
      }
    }

    // Remove reflexive vertices
    for (Edge<T> edge : reflexiveEdges) {
      edgesCopy.remove(edge);
    }

    // If no transitive relation, return false;
    for (Edge<T> edge : edgesCopy) {
      for (Edge<T> edge2 : edgesCopy) {
        if (edge.getDestination().equals(edge2.getSource())
            && !edge.getSource().equals(edge2.getDestination())) {
          Edge<T> edge3 = new Edge<T>(edge.getSource(), edge2.getDestination());
          if (!edgesCopy.contains(edge3)) {
            return false;
          } else if (edge2.equals(edge3)) {
            return false;
          }
        }
      }
    }

    return true;
  }

  /**
   * A method determines whether a graph is antisymmetric or not.
   *
   * @return true or false showing if the graph is antisymmetric
   */
  public boolean isAntiSymmetric() {
    // TODO: Task 1.
    for (Edge<T> edge : edges) {
      Edge<T> revEdge = new Edge<T>(edge.getDestination(), edge.getSource());
      // This is only false when there is a symmetric edge exists in the set and two edges are not
      // the same
      if (edges.contains(revEdge)) {
        if (!edge.equals(revEdge)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * A method determines whether a graph is equivalence or not.
   *
   * @return true or false showing if the graph is equivalence
   */
  public boolean isEquivalence() {
    // TODO: Task 1.
    if (isReflexive() && isSymmetric() && isTransitive()) {
      return true;
    } else {
      return false;
    }
  }

  
  /**
   * A method finds all equivalence classes in a graph.
   * 
   * @param vertex vertex which we would like to find equivalence classes
   * @return a set of all equivalence classes for a vertex
   */
  public Set<T> getEquivalenceClass(T vertex) {
    // TODO: Task 1.
    Set<Integer> equivalenceClass = new TreeSet<>();
    if (!isEquivalence()) {
      return new TreeSet<T>();
    }
    for (Edge<T> edge : edges) {
      if (vertex.equals(edge.getSource())) {
        equivalenceClass.add(Integer.parseInt(edge.getDestination().toString()));
      }
    }
    LinkedHashSet<T> equivalenceClassInOrder = new LinkedHashSet<>();
    for (Integer v : equivalenceClass) {
      equivalenceClassInOrder.add((T) (Object) String.valueOf(v));
    }
    return equivalenceClassInOrder;
  }

  /**
   * A method iteratively finds the breadth first search for the graph.
   *
   * @return the list of breadth first search verticies
   */
  public List<T> iterativeBreadthFirstSearch() {
    // TODO: Task 2.
    Queue<T> discovered = new Queue<T>();
    List<T> explored = new ArrayList<T>();

    // Get roots and mark the roots as discovered
    Set<T> roots = getRoots();

    for (T a : roots) {
      discovered.enQueue(a);
      // Until all vertecies are explored, repeat the process
      while (!(explored.size() == verticies.size()) && !discovered.isEmpty()) {
        T b = discovered.peek();
        discovered.deQueue();
        if (!explored.contains(b)) {
          explored.add(b);
          List<T> reach = reachableVerticies(b);
          Collections.reverse(reach);
          while (!reach.isEmpty()) {
            for (T c : reachableVerticies(b)) {
              discovered.enQueue(c);
              reach.remove(c);
            }
          }
        }
      }
    }
    return explored;
  }

  /**
   * A method iteratively finds the depth first search for the graph.
   *
   * @return list of depth first search verticies
   */
  public List<T> iterativeDepthFirstSearch() {
    // TODO: Task 2.

    Stack<T> discovered = new Stack<>();
    List<T> explored = new ArrayList<>();

    // Get roots and reverse the order
    Set<T> roots = getRoots();
    List<T> rootsInList = new ArrayList<>();
    for (T a : roots) {
      rootsInList.add(a);
    }
    Collections.reverse(rootsInList);

    // Add roots to the stack first
    for (T a : rootsInList) {
      discovered.push(a);
    }

    // Iterate until all nodes are explored
    while (!(explored.size() == verticies.size())) {
      T b = discovered.pop();
      if (!explored.contains(b)) {
        explored.add(b);
        List<T> reach = reachableVerticies(b);
        reach = reversedReachable(reach);
        while (!reach.isEmpty()) {
          for (T c : reversedReachable(reachableVerticies(b))) {
            discovered.push(c);
            reach.remove(c);
          }
        }
      }
    }
    return explored;
  }

  /**
   * A helpter method which finds all reachable verticies form a vertex.
   *
   * @param vertex a vertex to find out what verticies are reachable from
   * @return list of all reachable verticies in a list
   */
  // A helper method to find what nodes are reachable from the vertex
  public List<T> reachableVerticies(T vertex) {
    Set<T> reachedVerticies = new TreeSet<>();
    // Find all vertices that have been connected to the vertex except the self loop
    for (Edge<T> edge : edges) {
      // if the edges starts from the vertex and not a self loop, the vertex is reachable from the
      // vertex
      if (vertex.equals(edge.getSource()) && !vertex.equals(edge.getDestination())) {
        reachedVerticies.add(edge.getDestination());
      }
    }

    // Move the set to list to satisfy return type
    List<Integer> result = new ArrayList<>();
    for (T r : reachedVerticies) {
      result.add(Integer.parseInt(r.toString()));
    }
    Collections.sort(result);

    // Convert the integer array to generic array
    List<T> resultInT = new ArrayList<>();
    for (Integer i : result) {
      resultInT.add((T) (Object) String.valueOf(i));
    }
    return resultInT;
  }

  /**
   * A helper method which reverses the given list of reachable verticies.
   *
   * @param reach all reachable vertices in asending order
   * @return a list of reachable verticies in descending order
   */
  // helper method to reverse the list of reachable verticies
  public List<T> reversedReachable(List<T> reach) {
    Collections.reverse(reach);
    return reach;
  }

  /**
   * A method recursively search for all breadth first search verticies by calling recursive method.
   *
   * @return a list of the breadth first search verticies
   */
  public List<T> recursiveBreadthFirstSearch() {
    // TODO: Task 3.
    List<T> explored = new ArrayList<T>();
    Queue<T> discovered = new Queue<T>();
    Set<T> roots = getRoots();
    return recursiveBreadthFirstSearch(explored, discovered, roots);
  }

  /**
   * A recursive method finds all breadth first search verticies.
   *
   * @param explored all the verticies that have been explored
   * @param discovered all the verticies that have been discovered, but not yet explored
   * @return completed breadth first search list
   */
  public List<T> recursiveBreadthFirstSearch(List<T> explored, Queue<T> discovered, Set<T> roots) {
    // To initiate, all roots are discoverd
    if (!roots.isEmpty() && discovered.isEmpty()) {
      List<T> rootsInList = new ArrayList<>();
      for (T root : roots) {
        rootsInList.add(root);
      }
      discovered.enQueue(rootsInList.get(0));
      roots.remove(rootsInList.get(0));
    }
    // Until all nodes are explored, recursively repeat this stage
    if (!(explored.size() == verticies.size())) {
      T dequed = discovered.peek();
      discovered.deQueue();
      if (!explored.contains(dequed)) {
        explored.add(dequed);
      }
      List<T> reach = reachableVerticies(dequed);
      Collections.reverse(reach);
      while (!reach.isEmpty()) {
        for (T c : reachableVerticies(dequed)) {
          discovered.enQueue(c);
          reach.remove(c);
        }
      }
      recursiveBreadthFirstSearch(explored, discovered, roots);
    }

    return explored;
  }

  /**
   * A method recursively search for all depth first search verticies by calling recursive method.
   *
   * @return a list of the depth first search verticies
   */
  public List<T> recursiveDepthFirstSearch() {
    // TODO: Task 3.
    List<T> explored = new ArrayList<T>();
    Stack<T> discovered = new Stack<T>();
    return recursiveDepthFirstsearch(explored, discovered);
  }

  /**
   * A recursive method finds all breadth first search verticies.
   *
   * @param explored all the verticies that have been explored
   * @param discovered all the verticies that have been discovered, but not yet explored
   * @return completed depth first search list
   */
  public List<T> recursiveDepthFirstsearch(List<T> explored, Stack<T> discovered) {

    // To initialise, put roots in descending order in the discovered
    if (explored.isEmpty()) {
      Set<T> roots = getRoots();
      List<T> rootsInList = new ArrayList<>();
      for (T a : roots) {
        rootsInList.add(a);
      }
      Collections.reverse(rootsInList);
      for (T a : rootsInList) {
        discovered.push(a);
      }
    }

    // Until all nodes are explored, repeat this step
    if (!(explored.size() == verticies.size())) {
      T b = discovered.pop();
      if (!explored.contains(b)) {
        explored.add(b);
        List<T> reach = reachableVerticies(b);
        reach = reversedReachable(reach);
        while (!reach.isEmpty()) {
          for (T c : reversedReachable(reachableVerticies(b))) {
            discovered.push(c);
            reach.remove(c);
          }
        }
      }
      explored = recursiveDepthFirstsearch(explored, discovered);
    }

    return explored;
  }
}
