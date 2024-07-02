

package com.sentrifugo.performanceManagement.service;
import com.sentrifugo.performanceManagement.entity.DomainMaster;
import com.sentrifugo.performanceManagement.repository.DomainMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DomainMasterService {

    @Autowired
    private DomainMasterRepository domainMasterRepository;

    public String addDomain(DomainMaster domainMaster) {
        domainMasterRepository.save(domainMaster);
        return "new Domain Added";
    }

    public DomainMaster update(DomainMaster domainMaster) {
        Optional<DomainMaster> project=domainMasterRepository.findById(domainMaster.getDomainId());
        if(project.isPresent()){
            return domainMasterRepository.save(domainMaster);
        }
        else {
            return null;
        }
    }
}
