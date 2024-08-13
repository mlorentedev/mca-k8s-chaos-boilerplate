package es.codeurjc.mastercloudapps.server.service;

import es.codeurjc.mastercloudapps.server.model.EoloPlantCreationRequest;
import es.codeurjc.mastercloudapps.server.model.EoloPlantInput;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class EoloPlantCreatorService {

    @Channel("eoloplantCreationRequests")
    Emitter<EoloPlantCreationRequest> emitter;

    public void createEoloPlant(EoloPlantCreationRequest eoloPlantCreationRequest) {
		emitter.send(eoloPlantCreationRequest);
    }
    
    public String jsonify(EoloPlantInput request) throws JsonProcessingException {
    	ObjectMapper mapper = new ObjectMapper();
    	return mapper.writeValueAsString(request);
    }

}
