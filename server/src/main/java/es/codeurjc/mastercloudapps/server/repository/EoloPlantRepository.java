package es.codeurjc.mastercloudapps.server.repository;

import javax.enterprise.context.ApplicationScoped;

import es.codeurjc.mastercloudapps.server.model.EoloPlant;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class EoloPlantRepository implements PanacheRepository<EoloPlant> {

}
