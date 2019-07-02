package scvetkovic.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import scvetkovic.model.Pivara;
import scvetkovic.model.Pivo;
import scvetkovic.service.PivaraService;
import scvetkovic.service.PivoService;
import scvetkovic.support.PivaraToPivaraDTO;
import scvetkovic.support.PivoToPivoDTO;
import scvetkovic.web.dto.PivaraDTO;
import scvetkovic.web.dto.PivoDTO;

@RestController
@RequestMapping("/api/pivare")
public class ApiPivaraController {
	
	/*** SERVICE & OBJECT TRANSFORMATION LAYER THROUGH DEPENDENCY INJECTION ***/
	@Autowired
	private PivaraService pivaraService;
	
	@Autowired
	private PivoService pivoService;

	@Autowired
	private PivaraToPivaraDTO toPivaraDTO;
	
	@Autowired
	PivoToPivoDTO toPivoDTO;
	
	
	/*** GET ALL  ***/
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<PivaraDTO>> get(){
		return new ResponseEntity<>(
				toPivaraDTO.convert(pivaraService.findAll()),
				HttpStatus.OK);
	}
	
	
	/*** GET ONE ***/
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<PivaraDTO> get(@PathVariable Long id) {

		Pivara pivara = pivaraService.findOne(id);

		if (pivara == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(
				toPivaraDTO.convert(pivara), 
				HttpStatus.OK);
	}
	
	/*** GET ONE FESTIVALS ***/
	@RequestMapping(value="/{id_pivare}/piva")
	public ResponseEntity<List<PivoDTO>> getFestivali(
			@PathVariable Long id_pivare,
			@RequestParam(defaultValue="0") int pageNum){
		Page<Pivo> piva = pivoService.findByPivaraId(pageNum, id_pivare);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("totalPages", Integer.toString(piva.getTotalPages()) );
		return  new ResponseEntity<>(
				toPivoDTO.convert(piva.getContent()),
				headers,
				HttpStatus.OK);
	}
	
	@ExceptionHandler(value=DataIntegrityViolationException.class)
	public ResponseEntity<Void> handle() {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
