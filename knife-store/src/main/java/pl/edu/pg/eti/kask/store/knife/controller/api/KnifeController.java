package pl.edu.pg.eti.kask.store.knife.controller.api;

import pl.edu.pg.eti.kask.store.knife.dto.GetKnifeResponse;
import pl.edu.pg.eti.kask.store.knife.dto.GetKnivesResponse;
import pl.edu.pg.eti.kask.store.knife.dto.PatchKnifeRequest;
import pl.edu.pg.eti.kask.store.knife.dto.PutKnifeRequest;

import java.util.UUID;

public interface KnifeController {

    GetKnivesResponse getKnives();

    GetKnivesResponse getKnivesByCategory(UUID id);

    GetKnivesResponse getKnivesByUser(UUID id);

    GetKnifeResponse getKnife(UUID id);

    void putKnife(UUID id, PutKnifeRequest request);

    void patchKnife(UUID id, PatchKnifeRequest request);

    void deleteKnife(UUID id);
}
