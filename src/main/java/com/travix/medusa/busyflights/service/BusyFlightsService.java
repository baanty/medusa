package com.travix.medusa.busyflights.service;


import static com.travix.medusa.busyflights.util.Assembler.buildBusyFlightsResponseFromCrazyAirResponse;
import static com.travix.medusa.busyflights.util.Assembler.buildBusyFlightsResponseFromToughJetResponse;
import static com.travix.medusa.busyflights.util.Assembler.buildCrazyAirRequestFromBusyFlights;
import static com.travix.medusa.busyflights.util.Assembler.buildToughJetRequestFromBusyFlights;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.travix.medusa.busyflights.comparator.AirlineResultComparator;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class BusyFlightsService {
	
	@Autowired
	Validator validator;
	
	@Value( value = "${crazy.air.http.url}" )
	String cUrl;
	
	@Value( value = "${tough.jet.http.url}" )
	String tUrl;
	
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping( value = { "/busy/*" }, consumes = "application/json", produces = "application/json" )
	public Object getCrazyAirResponse(@RequestBody BusyFlightsRequest request) {
		
		Set<ConstraintViolation<BusyFlightsRequest>> violations = validator.validate(request);
		
		if (violations != null && violations.size() > 0) {
			return String.join("|", violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));
		}
		
		
		List<BusyFlightsResponse> responseList = new ArrayList<BusyFlightsResponse>();
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);
		
		HttpEntity<CrazyAirRequest> entity = new HttpEntity<CrazyAirRequest>(buildCrazyAirRequestFromBusyFlights(request));
		List<LinkedHashMap<Object, Object>> cResponse = restTemplate.postForObject(cUrl, entity, List.class);
		
		if (!CollectionUtils.isEmpty(cResponse)) {
			cResponse.stream().forEach(responseMap -> {
				
				CrazyAirResponse response = new CrazyAirResponse();
				
				for (Map.Entry<Object, Object> entry : responseMap.entrySet()) {

		        	   try {
		        		
		        		if (!Stream.of(CrazyAirResponse.class.getDeclaredFields()).map(Field::getName).collect(Collectors.toList()).contains(entry.getKey().toString())) {
		        			continue;
		        		}
		        		Field field = CrazyAirResponse.class.getDeclaredField(entry.getKey().toString());
		        		Object value = entry.getValue();
		        		
		        		if (field.getType().equals(LocalDate.class)) {
		        			value = LocalDate.parse(entry.getValue().toString());
		        		}
						PropertyDescriptor pd = new PropertyDescriptor(entry.getKey().toString(), CrazyAirResponse.class);
						pd.getWriteMethod().invoke(response, value);
					} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException | SecurityException e) {
						log.error("Can not invoke setter");
					}
		        	
				}
				responseList.add(buildBusyFlightsResponseFromCrazyAirResponse(response));
			});
		}
		
		
		
		RestTemplate tRestTemplate = new RestTemplate();
		HttpHeaders tHeaders = new HttpHeaders();
		tHeaders.setContentType(MediaType.APPLICATION_JSON);
		tRestTemplate.setMessageConverters(list);
		
		HttpEntity<ToughJetRequest> tEntity = new HttpEntity<ToughJetRequest>(buildToughJetRequestFromBusyFlights(request));
		List<LinkedHashMap<Object, Object>> tResponse = tRestTemplate.postForObject(tUrl, tEntity, List.class);

		
		
		if (!CollectionUtils.isEmpty(tResponse)) {
			tResponse.stream().forEach(responseMap -> {
				
				ToughJetResponse response = new ToughJetResponse();
				
				for (Map.Entry<Object, Object> entry : responseMap.entrySet()) {

		        	   try {
		        		
		        		if (!Stream.of(ToughJetResponse.class.getDeclaredFields()).map(Field::getName).collect(Collectors.toList()).contains(entry.getKey().toString())) {
		        			continue;
		        		}
		        		Field field = ToughJetResponse.class.getDeclaredField(entry.getKey().toString());
		        		Object value = entry.getValue();
		        		
		        		if (field.getType().equals(LocalDate.class)) {
		        			value = LocalDate.parse(entry.getValue().toString());
		        		}
						PropertyDescriptor pd = new PropertyDescriptor(entry.getKey().toString(), ToughJetResponse.class);
						pd.getWriteMethod().invoke(response, value);
					} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException | SecurityException e) {
						log.error("Can not invoke setter");
					}
		        	
				}
				responseList.add(buildBusyFlightsResponseFromToughJetResponse(response));
			});
		}
	
		Collections.sort(responseList, new AirlineResultComparator());
		
		return responseList;
	}
	
	

}
