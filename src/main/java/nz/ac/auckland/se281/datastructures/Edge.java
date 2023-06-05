package nz.ac.auckland.se281.datastructures;

import java.util.Objects;

/**
 * An edge in a graph that connects two verticies.
 *
 * <p>You must NOT change the signature of the constructor of this class.
 *
 * @param <T> The type of each vertex.
 */
public class Edge<T> {
  private T source;
  private T destination;

  public Edge(T source, T destination) {
    this.source = source;
    this.destination = destination;
  }

  public T getSource() {
    return source;
  }

  public T getDestination() {
    return destination;
  }

  @Override
  // When two edges have the same source and destination, two edges are equal
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (obj == null) {
      return false;
    } else if (getClass() != obj.getClass()) {
      return false;
    } else {
      Edge<T> edge = (Edge<T>) obj;
      return source == edge.source && destination == edge.destination;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(source, destination);
  }
}
