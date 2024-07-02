

package com.sentrifugo.performanceManagement.service;
import com.sentrifugo.performanceManagement.entity.SowMaster;
import com.sentrifugo.performanceManagement.repository.SowMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SowMasterService {
    @Autowired
    private SowMasterRepository sowMasterRepository;

    public List<SowMaster> getAll() {
        return sowMasterRepository.findAll();
    }

    public SowMaster add(SowMaster sowMaster) {
        return sowMasterRepository.save(sowMaster);
    }

    public List<String> getSowIds() {
        return sowMasterRepository.getSowIds();
    }

    public SowMaster getSowDataById(String id) {
        return sowMasterRepository.getSowDataById(id);
    }

    public SowMaster update(SowMaster updatedSow) {
        Optional<SowMaster> sow=sowMasterRepository.findById(updatedSow.getId());
        if(sow.isPresent()){
            return sowMasterRepository.save(updatedSow);
        }
        else {
            return null;
        }
    }
}
