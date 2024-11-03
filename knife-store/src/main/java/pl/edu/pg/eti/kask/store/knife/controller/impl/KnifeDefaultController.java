package pl.edu.pg.eti.kask.store.knife.controller.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import pl.edu.pg.eti.kask.store.controller.servlet.exception.BadRequestException;
import pl.edu.pg.eti.kask.store.factory.DtoFunctionFactory;
import pl.edu.pg.eti.kask.store.knife.controller.api.KnifeController;
import pl.edu.pg.eti.kask.store.knife.dto.GetKnifeResponse;
import pl.edu.pg.eti.kask.store.knife.dto.GetKnivesResponse;
import pl.edu.pg.eti.kask.store.knife.dto.PatchKnifeRequest;
import pl.edu.pg.eti.kask.store.knife.dto.PutKnifeRequest;
import pl.edu.pg.eti.kask.store.knife.service.KnifeService;
import pl.edu.pg.eti.kask.store.controller.servlet.exception.NotFoundException;

import java.util.UUID;

@Path("")
public class KnifeDefaultController implements KnifeController {
    private final KnifeService service;

    private final DtoFunctionFactory factory;

    @Inject
    public KnifeDefaultController(KnifeService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetKnivesResponse getKnives() {
        return factory.knivesToResponse().apply(service.findAll());
    }

    @Override
    public GetKnifeResponse getKnife(UUID id) {
        return service.find(id)
                .map(factory.knifeToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteKnife(UUID id) {
        service.find(id)
                .ifPresentOrElse(service::delete, () -> {
                    throw new NotFoundException();
                });
    }

    @Override
    public void putKnife(UUID id, PutKnifeRequest request){
        try{
            service.create(factory.requestToKnife().apply(id, request));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    public void patchKnife(UUID id, PatchKnifeRequest request){
        service.find(id)
                .ifPresentOrElse(entity -> {
                    service.update(factory.updateKnife().apply(entity, request));
                }, () -> {
                    throw new NotFoundException();
                });
    }

    @Override
    public GetKnivesResponse getKnivesByCategory(UUID id) {
        return service.findAllByCategory(id)
                .map(factory.knivesToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetKnivesResponse getKnivesByUser(UUID id) {
        return service.findAllByUser(id)
                .map(factory.knivesToResponse())
                .orElseThrow(NotFoundException::new);
    }
}
