package ru.kuznetsov.shop.address.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kuznetsov.shop.data.service.AddressService;
import ru.kuznetsov.shop.represent.dto.AddressDto;

import java.util.List;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.findById(id));
    }

    @GetMapping()
    public ResponseEntity<List<AddressDto>> getAll() {
        return ResponseEntity.ok(addressService.findAll());
    }

    @PostMapping
    public ResponseEntity<AddressDto> create(@RequestBody AddressDto addressDto) {
        return ResponseEntity.ok(addressService.add(addressDto));
    }

    @DeleteMapping("/{id}")
    public void deletee(@PathVariable Long id) {
        addressService.deleteById(id);
    }
}
