package com.ms.users.infra.adapter.repository;

import com.ms.users.api.assembler.MapperDTO;
import com.ms.users.domain.model.Stake;
import com.ms.users.domain.model.User;
import com.ms.users.domain.port.repository.StakeRepositoryPort;
import com.ms.users.infra.adapter.model.StakeModel;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StakeRepository implements StakeRepositoryPort {

    private final StakeRepositoryAccess stakeRepositoryAccess;
    private final MapperDTO mapper;

    public StakeRepository(StakeRepositoryAccess stakeRepositoryAccess, MapperDTO mapper) {
        this.stakeRepositoryAccess = stakeRepositoryAccess;
        this.mapper = mapper;
    }

    @Override
    public Stake save(Stake stake){
        var stakeModel = mapper.transform(stake, StakeModel.class);
        stakeModel = stakeRepositoryAccess.save(stakeModel);
        stake = mapper.transform(stakeModel, Stake.class);
        return stake;
    }

    @Override
    public Optional<Stake> findById(Long id){

        var stakeModel = stakeRepositoryAccess.findById(id);
        if(stakeModel.isEmpty()) return Optional.empty();
        var stake = mapper.transform(stakeModel.get(), Stake.class);
        return Optional.of(stake);
    }

    @Override
    public void deleteById(Long id){
        stakeRepositoryAccess.deleteById(id);
    }

    @Override
    public void flush(){
        stakeRepositoryAccess.flush();
    }
}
