
package com.sentrifugo.performanceManagement.service;
import com.sentrifugo.performanceManagement.entity.ClientAsset;
import com.sentrifugo.performanceManagement.repository.ClientAssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientAssetService {

    @Autowired
    private ClientAssetRepository assetRepository;

    public List<ClientAsset> getAllAssets() {
        return assetRepository.findAll();
    }

    public ClientAsset addAsset(ClientAsset asset) {
        return assetRepository.save(asset);
    }

    public ClientAsset getAssetById(Long id) {
        return assetRepository.findById(id).orElse(null);
    }

    public ClientAsset updateAsset(ClientAsset asset) {
        return assetRepository.save(asset);
    }


}
