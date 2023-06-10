package com.example.signin.Service;

import com.example.signin.Entities.Role;
import com.example.signin.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public void addRoles(List<Role> roleList){
        roleRepository.saveAll(roleList);
    }

    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

    public Role getRole(int roleId){
        return roleRepository.getById(roleId);
    }
}
