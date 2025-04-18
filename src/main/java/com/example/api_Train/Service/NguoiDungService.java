package com.example.api_Train.Service;

import com.example.api_Train.Repository.NguoiDungRepository;
import com.example.api_Train.models.NguoiDung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NguoiDungService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    public List<NguoiDung> getAllNguoiDung() {
        return nguoiDungRepository.findAll();
    }

    public Optional<NguoiDung> getNguoiDungById(Integer id) {
        return nguoiDungRepository.findById(id);
    }

    public NguoiDung createNguoiDung(NguoiDung nguoiDung) {
        return nguoiDungRepository.save(nguoiDung);
    }

    public NguoiDung updateNguoiDung(Integer id, NguoiDung nguoiDungDetails) {
        Optional<NguoiDung> optional = nguoiDungRepository.findById(id);
        if (optional.isPresent()) {
            NguoiDung nd = optional.get();
            nd.setTenDangNhap(nguoiDungDetails.getTenDangNhap());
            nd.setMatKhau(nguoiDungDetails.getMatKhau());
            nd.setRole(nguoiDungDetails.getRole());
            nd.setHoTen(nguoiDungDetails.getHoTen());
            nd.setSoDienThoai(nguoiDungDetails.getSoDienThoai());
            nd.setEmail(nguoiDungDetails.getEmail());
            nd.setCccd(nguoiDungDetails.getCccd());
            return nguoiDungRepository.save(nd);
        } else {
            return null;
        }
    }

    public boolean deleteNguoiDung(Integer id) {
        if (nguoiDungRepository.existsById(id)) {
            nguoiDungRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
