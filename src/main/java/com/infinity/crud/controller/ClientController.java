package com.infinity.crud.controller;

import com.infinity.crud.dto.clientdto.ClientRequestDTO;
import com.infinity.crud.dto.clientdto.ClientResponseDTO;
import com.infinity.crud.dto.clientdto.ClientUpdateDTO;
import com.infinity.crud.service.client.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ClientResponseDTO createClient(@RequestBody @Valid ClientRequestDTO dto) {
        return clientService.createClient(dto);
    }

    @GetMapping("/{clientId}")
    public ClientResponseDTO searchClient(@PathVariable Long clientId) {
        return clientService.searchClient(clientId);
    }

    @PatchMapping("/{clientId}")
    public ClientResponseDTO updateClient(@PathVariable Long clientId,
                                          @RequestBody @Valid ClientUpdateDTO dto) {
        return clientService.updateClient(clientId, dto);
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long clientId) {
        clientService.deleteClient(clientId);
        return ResponseEntity.noContent().build();
    }
}