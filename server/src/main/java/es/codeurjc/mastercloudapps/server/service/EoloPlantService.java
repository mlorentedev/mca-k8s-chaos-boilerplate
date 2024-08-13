package es.codeurjc.mastercloudapps.server.service;

import es.codeurjc.mastercloudapps.server.model.EoloPlant;
import es.codeurjc.mastercloudapps.server.model.EoloPlantCreationRequest;
import es.codeurjc.mastercloudapps.server.model.EoloPlantInput;
import es.codeurjc.mastercloudapps.server.repository.EoloPlantRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EoloPlantService {

    @Inject
    private EoloPlantRepository eoloPlants;

    @Inject
    private EoloPlantCreatorService eoloPlantCreator;

    @Inject
    private WebSocketHandler wsService;

    public Collection<EoloPlant> findAll() {
        return eoloPlants.listAll();
    }

    public Optional<EoloPlant> findById(long id) {
        return eoloPlants.findByIdOptional(id);
    }

    public EoloPlant createEoloplant(EoloPlantInput eoloPlantCreationRequest) {

        EoloPlant eoloPlant = new EoloPlant(eoloPlantCreationRequest.getUserId(), eoloPlantCreationRequest.getCity());
        eoloPlants.persist(eoloPlant);

        EoloPlantCreationRequest request = new EoloPlantCreationRequest((int)eoloPlant.getId(), (int) eoloPlant.getUserId(), eoloPlant.getCity());
        eoloPlantCreator.createEoloPlant(request);

        return eoloPlant;
    }

    public EoloPlant deleteById(long id) {

        EoloPlant eoloPlant = eoloPlants.findByIdOptional(id).orElseThrow();

        eoloPlants.deleteById(id);

        return eoloPlant;
    }

   
}
