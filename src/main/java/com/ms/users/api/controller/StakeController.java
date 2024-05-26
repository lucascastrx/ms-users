package com.ms.users.api.controller;

import com.ms.users.api.assembler.MapperDTO;
import com.ms.users.api.dto.stake.input.StakeInputDTO;
import com.ms.users.api.dto.stake.output.StakeDTO;
import com.ms.users.api.dto.user.input.UserInputDTO;
import com.ms.users.api.dto.user.output.UserDTO;
import com.ms.users.api.dto.wallet.output.WalletDTO;
import com.ms.users.domain.model.Stake;
import com.ms.users.domain.port.service.StakeServicePort;
import com.ms.users.infra.adapter.repository.StakeRepositoryAccess;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallets/{walletId}/stakes")
public class StakeController {

    private final StakeServicePort stakeService;
    private final MapperDTO mapperDTO;
    private final StakeRepositoryAccess stakeRepository;

    public StakeController(StakeServicePort stakeService, MapperDTO mapperDTO, StakeRepositoryAccess stakeRepository) {
        this.stakeService = stakeService;
        this.mapperDTO = mapperDTO;
        this.stakeRepository = stakeRepository;
    }

    @GetMapping("/{stakeId}")
    public StakeDTO retrieveStake(@PathVariable Long stakeId){
        var stake = stakeService.findById(stakeId);
        return mapperDTO.transform(stake, StakeDTO.class);
    }

    @GetMapping
    public List<StakeDTO> list(@PathVariable Long walletId){
        return mapperDTO.toCollection(stakeRepository.findAllByWalletId(walletId), StakeDTO.class);
    }

    @PostMapping
    public StakeDTO addStake(@PathVariable Long walletId, @RequestBody @Valid StakeInputDTO stakeInputDTO){
        var stake = mapperDTO.transform(stakeInputDTO, Stake.class);
        stake = stakeService.addStake(stake, walletId);
        return mapperDTO.transform(stake, StakeDTO.class);
    }

    @PutMapping("/{stakeId}")
    public StakeDTO updateStake(@PathVariable Long walletId, @PathVariable Long stakeId, @RequestBody @Valid StakeInputDTO stakeInputDTO){
        var stake = stakeService.findById(stakeId);
        mapperDTO.copyToDomain(stakeInputDTO, stake);
        stake = stakeService.addStake(stake, walletId);
        return mapperDTO.transform(stake, StakeDTO.class);
    }

    @DeleteMapping("/{stakeId}")
    public void delete(@PathVariable Long stakeId){
        stakeService.delete(stakeId);
    }
}
