package com.example.signin.Config;

import com.example.signin.Entities.Role;
import com.example.signin.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class StoreRoleConfiguration {
    @Autowired
    private RoleService roleService;

    @Bean
    public void addRoleToTheDatabase(){
        if(roleService.getRoles().isEmpty()){
            List<Role> roleList = new ArrayList<>();
            roleList.add(Role.builder().roleName(String.valueOf(com.example.signin.Enums.Role.ADMIN)).build());
            roleList.add(Role.builder().roleName(String.valueOf(com.example.signin.Enums.Role.MANAGER)).build());
            roleList.add(Role.builder().roleName(String.valueOf(com.example.signin.Enums.Role.USER)).build());
            roleService.addRoles(roleList);
        }
    }
}
