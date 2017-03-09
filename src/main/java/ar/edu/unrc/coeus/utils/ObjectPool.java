package ar.edu.unrc.coeus.utils;

import java.util.NoSuchElementException;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
@SuppressWarnings( "AbstractClassNeverImplemented" )
public abstract
class ObjectPool< E > {

    private transient Node< E > last = null;
    private transient int       maxSize;
    private transient int       size;

    protected
    ObjectPool() {
        super();
        size = 0;
        maxSize = 0;
    }

    /**
     * Puts the specified object in the pool, making it eligible to be returned by {@link #obtain()}.
     */
    public synchronized
    void free( final E object ) {
        if ( object == null ) { throw new IllegalArgumentException("object cannot be null."); }
        last = new Node<>(last, object);
        ++size;
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

    protected abstract
    E newObject();

    public synchronized
    E obtain() {
        if ( size == 0 ) {
            return newObject();
        } else {
            final Node< E > lastNode = last;
            if ( lastNode == null ) {
                throw new NoSuchElementException();
            } else {
                final E         item     = lastNode.item;
                final Node< E > prevNode = lastNode.prev;
                lastNode.item = null;
                lastNode.prev = null;
                last = prevNode;
                --size;
                return item;
            }
        }
    }

    private static
    class Node< E > {
        E         item;
        Node< E > prev;

        Node(
                final Node< E > prev,
                final E item
        ) {
            super();
            this.item = item;
            this.prev = prev;
        }
    }
}
