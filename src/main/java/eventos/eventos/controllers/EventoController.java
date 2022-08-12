package eventos.eventos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import eventos.eventos.models.Convidado;
import eventos.eventos.models.Evento;
import eventos.eventos.repository.ConvidadoRepository;
import eventos.eventos.repository.EventoRepository;

@Controller
public class EventoController {

    @Autowired
    private EventoRepository er;

    @Autowired
    private ConvidadoRepository cr;

    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
    public String form() {
        
        return "formEvento";
    }

    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
    public String form(Evento evento) {
        
        er.save(evento);
        
        return "redirect:/cadastrarEvento";
    }

    @RequestMapping("/")
    public ModelAndView listaEventos() {
        
        ModelAndView mv = new ModelAndView("index");
        Iterable<Evento> eventos = er.findAll();
        mv.addObject("eventos", eventos);
        
        return mv;
    }

    @RequestMapping(value = "/{nome}", method = RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("nome") String nome){
        
        Evento evento = er.findByNome(nome);
        ModelAndView mv = new ModelAndView("detalhesEvento");
        mv.addObject("evento", evento);

        Iterable<Convidado> convidados = cr.findByEvento(evento);
		mv.addObject("convidados", convidados);
        
		return mv;
	}

    @RequestMapping(value = "/{nome}", method = RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("nome") String nome, Convidado convidado){
		
        Evento evento = er.findByNome(nome);
        convidado.setEvento(evento);
		cr.save(convidado);
        
		return "redirect:/{nome}";
	}

    @RequestMapping("/deletarEvento")
    public String deletarEvento(String nome){
        
        Evento evento = er.findByNome(nome);
        er.delete(evento);

        return "redirect:/";
    }

    @RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg){
		Convidado convidado = cr.findByRg(rg);
		cr.delete(convidado);
		
		Evento evento = convidado.getEvento();
		String codigoLong = evento.getNome();
		String codigo = "" + codigoLong;
		return "redirect:/" + codigo;
	}

}