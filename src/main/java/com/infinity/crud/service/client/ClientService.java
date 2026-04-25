package com.infinity.crud.service.client;

import com.infinity.crud.dto.clientdto.ClientRequestDTO;
import com.infinity.crud.dto.clientdto.ClientResponseDTO;
import com.infinity.crud.dto.clientdto.ClientUpdateDTO;

//CRUD
public interface ClientService {
    ClientResponseDTO createClient(ClientRequestDTO dto);
    ClientResponseDTO searchClient (Long id);
    ClientResponseDTO updateClient (Long id, ClientUpdateDTO dto);
    void deleteClient(Long id);
}
