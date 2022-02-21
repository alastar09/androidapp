package ru.biis.biissale;

public class Catsbiis {

    private Long id;
    private String catname;
    private String catid;

    public Catsbiis(Long id, String title, String catid) {
        this.id = id;
        this.catname = title;
        this.catid = catid;
    }

    public String getText() {
        return catname;
    }
    public String getCatid() {
        return catid;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Catsbiis catsbiis = (Catsbiis) o;

        if (!id.equals(catsbiis.id)) return false;
        if (!catid.equals(catsbiis.catid)) return false;
        return catname != null ? catname.equals(catsbiis.catname) : catsbiis.catname == null;
    }
}