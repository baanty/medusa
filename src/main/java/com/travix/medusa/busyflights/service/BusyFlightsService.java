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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	
	@Autowired
	RestTemplateService templateService;
	
	@Value( value = "${crazy.air.http.url}" )
	String cUrl;
	
	@Value( value = "${tough.jet.http.url}" )
	String tUrl;
	
	@ResponseBody
	@RequestMapping( value = { "/busy/*" }, consumes = "application/json", produces = "application/json" )
	public Object getCrazyAirResponse(@RequestBody BusyFlightsRequest request) {
		
		Set<ConstraintViolation<BusyFlightsRequest>> violations = validator.validate(request);
		
		if (violations != null && violations.size() > 0) {
			return String.join("|", violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));
		}
		
		
		List<BusyFlightsResponse> responseList = new ArrayList<BusyFlightsResponse>();
		
		HttpEntity<CrazyAirRequest> entity = new HttpEntity<CrazyAirRequest>(buildCrazyAirRequestFromBusyFlights(request));
		List<LinkedHashMap<Object, Object>> cResponse = templateService.callRestTemplate(cUrl, entity);
		
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
		

		HttpEntity<ToughJetRequest> tEntity = new HttpEntity<ToughJetRequest>(buildToughJetRequestFromBusyFlights(request));
		List<LinkedHashMap<Object, Object>> tResponse = templateService.callRestTemplate(tUrl, tEntity);

		
		
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
