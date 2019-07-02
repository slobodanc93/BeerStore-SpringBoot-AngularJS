package scvetkovic.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import scvetkovic.model.Kupovina;
import scvetkovic.model.Pivo;
import scvetkovic.service.KupovinaService;
import scvetkovic.service.PivoService;
import scvetkovic.support.KupovinaToKupovinaDTO;
import scvetkovic.support.PivoDTOToPivo;
import scvetkovic.support.PivoToPivoDTO;
import scvetkovic.web.dto.KupovinaDTO;
import scvetkovic.web.dto.PivoDTO;

@RestController
@RequestMapping("/api/piva")
public class ApiPivoController {
	
	
	/*** SERVICE & OBJECT TRANSFORMATION LAYER THROUGH DEPENDENCY INJECTION ***/
	@Autowired
	private PivoToPivoDTO toPivoDTO;

	@Autowired
	private PivoDTOToPivo toPivo;
	
	@Autowired
	private KupovinaToKupovinaDTO toKupovinaDTO;

	@Autowired
	private PivoService pivoService;
	
	@Autowired
	private KupovinaService kupovinaService;
	
	
	/*** GET ALL WITH OPTIONAL SEARCH PARAMETERS ***/
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PivoDTO>> get(
			@RequestParam(required = false) String nazivPiva,
			@RequestParam(required = false) Double minIbu, 
			@RequestParam(required = false) Double maxIbu, 
			@RequestParam(required = false) String nazivPivare,
			@RequestParam(required = false) Integer nestalo,
			@RequestParam(defaultValue = "0") int pageNum) {

		Page<Pivo> piva;

		if (nazivPiva != null || minIbu != null || maxIbu != null || nazivPivare != null || nestalo != null) {
			piva = pivoService.pretraga(nazivPiva, minIbu, maxIbu, nazivPivare, nestalo, pageNum);
		} else {
			piva = pivoService.findAll(pageNum);
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("totalPages", Integer.toString(piva.getTotalPages()));

		return new ResponseEntity<>(
				toPivoDTO.convert(piva.getContent()), 
				headers, 
				HttpStatus.OK);
	}
	
	
	/*** GET ONE ***/
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<PivoDTO> get(@PathVariable Long id) {

		Pivo pivo = pivoService.findOne(id);

		if (pivo == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(
				toPivoDTO.convert(pivo), 
				HttpStatus.OK);
	}
	
	/*** POST ***/
	@RequestMapping(method = RequestMethod.POST, consumes="application/json")
	public ResponseEntity<PivoDTO> add(
			@Validated @RequestBody PivoDTO novoPivo) {

		Pivo pivo = toPivo.convert(novoPivo);
		pivoService.save(pivo);

		return new ResponseEntity<>(
				toPivoDTO.convert(pivo), 
				HttpStatus.CREATED);
	}
	
	
	/*** PUT ***/
	@RequestMapping(method=RequestMethod.PUT, value="/{id}", consumes="application/json")
	public ResponseEntity<PivoDTO> edit(
			@PathVariable Long id,
			@Validated @RequestBody PivoDTO izmenjena){
		
		if(!id.equals(izmenjena.getId())){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Pivo pivo = toPivo.convert(izmenjena); 
		pivoService.save(pivo);
		
		return new ResponseEntity<>(
				toPivoDTO.convert(pivo), 
				HttpStatus.OK);
	}
	
	/*** DELETE ***/
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<PivoDTO> delete(
			@PathVariable Long id){
		
		Pivo deleted = pivoService.remove(id);
		if(deleted == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(
				toPivoDTO.convert(deleted),
				HttpStatus.OK);
	}
	
	
	/*** POST (KUPOVINA) ***/
	@RequestMapping(method=RequestMethod.POST, value="/{id}/kupovina")
	public ResponseEntity<KupovinaDTO> glasaj(@PathVariable Long id){
		
		Kupovina kupovina = kupovinaService.kupi(id);
		
		if(kupovina != null) {
			return new ResponseEntity<>(
					toKupovinaDTO.convert(kupovina), 
					HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@ExceptionHandler(value= {DataIntegrityViolationException.class, IllegalArgumentException.class})
	public ResponseEntity<Void> handle() {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	

}
