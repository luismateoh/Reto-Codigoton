package com.codigoton.dinnerforclients.service;

import com.codigoton.dinnerforclients.entity.Client;
import com.codigoton.dinnerforclients.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ModelMapper modelMapper;
    @Autowired
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    public List<Client> findAll() {
        List<Client> listItems = (List<Client>) clientRepository.findAll();
        return listItems.stream()
                .map(client -> modelMapper.map(client, Client.class))
                .collect(Collectors.toList());

    }

    public Client findById(Integer id) {
        Optional<Client> findClientOptional = clientRepository.findById(id);
        Client findClient = findClientOptional.orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(findClient, Client.class);
    }


}
