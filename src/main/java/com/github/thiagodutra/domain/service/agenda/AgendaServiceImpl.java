package com.github.thiagodutra.domain.service.agenda;

import com.github.thiagodutra.domain.dto.AgendaDTO;
import com.github.thiagodutra.domain.repository.AgendaRepository;
import com.github.thiagodutra.domain.service.AgendaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaServiceImpl implements AgendaService{

    @Autowired
    AgendaRepository agendaRepository;

    @Override
    public void getAgendaById(Long id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void createAgenda(AgendaDTO newAgenda) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void getAgendaVotingSession(Long id) {
        // TODO This will return all open voting session
        
    }
    
    
    

}