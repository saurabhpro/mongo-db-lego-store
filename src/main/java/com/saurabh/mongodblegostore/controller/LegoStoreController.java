package com.saurabh.mongodblegostore.controller;

import com.saurabh.mongodblegostore.model.LegoSet;
import com.saurabh.mongodblegostore.persistance.repository.LegoSetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/legoStore")
public class LegoStoreController {

    private final LegoSetRepository legoSetRepository;

    public LegoStoreController(LegoSetRepository legoSetRepository) {
        this.legoSetRepository = legoSetRepository;
    }

    @PostMapping
    public ResponseEntity<LegoSet> insert(@RequestBody LegoSet lego) {
        final LegoSet insert = this.legoSetRepository.insert(lego);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(insert.getId())
                .toUri();

        return ResponseEntity.created(uri).build();

    }

    @GetMapping
    public ResponseEntity<List<LegoSet>> getAll() {
        //return this.mongoTemplate.findAll(LegoSet.class);
        Sort sortByThemeAsc = Sort.by("theme").ascending();
        final List<LegoSet> legoSetList = this.legoSetRepository.findAll(sortByThemeAsc);

        return legoSetList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(legoSetList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LegoSet> get(@PathVariable String id) {
        final Optional<LegoSet> legoSetList = this.legoSetRepository.findById(id);

        return legoSetList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(legoSetList.get());
    }

    @PutMapping
    public ResponseEntity<LegoSet> update(@RequestBody LegoSet lego) {
        LegoSet legoSet = this.legoSetRepository.save(lego);
        return ResponseEntity.ok(legoSet);
    }

    @DeleteMapping("{id}")
    public ResponseEntity.HeadersBuilder<?> delete(@PathVariable String id) {
        //final LegoSet removed = this.legoSetRepository.findAndRemove(new Query(Criteria.where("id").is(id)), LegoSet.class);
        log.info("deleted: " + id);
        this.legoSetRepository.deleteById(id);

        return ResponseEntity.noContent();
    }
}
