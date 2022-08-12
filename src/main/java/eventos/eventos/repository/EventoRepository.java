package eventos.eventos.repository;

import org.springframework.data.repository.CrudRepository;
import eventos.eventos.models.Evento;

public interface EventoRepository extends CrudRepository<Evento, String> {

    Evento findByNome(String nome);

}