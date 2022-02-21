package ru.biis.biissale;

public class Infobiis {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;

    public Infobiis(Long id, String name, String email, String phone, String address) {
        this.id    = id;
        this.name  = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Infobiis infobiis = (Infobiis) o;

        if (!id.equals(infobiis.id)) return false;
        if (!name.equals(infobiis.name)) return false;
        if (!phone.equals(infobiis.phone)) return false;
        if (!address.equals(infobiis.address)) return false;
        return email != null ? email.equals(infobiis.email) : infobiis.email == null;
    }

}
