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

  public boolean isEquivalence() {
    // TODO: Task 1.
    if (isReflexive() && isSymmetric() && isTransitive()) {
      return true;
    } else {
      return false;
    }
  }

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

  public List<T> iterativeBreadthFirstSearch() {
    // TODO: Task 2.
    return new ArrayList<>();
  }

  public List<T> iterativeDepthFirstSearch() {
    // TODO: Task 2.
    Stack<T> discovered = new Stack<>();
    List<T> explored = new ArrayList<>();
    for (T a : getRoots()) {
      discovered.push(a);
    }
    while (!discovered.isEmpty()) {
      T b = discovered.pop();
      if (!explored.contains(b)) {
        explored.add(b);
        List<T> reach = reachable(b);
        while (!reach.isEmpty()) {
          for (T c : reachable(b)) {
            discovered.push(c);
            reach.remove(c);
          }
        }
      }
    }
    return explored;
  }

  public List<T> reachable(T vertex) {
    Set<T> reach = new TreeSet<T>();
    for (Edge<T> edge : edges) {
      if (vertex.equals(edge.getSource()) && !vertex.equals(edge.getDestination())) {
        reach.add(edge.getDestination());
      }
    }

    List<T> result = new ArrayList<T>();
    for (T r : reach) {
      result.add(r);
    }
    Collections.reverse(result);
    return result;
  }

  public List<T> recursiveBreadthFirstSearch() {
    // TODO: Task 3.
    throw new UnsupportedOperationException();
  }

  public List<T> recursiveDepthFirstSearch() {
    // TODO: Task 3.
    throw new UnsupportedOperationException();
  }
}
