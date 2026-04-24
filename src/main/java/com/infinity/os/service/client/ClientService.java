package com.infinity.os.service.client;

import com.infinity.os.dto.clientdto.ClientRequestDTO;
import com.infinity.os.dto.clientdto.ClientResponseDTO;
import com.infinity.os.dto.clientdto.ClientUpdateDTO;

//CRUD
public interface ClientService {
    ClientResponseDTO createClient(ClientRequestDTO dto);
    ClientResponseDTO searchClient (Long id);
    ClientResponseDTO updateClient (Long id, ClientUpdateDTO dto);
    void deleteClient(Long id);
}
