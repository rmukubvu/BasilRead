package za.co.thamserios.basilread.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by robson on 2017/03/02.
 */
public class OperationsUser {
    public Long _id; // for cupboard
    public int operationsId;
    public Integer roleId;
    public String firstName;
    public String lastName;

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long createdDate;

    public int getOperationsId() {
        return operationsId;
    }

    public void setOperationsId(int operationsId) {
        this.operationsId = operationsId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("%s %s",firstName,lastName);
    }
}
