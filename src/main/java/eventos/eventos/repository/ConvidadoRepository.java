package eventos.eventos.repository;

import org.springframework.data.repository.CrudRepository;

import eventos.eventos.models.Convidado;
import eventos.eventos.models.Evento;

public interface ConvidadoRepository extends CrudRepository<Convidado, String> {

    Iterable<Convidado> findByEvento(Evento evento);
    Convidado findByRg(String rg);

}