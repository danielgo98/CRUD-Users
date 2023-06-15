package org.dabravo.java.jdbc.repositorio;

import java.util.List;

public interface Repositorio <T> {
    List<T> listar();
    void crear(T tipo);

    T porId(Long id);
    void actualizar(T tipo);
    void eliminar(long id);
}
