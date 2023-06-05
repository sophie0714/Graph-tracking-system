package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
   * @return Set<T> The set of roots
   */
  public Set<T> getRoots() {
    // TODO: Task 1.
    Set<T> roots = new TreeSet<>();

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
        roots.add(vertex);
      }
    }
    // smallest vertex in equivalence relation
    for (T vertex : verticies) {
      Set<T> equivalenceClass = getEquivalenceClass(vertex);
      if (!equivalenceClass.isEmpty()) {
        roots.add(Collections.min(equivalenceClass));
      }
    }

    return roots;
  }

  /**
   * A method determines whether a graph is reflexive or not.
   *
   * @return boolean showing if the graph is reflexive
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
   * @return boolean showing if the graph is symmetric
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
   * @return boolean showing if the graph is transitive
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
   * @return boolean showing if the graph is antisymmetric
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
   * @return boolean showing if the graph is equivalence
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
   *@param T vertex which we would like to find equivalence classes
   *@return Set<T> all equivalence classes for a vertex
   */
  public Set<T> getEquivalenceClass(T vertex) {
    // TODO: Task 1.
    Set<T> equivalenceClass = new TreeSet<T>();
    if (!isEquivalence()) {
      return equivalenceClass;
    }
    for (Edge<T> edge : edges) {
      if (vertex.equals(edge.getSource())) {
        equivalenceClass.add(edge.getDestination());
      }
    }
    return equivalenceClass;
  }

  /**
   * A method iteratively finds the breadth first search for the graph.
   *
   *@return List<T> the list of breadth first search verticies
   */
  public List<T> iterativeBreadthFirstSearch() {
    // TODO: Task 2.
    Queue<T> discovered = new Queue<T>();
    List<T> explored = new ArrayList<T>();

    // Get roots and mark the roots as discovered
    Set<T> roots = getRoots();

    for (T a : roots) {
      discovered.enQueue(a);
    }

    // Until all vertecies are explored, repeat the process
    while (!(explored.size() == verticies.size())) {
      T b = discovered.peek();
      discovered.deQueue();
      if (!explored.contains(b)) {
        explored.add(b);
        List<T> reach = reachableVertex(b);
        Collections.reverse(reach);
        while (!reach.isEmpty()) {
          for (T c : reachableVertex(b)) {
            discovered.enQueue(c);
            reach.remove(c);
          }
        }
      }
    }
    return explored;
  }

  /**
   * A method iteratively finds the depth first search for the graph.
   *
   *@return List<T> the list of depth first search verticies
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
        List<T> reach = reachableVertex(b);
        reach = reversedReachable(reach);
        while (!reach.isEmpty()) {
          for (T c : reversedReachable(reachableVertex(b))) {
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
   *@param vertex
   *@return List<T> containing all reachable verticies in a list
   */
  // A helper method to find what nodes are reachable from the vertex
  public List<T> reachableVertex(T vertex) {
    Set<T> reachedVertex = new TreeSet<T>();
    // Find all vertices that have been connected to the vertex except the self loop
    for (Edge<T> edge : edges) {
      if (vertex.equals(edge.getSource()) && !vertex.equals(edge.getDestination())) {
        reachedVertex.add(edge.getDestination());
      }
    }

    // Move the set to list to satisfy return type
    List<T> result = new ArrayList<T>();
    for (T r : reachedVertex) {
      result.add(r);
    }
    return result;
  }

  /**
   * A helper method which reverses the given list of reachable verticies.
   *
   *@param reach all reachable vertices in asending order
   *@return List<T> reachable verticies in descending order
   */
  // helper method to reverse the list of reachable verticies
  public List<T> reversedReachable(List<T> reach) {
    Collections.reverse(reach);
    return reach;
  }

  /**
   * A method recursively search for all breadth first search verticies by calling recursive method.
   *
   *@return List<T> a list of the breadth first search verticies
   */
  public List<T> recursiveBreadthFirstSearch() {
    // TODO: Task 3.
    List<T> explored = new ArrayList<T>();
    Queue<T> discovered = new Queue<T>();
    return recursiveBreadthFirstSearch(explored, discovered);
  }

  /**
   * A recursive method finds all breadth first search verticies.
   *
   *@param explored all the verticies that have been explored
   *@param discovered all the verticies that have been discovered, but not yet explored
   *@return List<T> completed breadth first search list
   */
  public List<T> recursiveBreadthFirstSearch(List<T> explored, Queue<T> discovered) {
    // To initiate, all roots are discoverd
    if (explored.isEmpty()) {
      Set<T> roots = getRoots();
      for (T root : roots) {
        discovered.enQueue(root);
      }
    }
    // Until all nodes are explored, recursively repeat this stage
    if (!(explored.size() == verticies.size())) {
      T dequed = discovered.peek();
      discovered.deQueue();
      if (!explored.contains(dequed)) {
        explored.add(dequed);
      }
      List<T> reach = reachableVertex(dequed);
      Collections.reverse(reach);
      while (!reach.isEmpty()) {
        for (T c : reachableVertex(dequed)) {
          discovered.enQueue(c);
          reach.remove(c);
        }
      }
      recursiveBreadthFirstSearch(explored, discovered);
    }

    return explored;
  }

  /**
   * A method recursively search for all depth first search verticies by calling recursive method.
   *
   *@return List<T> a list of the depth first search verticies
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
   *@param explored all the verticies that have been explored
   *@param discovered all the verticies that have been discovered, but not yet explored
   *@return List<T> completed depth first search list
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
        List<T> reach = reachableVertex(b);
        reach = reversedReachable(reach);
        while (!reach.isEmpty()) {
          for (T c : reversedReachable(reachableVertex(b))) {
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
