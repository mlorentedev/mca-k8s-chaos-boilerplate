package es.codeurjc.mastercloudapps.server.graphql;

import java.util.Collection;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Id;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import es.codeurjc.mastercloudapps.server.model.EoloPlant;
import es.codeurjc.mastercloudapps.server.model.EoloPlantInput;
import es.codeurjc.mastercloudapps.server.service.EoloPlantService;

@GraphQLApi
public class EoloPlantController {

    @Inject
	EoloPlantService plants;

	@Query
	public Collection<EoloPlant> eoloPlants() {
		return plants.findAll();
	}

	@Query
	public EoloPlant eoloPlant(@Id long id) {
		return plants.findById(id).orElseThrow();
	}

	@Mutation
	@Transactional
	public EoloPlant createEoloPlant(EoloPlantInput eoloPlant) {

		return plants.createEoloplant(eoloPlant);
	}

	@Mutation
	@Transactional
	public EoloPlant deleteEoloPlant(@Id long id) {

		return plants.deleteById(id);
	}

}
