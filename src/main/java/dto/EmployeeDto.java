package dto;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class EmployeeDto {
    private Long id;

    private String employeeName;

    private String gender;

    private Set<EmailDto> emailsDto;

    private Set<AddressDto> addressesDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Set<EmailDto> getEmailsDto() {
        return emailsDto;
    }

    public void setEmailsDto(Set<EmailDto> emailsDto) {
        this.emailsDto = emailsDto;
    }

    public void addEmailDto(EmailDto emailDto) {
        if (CollectionUtils.isEmpty (this.emailsDto)) {
            this.emailsDto = new HashSet<> ();
        }
        this.emailsDto.add(emailDto);
        emailDto.setEmployeeDto (this);
    }

    public void setAddressesDto(Set<AddressDto> addressesDto) {
        this.addressesDto = addressesDto;
    }

    public Set<AddressDto> getAddressesDto() {
        return addressesDto;
    }

    public void addAddressDto(AddressDto addressDto) {
        if(CollectionUtils.isEmpty (addressesDto)) {
            this.addressesDto = new HashSet<> ();
        }
        this.addressesDto.add (addressDto);
        addressDto.setEmployeeDto (this);
    }
}
