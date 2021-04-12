package com.github.thiagodutra.coopvoteservice.api.controllers;

public class SessionController {
    
    /* @RestController
    @RequestMapping("/resourceRootPath")
    class resourceNameController {
    
        @Autowired
        repositoryClassName repository;
    
        @GetMapping
        public ResponseEntity<List<entityClassName>> getAll() {
            try {
                List<entityClassName> items = new ArrayList<entityClassName>();
    
                repository.findAll().forEach(items::add);
    
                if (items.isEmpty())
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    
                return new ResponseEntity<>(items, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    
        @GetMapping("{id}")
        public ResponseEntity<entityClassName> getById(@PathVariable("id") entityIdType id) {
            Optional<entityClassName> existingItemOptional = repository.findById(id);
    
            if (existingItemOptional.isPresent()) {
                return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    
        @PostMapping
        public ResponseEntity<entityClassName> create(@RequestBody entityClassName item) {
            try {
                entityClassName savedItem = repository.save(item);
                return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
            }
        }
    
        @PutMapping("{id}")
        public ResponseEntity<entityClassName> update(@PathVariable("id") entityIdType id, @RequestBody entityClassName item) {
            Optional<entityClassName> existingItemOptional = repository.findById(id);
            if (existingItemOptional.isPresent()) {
                entityClassName existingItem = existingItemOptional.get();
                System.out.println("TODO for developer - update logic is unique to entity and must be implemented manually.");
                //existingItem.setSomeField(item.getSomeField());
                return new ResponseEntity<>(repository.save(existingItem), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    
        @DeleteMapping("{id}")
        public ResponseEntity<HttpStatus> delete(@PathVariable("id") entityIdType id) {
            try {
                repository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
        }
    } */
}
