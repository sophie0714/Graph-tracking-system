package nz.ac.auckland.se281.datastructures;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    throw new UnsupportedOperationException();
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
    Set<Edge<T>> edgesCopy = edges;

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
    throw new UnsupportedOperationException();
  }

  public List<T> iterativeBreadthFirstSearch() {
    // TODO: Task 2.
    throw new UnsupportedOperationException();
  }

  public List<T> iterativeDepthFirstSearch() {
    // TODO: Task 2.
    throw new UnsupportedOperationException();
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
