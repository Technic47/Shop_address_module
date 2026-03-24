package ru.kuznetsov.shop.address.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kuznetsov.shop.address.api.AddressControllerApi;
import ru.kuznetsov.shop.data.service.AddressService;
import ru.kuznetsov.shop.represent.dto.AddressDto;

import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController implements AddressControllerApi {

    private final AddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getById(@PathVariable Long id) {
        AddressDto byId = addressService.findById(id);
        return byId == null ?
                ResponseEntity.status(NO_CONTENT).build()
                : ResponseEntity.ok(byId);
    }

    @GetMapping()
    public ResponseEntity<List<AddressDto>> getAll() {
        List<AddressDto> found = addressService.findAll();
        return found.isEmpty() ?
                ResponseEntity.status(NO_CONTENT).build()
                : ResponseEntity.ok(found);
    }

    @PostMapping
    public ResponseEntity<AddressDto> create(@RequestBody AddressDto addressDto) {
        return ResponseEntity.ok(addressService.add(addressDto));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<AddressDto>> createBatch(@RequestBody Collection<AddressDto> addressDtoCollection) {
        return ResponseEntity.ok(addressDtoCollection.stream()
                .map(addressService::add)
                .toList());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        addressService.deleteById(id);
    }
}
