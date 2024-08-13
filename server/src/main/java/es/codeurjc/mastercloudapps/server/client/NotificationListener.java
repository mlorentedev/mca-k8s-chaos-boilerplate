package es.codeurjc.mastercloudapps.server.client;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.rabbitmq.client.AMQP.Basic.Ack;

import es.codeurjc.mastercloudapps.server.service.WebSocketHandler;
import es.codeurjc.mastercloudapps.server.model.EoloPlant;
import es.codeurjc.mastercloudapps.server.repository.EoloPlantRepository;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class NotificationListener {
    
    @Inject
    private EoloPlantRepository eoloPlants;

    @Inject
    private WebSocketHandler wsService;

    @Incoming("eoloplantCreationProgressNotifications")
    @Blocking
    @Transactional
    public void notifications(JsonObject jsonEoloPlant) {
        EoloPlant eoloplant = jsonEoloPlant.mapTo(EoloPlant.class);
        System.out.printf("Progress received: %s -> %d%n", eoloplant.getCity(), eoloplant.getProgress());
        EoloPlant saved = eoloPlants.findByIdOptional(eoloplant.getId()).get();
        saved.copy(eoloplant);
        eoloPlants.persist(saved);
        try {
            wsService.sendUpdate(eoloplant.getUserId(), eoloplant);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
