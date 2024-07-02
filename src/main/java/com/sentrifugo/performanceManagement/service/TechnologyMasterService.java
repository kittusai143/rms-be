

package com.sentrifugo.performanceManagement.service;
import com.sentrifugo.performanceManagement.entity.TechnologyMaster;
import com.sentrifugo.performanceManagement.repository.TechnologyMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class TechnologyMasterService {
@Autowired
private TechnologyMasterRepository technologyMasterRepository;
    public TechnologyMaster update(TechnologyMaster technologyMaster) {
        Optional<TechnologyMaster> techmaster=technologyMasterRepository.findById(technologyMaster.getTechId());
        if(techmaster.isPresent()){
            return technologyMasterRepository.save(technologyMaster);
        }
        else {
            return null;
        }
    }

    public Map<String, List<String>> getDistinctGroupsandSkills() {

        List<String> groups=technologyMasterRepository.getDistinctGroups();
        List<String> skills=technologyMasterRepository.getDistinctSkills();
        Map<String, List<String>> map=new HashMap<>();
        map.put("groups",groups);
        map.put("skills",skills);
        return map;
    }
}
