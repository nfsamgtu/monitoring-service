package com.smetering.domain.entities;

import com.smetering.domain.entities.enums.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Учётная запись пользователя системы передачи показаний приборов учёта.
 *
 * Атрибуты класса:
 * hashedPassword - хэш пароля учётной записи;
 * customer - потребитель ресурсов, для которого создана учётная запись;
 * activityHistory - список действий в системе;
 * role - роль пользователя системы.
 */
public class User extends IdentifiedObject {

    private final String hashedPassword;
    private final Customer customer;
    private final List<String> activityHistory = new ArrayList<>();
    private Role role;

    public User(String hashedPassword) {
        super();
        this.role = Role.ROLE_USER;
        this.hashedPassword = hashedPassword;
        this.customer = new Customer();
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<String> getActivityHistory() {
        return this.activityHistory;
    }

    public void addActivityRecord(String record) {
        this.activityHistory.add(record);
    }


    @Override
    public String toString() {
        return "User{" +
                "mRID=" + getmRID() + ", " +
                "name=" + getName() + ", " +
                "role=" + getRole() +
                '}';
    }
}
