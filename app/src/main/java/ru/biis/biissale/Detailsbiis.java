package ru.biis.biissale;

public class Detailsbiis {

    private Long id;
    private String company;
    private String price;
    private String message;
    private String parent;

    public Detailsbiis(Long id, String company, String price, String message, String parent) {
        this.id = id;
        this.company = company;
        this.price = price;
        this.message = message;
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }
    public String getPrice() {
        return price;
    }
    public String getMessage() {
        return message;
    }
    public String getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Detailsbiis detailsbiis = (Detailsbiis) o;

        if (!id.equals(detailsbiis.id)) return false;
        return company != null ? company.equals(detailsbiis.company) : detailsbiis.company == null;
    }
}
