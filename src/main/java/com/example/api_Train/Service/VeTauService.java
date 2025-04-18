package com.example.api_Train.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api_Train.Repository.VeTauRepository;
import com.example.api_Train.models.VeTau;

@Service
public class VeTauService {
    @Autowired
    private VeTauRepository veTauRepository;

    public List<VeTau> GetAllVeTau() {
        return veTauRepository.findAll();
    }

    public Optional<VeTau> GetVeTauId(Integer id) {
        return veTauRepository.findById(id);
    }

    public VeTau CreateVeTau(VeTau veTau) {
        return veTauRepository.save(veTau);
    }

    public VeTau UpdateVeTau(Integer id, VeTau veTau) {
        Optional<VeTau> optional = veTauRepository.findById(id);
        if (optional.isPresent()) {
            VeTau vt = optional.get();
            vt.setHanhKhach(veTau.getHanhKhach());
            vt.setChuyenTau(veTau.getChuyenTau());
            vt.setTau(veTau.getTau());
            vt.setGhe(veTau.getGhe());
            vt.setBangGia(veTau.getBangGia());
            vt.setDoiTuong(veTau.getDoiTuong());
            vt.setTinhTrangVe(veTau.getTinhTrangVe());
            return veTauRepository.save(vt);
        } else {
            throw new RuntimeException("Không tìm thấy vé với ID: " + id);
        }
    }

    public boolean DeleteVeTau(Integer id) {
        if (veTauRepository.existsById(id)) {
            veTauRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
