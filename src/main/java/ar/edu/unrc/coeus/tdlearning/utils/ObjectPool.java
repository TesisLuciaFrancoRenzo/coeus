package ar.edu.unrc.coeus.tdlearning.utils;

import java.util.NoSuchElementException;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public abstract
class ObjectPool< E > {

    transient ObjectPool.Node< E > last;
    transient int                  size;

    public
    ObjectPool() {
        this.size = 0;
    }

    /**
     * Puts the specified object in the pool, making it eligible to be returned by {@link #obtain()}.
     */
    public synchronized
    void free( E object ) {
        if ( object == null ) { throw new IllegalArgumentException("object cannot be null."); }
        ObjectPool.Node< E > newNode = new ObjectPool.Node(this.last, object);
        this.last = newNode;
        ++this.size;
    }

    abstract protected
    E newObject();

    public
    E obtain() {
        return size == 0 ? newObject() : recycleLastNode();
    }

    private synchronized
    E recycleLastNode() {
        Node< E > lastNode = this.last;
        if ( lastNode == null ) {
            throw new NoSuchElementException();
        } else {
            Node< E > prevNode = lastNode.prev;
            lastNode.item = null;
            lastNode.prev = null;
            this.last = prevNode;
            --this.size;
            return lastNode.item;
        }
    }

    private static
    class Node< E > {
        E                    item;
        ObjectPool.Node< E > prev;

        Node(
                ObjectPool.Node< E > prev,
                E item
        ) {
            this.item = item;
            this.prev = prev;
        }
    }
}
