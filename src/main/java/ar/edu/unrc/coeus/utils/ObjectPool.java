package ar.edu.unrc.coeus.utils;

import com.sun.istack.internal.NotNull;

import java.util.NoSuchElementException;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public abstract
class ObjectPool< E > {

    transient ObjectPool.Node< E > last;
    transient int                  maxSize;
    transient int                  size;

    public
    ObjectPool() {
        this.size = 0;
        this.maxSize = 0;
    }

    /**
     * Puts the specified object in the pool, making it eligible to be returned by {@link #obtain()}.
     */
    public synchronized
    void free( @NotNull E object ) {
        if ( object == null ) { throw new IllegalArgumentException("object cannot be null."); }
        ObjectPool.Node< E > newNode = new ObjectPool.Node(this.last, object);
        this.last = newNode;
        ++this.size;
        if ( size > maxSize ) {
            maxSize = size;
        }
    }

    public
    int getMaxSize() {
        return maxSize;
    }

    public
    int getSize() {
        return size;
    }

    abstract protected
    E newObject();

    public synchronized
    E obtain() {
        if ( size == 0 ) {
            return newObject();
        } else {
            final Node< E > lastNode = this.last;
            if ( lastNode == null ) {
                throw new NoSuchElementException();
            } else {
                final E         item     = lastNode.item;
                final Node< E > prevNode = lastNode.prev;
                lastNode.item = null;
                lastNode.prev = null;
                this.last = prevNode;
                --this.size;
                return item;
            }
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
